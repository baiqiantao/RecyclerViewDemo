package rv.bqt.com.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
	private Context context;
	private List<String> mDatas;
	private MyOnItemClickLitener mOnItemClickLitener;

	private int margins = 0;
	private boolean randomWidth = false;
	private boolean randomHeight = false;

	public MyRecyclerViewAdapter(Context context, List<String> mDatas) {
		this.context = context;
		this.mDatas = mDatas;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position) {
		holder.tv.setText(mDatas.get(position));
		RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.tv.getLayoutParams();
		lp.setMargins(margins, margins, margins, margins);//设置边距，这里完全可以放在RecyclerView的addItemDecoration方法中

		//横向时，item的宽度需要设置；纵向时，item的高度需要设置
		if (randomHeight) lp.height = 200 + new Random().nextInt(5) * 100;//**************************************************唯一的区别在这
		if (randomWidth) lp.width = 200 + new Random().nextInt(5) * 100;//***************************************************唯一的区别在这

		holder.tv.setLayoutParams(lp);

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
	public void addDataAt(int position, String data) {
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

	public void setMargins(int margins) {
		this.margins = margins;
	}

	public void setRandomWidth(boolean randomWidth) {
		this.randomWidth = randomWidth;
	}

	public void setRandomHeight(boolean randomHeight) {
		this.randomHeight = randomHeight;
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tv;

		public MyViewHolder(View view) {
			super(view);
			tv = (TextView) view.findViewById(R.id.id_num);
		}
	}
}