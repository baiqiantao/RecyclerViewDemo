package rv.bqt.com.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewActivity extends Activity implements MyOnItemClickLitener {
	
	public static final int ADD_DATA = 1;
	public static final int DELETE_DATA = 2;
	public static final int LISTVIEW = 3;
	public static final int GRIDVIEW = 4;
	public static final int STAGGERED_HORIZONTAL_GRIDVIEW = 5;
	public static final int STAGGERED_VERTICAL_GRIDVIEW = 6;
	public static final int STAGGERED_ADAPTER = 7;
	public static final int RECYCLERVIEW_ADAPTER = 8;

	private RecyclerView mRecyclerView;
	private List<String> mDatas;//数据
	private List<Integer> mHeights;//高度
	private MyRecyclerViewAdapter mRecyclerViewAdapter;//适配器
	private MyStaggeredAdapter mStaggeredAdapter;
	private RecyclerView.ItemDecoration decoration1;//分割线
	private RecyclerView.ItemDecoration decoration2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecyclerView = (RecyclerView) findViewById(R.id.rv);
		initData();
		initRecylerView();
	}
	
	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++) {
			mDatas.add("" + (char) i);
		}
		
		mHeights = new ArrayList<Integer>();
		for (int i = 0; i < mDatas.size(); i++) {
			mHeights.add((int) (100 + Math.random() * 500));
		}
	}
	
	private void initRecylerView() {
		mRecyclerViewAdapter = new MyRecyclerViewAdapter(this, mDatas);
		mStaggeredAdapter = new MyStaggeredAdapter(this, mDatas, mHeights);
		mRecyclerViewAdapter.setOnItemClickLitener(this);
		mStaggeredAdapter.setOnItemClickLitener(this);
		decoration1 = new DividerItemDecoration(this, 0);
		decoration2 = new DividerItemDecoration(this, 1);

		mRecyclerView.setPadding(10, 10, 10, 10);
		mRecyclerView.setAdapter(mRecyclerViewAdapter);//设置adapter
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));//设置布局管理器
		mRecyclerView.addItemDecoration(decoration1);//添加一个分割线
		mRecyclerView.addItemDecoration(decoration2);//还可以再添加一个分割线
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
	}

	@Override
	public void onItemClick(View view, int position) {
		Toast.makeText(this, position + " 被点击了", Toast.LENGTH_SHORT).show();
		reInitRecylerView();
	}
	
	@Override
	public void onItemLongClick(View view, int position) {
		Toast.makeText(this, position + "被长按了", Toast.LENGTH_SHORT).show();
	}

	private void reInitRecylerView() {
		int type = getIntent().getIntExtra("type", 0);
		if (type == 0) return;

		mRecyclerView.removeItemDecoration(decoration1);//移除分割线，即使没有显示也可以移除
		mRecyclerView.removeItemDecoration(decoration2);
		switch (type) {
			case ADD_DATA:
				mRecyclerViewAdapter.addData(new Random().nextInt(5));
				break;
			case DELETE_DATA:
				mRecyclerViewAdapter.removeData(new Random().nextInt(5));
				break;
			//通过RecyclerView去实现ListView、GridView、瀑布流的效果基本上没有什么区别，仅仅通过设置不同的LayoutManager即可实现
			case LISTVIEW:
				mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
				break;
			case GRIDVIEW:
				mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
				break;
			case STAGGERED_HORIZONTAL_GRIDVIEW://Staggered：错列的，叉排的。
				mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));//5行
				break;
			case STAGGERED_VERTICAL_GRIDVIEW:
				mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));//3列
				break;
			case STAGGERED_ADAPTER:
				mRecyclerView.setAdapter(mStaggeredAdapter);
				break;
			case RECYCLERVIEW_ADAPTER:
				mRecyclerView.setAdapter(mRecyclerViewAdapter);
				break;
		}
	}

}