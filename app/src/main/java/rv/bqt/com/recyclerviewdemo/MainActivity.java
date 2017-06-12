package rv.bqt.com.recyclerviewdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"基础，点击下试试",
				"添加",
				"删除",
				"ListView",
				"横向的GridView",
				"横向瀑布流",
				"纵向瀑布流",
				"分割线 ItemDecoration",
				"BaseRecyclerViewAdapterHelper基本使用",
				"RecyclerView数据刷新(有福利)",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (position <= 6) {
			Intent intent = new Intent(this, RV_Activity.class);
			intent.putExtra("type", position);
			startActivity(intent);
		} else if (position == 7) {
			startActivity(new Intent(this, ItemDecoration_Activity.class));
		} else if (position == 8) {
			startActivity(new Intent(this, BRVAH_Activity.class));
		} else if (position == 9) {
			startActivity(new Intent(this, Notify_Activity.class));
		}
	}
}