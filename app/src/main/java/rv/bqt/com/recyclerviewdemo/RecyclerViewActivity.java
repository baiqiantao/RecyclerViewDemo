package rv.bqt.com.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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

	private RecyclerView mRecyclerView;
	private MyRecyclerViewAdapter mRecyclerViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecyclerView = (RecyclerView) findViewById(R.id.rv);
		init();
	}
	
	protected void init() {
		List<String> mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++) {
			mDatas.add("" + (char) i);
		}
		mRecyclerViewAdapter = new MyRecyclerViewAdapter(this, mDatas);
		mRecyclerViewAdapter.setOnItemClickLitener(this);
		mRecyclerViewAdapter.setMargins(5);//addItemDecoration一会单独拿出来说

		mRecyclerView.setAdapter(mRecyclerViewAdapter);//设置adapter
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));//设置布局管理器
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
		int type = getIntent().getIntExtra("type", 0);
		switch (type) {
			case 0:
				mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//添加间隔线
				mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));//添加间隔线
				break;
			case 1:
				mRecyclerViewAdapter.addDataAt(position, "添加一条数据\nposition=" + position);
				mRecyclerView.scrollToPosition(position);
				break;
			case 2:
				mRecyclerViewAdapter.removeDataAt(position);
				mRecyclerView.scrollToPosition(position);//删除最后一条数据时也不会报错
				break;
			case 3:
				mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
				break;
			case 4:
				mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.HORIZONTAL, false));//横向的GridView
				break;
			case 5://Staggered：错列的，叉排的
				mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1 + new Random().nextInt(8), StaggeredGridLayoutManager.HORIZONTAL));//行
				mRecyclerViewAdapter.setRandomWidth(true);
				break;
			case 6:
				mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1 + new Random().nextInt(5), StaggeredGridLayoutManager.VERTICAL));//列
				mRecyclerViewAdapter.setRandomHeight(true);
				break;
		}
	}
}