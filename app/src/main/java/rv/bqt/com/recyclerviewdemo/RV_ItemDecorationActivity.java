package rv.bqt.com.recyclerviewdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
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

public class RV_ItemDecorationActivity extends Activity implements MyOnItemClickLitener {

	private RecyclerView mRecyclerView;
	private MyRecyclerViewAdapter mRecyclerViewAdapter;
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
		mDatas.add("添加VERTICAL间隔线");
		mDatas.add("添加HORIZONTAL间隔线");
		mDatas.add("移除VERTICAL间隔线");
		mDatas.add("recreate");
		mDatas.add("HorizontalDivider_ItemDecoration");
		mDatas.add("VerticalDivider_ItemDecoration");
		mDatas.add("使用Paint");
		for (int i = 'A'; i < 'z'; i++) {
			mDatas.add("" + (char) i);
		}
		mRecyclerViewAdapter = new MyRecyclerViewAdapter(this, mDatas);
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
				Paint paint = new Paint();
				paint.setStrokeWidth(5);
				paint.setColor(Color.BLUE);
				paint.setAntiAlias(true);
				paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
				mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(20).paint(paint).build());
				break;
			case 7:
				break;
		}
	}
}