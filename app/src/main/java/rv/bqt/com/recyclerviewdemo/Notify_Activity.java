package rv.bqt.com.recyclerviewdemo;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Notify_Activity extends Activity implements MyOnItemClickLitener {

	private RecyclerView mRecyclerView;
	private Notify_Adapter mAdapter;
	private ArrayList<PicUrls.BasicPicBean> beans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((SwipeRefreshLayout) findViewById(R.id.swipeLayout)).setEnabled(false);
		mRecyclerView = (RecyclerView) findViewById(R.id.rv);
		initList();
		initView();
	}
	
	protected void initView() {
		mAdapter = new Notify_Adapter(this, beans);
		mAdapter.setOnItemClickLitener(this);
		mRecyclerView.setAdapter(mAdapter);//设置adapter

		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));//设置布局管理器
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
		mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4)
				.spaceSize(1).mDivider(new ColorDrawable(0xffff0000)).build());
	}

	private void initList() {
		beans = PicUrls.getPicList(PicUrls.BIG_BEANS_2);
		beans.get(0).name = "刷新当前，notifyItemChanged(int)";
		beans.get(1).name = "全部刷新，notifyDataSetChanged()";
		beans.get(2).name = "从此位置开始刷新2个，notifyItemRangeChanged";
		beans.get(3).name = "插入一个并自动刷新，notifyItemInserted";
		beans.get(4).name = "只更改数据源，这样当然不会刷新UI";
		beans.get(5).name = "插入一个并刷新当前，notifyItemChanged";
		beans.get(6).name = "局部刷新，tv";
		beans.get(7).name = "局部刷新，et";
		beans.get(8).name = "局部刷新，iv";
	}

	@Override
	public void onItemClick(View view, int position) {
		Toast.makeText(this, position + " 被点击了", Toast.LENGTH_SHORT).show();
		reInit(position);
	}
	
	@Override
	public void onItemLongClick(View view, int position) {
		Toast.makeText(this, position + "被长按了", Toast.LENGTH_SHORT).show();
	}

	private void reInit(int position) {
		switch (position) {
			default:
				mAdapter.notifyItemChanged(position);//刷新指定一个。一定会闪
				break;
			case 1:
				mAdapter.notifyDataSetChanged();//全部刷新。基本不会闪，刚开始个别可能会闪
				break;
			case 2:
				mAdapter.notifyItemRangeChanged(position, 2);//从指定位置开始刷新指定个。一定会闪
				break;
			case 3:
				beans.add(position, new PicUrls.BasicPicBean("http://img.mmjpg.com/2015/74/1.jpg", "新插入的图片1", 1));
				mAdapter.notifyItemInserted(position);//插入一个并刷新，正常
				break;
			case 4://只更改数据源，这样当然不会刷新UI
				beans.add(position, new PicUrls.BasicPicBean("http://img.mmjpg.com/2015/74/2.jpg", "新插入的图片2", 2));
				break;
			case 5://
				beans.add(position, new PicUrls.BasicPicBean("http://img.mmjpg.com/2015/74/3.jpg", "新插入的图片3", 3));
				mAdapter.notifyItemChanged(position);//这样只会导致当前item刷新，而不会刷新其他item，当然是不行的
				break;
			case 6:
				beans.set(position, beans.get(new Random().nextInt(beans.size())));
				mAdapter.notifyItemChanged(position, Notify_Adapter.NOTIFY_TV);//局部刷新
				break;
			case 7:
				beans.set(position, beans.get(new Random().nextInt(beans.size())));
				mAdapter.notifyItemChanged(position, Notify_Adapter.NOTIFY_ET);//局部刷新
				break;
			case 8:
				beans.set(position, beans.get(new Random().nextInt(beans.size())));
				mAdapter.notifyItemChanged(position, Notify_Adapter.NOTIFY_IV);//局部刷新
				break;
		}
	}
}