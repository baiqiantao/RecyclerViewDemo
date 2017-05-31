package rv.bqt.com.recyclerviewdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BRVAHActivity extends Activity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
	private List<String> mDatas;
	private RecyclerView mRecyclerView;
	private PullToRefreshAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	private static final int TOTAL_COUNTER = 18;
	private static final int PAGE_SIZE = 6;
	private static final int DELAY_MILLIS = 1000;
	private int mCurrentCounter = 0;

	private boolean isErr;
	private boolean mLoadMoreEndGone = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));

		mRecyclerView = (RecyclerView) findViewById(R.id.rv);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

		initDate();
		initAdapter();

		ImageView headView = new ImageView(this);
		headView.setImageResource(R.mipmap.ic_launcher);
		mAdapter.addHeaderView(headView);
	}

	private void initDate() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++) {
			mDatas.add("" + (char) i);
		}
	}

	private void initAdapter() {
		mAdapter = new PullToRefreshAdapter();
		mAdapter.setOnLoadMoreListener(this, mRecyclerView);
		mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);//动画
		mAdapter.setPreLoadNumber(3);//预加载
		mRecyclerView.setAdapter(mAdapter);
		mCurrentCounter = mAdapter.getData().size();

		//item点击事件
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Toast.makeText(BRVAHActivity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
			}
		});
		//item中View的点击事件
		mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				Toast.makeText(BRVAHActivity.this, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onLoadMoreRequested() {
		mSwipeRefreshLayout.setEnabled(false);
		if (mAdapter.getData().size() < PAGE_SIZE) {
			mAdapter.loadMoreEnd(true);
		} else {
			if (mCurrentCounter >= TOTAL_COUNTER) {
				//pullToRefreshAdapter.loadMoreEnd();//default visible
				mAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
			} else {
				if (isErr) {
					mAdapter.addData("新数据");
					mCurrentCounter = mAdapter.getData().size();
					mAdapter.loadMoreComplete();
				} else {
					isErr = true;
					Toast.makeText(BRVAHActivity.this, "错误", Toast.LENGTH_LONG).show();
					mAdapter.loadMoreFail();
				}
			}
			mSwipeRefreshLayout.setEnabled(true);
		}
	}

	@Override
	public void onRefresh() {
		mAdapter.setEnableLoadMore(false);//禁止加载
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mAdapter.setNewData(mDatas);
				isErr = false;
				mCurrentCounter = PAGE_SIZE;
				mSwipeRefreshLayout.setRefreshing(false);
				mAdapter.setEnableLoadMore(true);//启用加载
			}
		}, DELAY_MILLIS);
	}

	class PullToRefreshAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
		public PullToRefreshAdapter() {
			super(R.layout.item2, mDatas);
		}

		@Override
		protected void convert(BaseViewHolder helper, String item) {
			helper.setText(R.id.id_num, item)
					.setImageResource(R.id.iv, R.drawable.add_icon)
					.addOnClickListener(R.id.iv);
		}
	}
}
