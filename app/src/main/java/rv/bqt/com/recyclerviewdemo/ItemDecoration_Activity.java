package rv.bqt.com.recyclerviewdemo;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ItemDecoration_Activity extends Activity implements MyOnItemClickLitener {

	private RecyclerView mRecyclerView;
	private RV_Adapter mRecyclerViewAdapter;
	private RecyclerView.ItemDecoration decoration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecyclerView = (RecyclerView) findViewById(R.id.rv);
		init();
	}
	
	protected void init() {
		List<String> mDatas = new ArrayList<String>();
		mDatas.add("0.添加VERTICAL间隔线");
		mDatas.add("1.添加HORIZONTAL间隔线");
		mDatas.add("2.移除VERTICAL间隔线");
		mDatas.add("3.recreate");
		mDatas.add("4.Horizontal_Divider_ItemDecoration");
		mDatas.add("5.Vertical_Divider_ItemDecoration");

		mDatas.add("6.不包含边界的空白间隔");
		mDatas.add("7.包含边界的空白间隔");
		mDatas.add("8.自定义图片间隔 true,false");
		mDatas.add("9.自定义颜色间隔 false,true");
		mDatas.add("10.显示但不绘制边界间隔");
		mDatas.add("11.不绘制左右边界间隔");
		mDatas.add("12.不绘制上下边界间隔");
		for (int i = 'A'; i < 'z'; i++) {
			mDatas.add("" + (char) i);
		}
		mRecyclerViewAdapter = new RV_Adapter(this, mDatas);
		mRecyclerViewAdapter.setOnItemClickLitener(this);
		decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

		mRecyclerView.setAdapter(mRecyclerViewAdapter);//设置adapter
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));//设置布局管理器
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
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
			case 0:
				mRecyclerView.addItemDecoration(decoration);//添加间隔线
				break;
			case 1:
				mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
				break;
			case 2:
				mRecyclerView.removeItemDecoration(decoration);//移除间隔线，即使没有添加也可以移除
				break;
			case 3:
				recreate();
				break;
			case 4://For layout manager having 【vertical orientation】 to draw horizontal divider
				mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)//不能和Vertical*同时使用
						.margin(20)//距离【左右】边界的距离margin(leftMargin, rightMargin)
						.size(5)//高度
						.color(0xff0000ff)
						//.drawable(R.mipmap.ic_launcher)//间隔线不使用颜色而使用drawable，color()会把drawable()覆盖掉
						.positionInsideItem(false)//间隔线是否绘制在item内容的上面，默认为false
						.showLastDivider()//默认不显示最后一条间隔线
						.build());
				break;
			case 5://For layout manager having 【horizontal orientation】 to draw vertical divider
				mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.HORIZONTAL, false));
				mRecyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(this)//不能和Horizontal*同时使用
						.margin(20)//距离【上下】边界的距离margin(leftMargin, rightMargin)
						.size(5).color(0xff0000ff).build());
				break;
			case 6:
				mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4).spaceSize(1).build());
				break;
			case 7:
				mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4).spaceSize(50)
						.includeLREdge(true).includeTBEdge(true).build());//
				break;
			case 8:
				mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4).spaceSize(12)
						.includeLREdge(true)
						.mDivider(getResources().getDrawable(R.mipmap.ic_launcher)).build());//
				break;
			case 9:
				mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4).spaceSize(6)
						.includeTBEdge(true)
						.mDivider(new ColorDrawable(0x880000ff)).build());//
				break;
			case 10:
				mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4).spaceSize(50)
						.includeLREdge(true).includeTBEdge(true).drawLREdge(false).drawTBEdge(false)
						.mDivider(new ColorDrawable(0x880000ff)).build());//
				break;
			case 11:
				mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4).spaceSize(50)
						.includeLREdge(true).includeTBEdge(true).drawLREdge(false)
						.mDivider(new ColorDrawable(0x880000ff)).build());//
				break;
			case 12:
				mRecyclerView.addItemDecoration(new GridItemDecoration.Builder().spanCount(4).spaceSize(50)
						.includeLREdge(true).includeTBEdge(true).drawTBEdge(false)
						.mDivider(new ColorDrawable(0x880000ff)).build());//
				break;
		}
	}
}