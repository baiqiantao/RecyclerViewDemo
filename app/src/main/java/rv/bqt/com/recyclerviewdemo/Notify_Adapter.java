package rv.bqt.com.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

public class Notify_Adapter extends RecyclerView.Adapter<Notify_Adapter.MyViewHolder> {
	private Context context;
	private List<PicUrls.BasicPicBean> mDatas;
	private MyOnItemClickLitener mOnItemClickLitener;
	public static final int NOTIFY_TV = 10086;
	public static final int NOTIFY_ET = 10087;
	public static final int NOTIFY_IV = 10088;

	public Notify_Adapter(Context context, List<PicUrls.BasicPicBean> mDatas) {
		this.context = context;
		this.mDatas = mDatas;
	}

	//**************************************************关键代码****************************************
	@Override
	public void onViewAttachedToWindow(MyViewHolder holder) {
		super.onViewAttachedToWindow(holder);//父类中为空代码
		int pos = holder.getAdapterPosition();
		long leftTime = mDatas.get(pos).url.hashCode() + 1000L * 60 * 60 * 24 * 365 * 500 - System.currentTimeMillis();
		if (leftTime > 0) {
			holder.count.start(leftTime);
		} else {
			holder.count.stop();
			holder.count.allShowZero();
		}
	}

	@Override
	public void onViewDetachedFromWindow(MyViewHolder holder) {
		super.onViewDetachedFromWindow(holder);
		holder.count.stop();
	}

	//**************************************************关键代码****************************************
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notify, parent, false));
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		//不使用
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position, List payloads) {
		//payloads是从notifyItemChanged(int, Object)中，或从notifyItemRangeChanged(int, int, Object)中传进来的Object集合
		//如果payloads不为空并且viewHolder已经绑定了旧数据了，那么adapter会使用payloads参数进行布局刷新
		//如果payloads为空，adapter就会重新绑定数据，也就是刷新整个item
		PicUrls.BasicPicBean bean = mDatas.get(holder.getAdapterPosition());

		if (payloads.isEmpty()) {//为空，即不是调用notifyItemChanged(position,payloads)后执行的，也即在初始化时执行的
			holder.tv.setText(bean.name);
			holder.count.start(bean.url.hashCode() + 1000L * 60 * 60 * 24 * 365 * 500 - System.currentTimeMillis());
			Glide.with(context).load(bean.url)
					.dontAnimate()
					//.diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
					.into(holder.iv);
		} else if (payloads.size() > 0 && payloads.get(0) instanceof Integer) {
			//不为空，即调用notifyItemChanged(position,payloads)后执行的，可以在这里获取payloads中的数据进行局部刷新
			int type = (int) payloads.get(0);// 刷新哪个部分 标志位
			switch (type) {
				case NOTIFY_TV:
					holder.tv.setText(bean.name);//只刷新tv
					break;
				case NOTIFY_ET:
					holder.count.start(bean.url.hashCode() - System.currentTimeMillis());//只刷新et
					break;
				case NOTIFY_IV:
					Glide.with(context).load(bean.url).dontAnimate().into(holder.iv);//只刷新iv
					break;
			}
		}

		// 如果设置了回调，则设置点击事件
		holder.itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickLitener != null) mOnItemClickLitener.onItemClick(holder.itemView, holder.getAdapterPosition());
			}
		});
		holder.itemView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (mOnItemClickLitener != null) mOnItemClickLitener.onItemLongClick(holder.itemView, holder.getAdapterPosition());
				return false;
			}
		});
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	/**
	 * 添加并更新数据，同时具有动画效果
	 */
	public void addDataAt(int position, PicUrls.BasicPicBean data) {
		mDatas.add(position, data);
		notifyItemInserted(position);//更新数据集，注意如果用adapter.notifyDataSetChanged()将没有动画效果
	}

	/**
	 * 移除并更新数据，同时具有动画效果
	 */
	public void removeDataAt(int position) {
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	public void setOnItemClickLitener(MyOnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		CountdownView count;
		TextView tv;
		ImageView iv;

		public MyViewHolder(View view) {
			super(view);
			count = (CountdownView) view.findViewById(R.id.count);
			tv = (TextView) view.findViewById(R.id.tv_name);
			iv = (ImageView) view.findViewById(R.id.iv_head);
		}
	}
}