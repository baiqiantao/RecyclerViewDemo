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
		String[] array = {"基础",
				"ADD_DATA",
				"DELETE_DATA",
				"LISTVIEW",
				"GRIDVIEW",
				"STAGGERED_HORIZONTAL_GRIDVIEW",
				"STAGGERED_VERTICAL_GRIDVIEW",
				"STAGGERED_ADAPTER",
				"RECYCLERVIEW_ADAPTER",};
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList(array))));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, RecyclerViewActivity.class);
		switch (position) {
			case 0:
				break;
			case 1:
				intent.putExtra("type", RecyclerViewActivity.ADD_DATA);
				break;
			case 2:
				intent.putExtra("type", RecyclerViewActivity.DELETE_DATA);
				break;
			case 3:
				intent.putExtra("type", RecyclerViewActivity.LISTVIEW);
				break;
			case 4:
				intent.putExtra("type", RecyclerViewActivity.GRIDVIEW);
				break;
			case 5:
				intent.putExtra("type", RecyclerViewActivity.STAGGERED_HORIZONTAL_GRIDVIEW);
				break;
			case 6:
				intent.putExtra("type", RecyclerViewActivity.STAGGERED_VERTICAL_GRIDVIEW);
				break;
			case 7:
				intent.putExtra("type", RecyclerViewActivity.STAGGERED_ADAPTER);
				break;
			case 8:
				intent.putExtra("type", RecyclerViewActivity.RECYCLERVIEW_ADAPTER);
				break;
		}
		startActivity(intent);
	}
}