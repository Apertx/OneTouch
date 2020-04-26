package jojo.touch;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.io.*;
import java.util.*;

public class HistoryActivity extends Activity {
	class State {
		String date;
		String time;
		String result;
	}
	ArrayAdapter adapter;
	List<State> elements;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(getSharedPreferences("settings", MODE_PRIVATE).getInt("theme", android.R.style.Theme_Material_Light));
		super.onCreate(savedInstanceState);
		String[] element = getSharedPreferences("settings", MODE_PRIVATE).getString("history", "").split(",");
		final float maxResult = getSharedPreferences("settings", MODE_PRIVATE).getFloat("maxResult", (float) 10.0);
		final float minResult = getSharedPreferences("settings", MODE_PRIVATE).getFloat("minResult", (float) 3.9);
		elements = new ArrayList<State>();
		if (element[0].length() != 0)
			for (String str : element) {
				State stat = new State();
				stat.date = str.split(" ")[0];
				stat.time = str.split(" ")[1];
				stat.result = str.split(" ")[2];
				elements.add(stat);
			}
		ListView list = new ListView(this);
		adapter = new ArrayAdapter<State>(this, android.R.layout.simple_list_item_1, elements) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null){
					convertView = getLayoutInflater().inflate(R.layout.history_item, null);
				}
				TextView date = convertView.findViewById(R.id.history_date);
				date.setText(elements.get(position).date);
				TextView time = convertView.findViewById(R.id.history_time);
				time.setText(elements.get(position).time);
				TextView result = convertView.findViewById(R.id.history_result);
				result.setText(elements.get(position).result);
				if (Float.parseFloat(elements.get(position).result) >= maxResult)
					result.setTextColor(Color.RED);
				else if (Float.parseFloat(elements.get(position).result) <= minResult)
					result.setTextColor(Color.BLUE);
				else
					result.setTextColor(Color.rgb(00, 0xaa, 00));
				return convertView;
			}
		};
		list.setAdapter(adapter);
		setContentView(list);
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4) {
					startActivityForResult(new Intent(HistoryActivity.this, EditActivity.class).putExtra("element", elements.get(p3).date + " " + elements.get(p3).time + " " + elements.get(p3).result).putExtra("number", p3), 3);
					return false;
				}
			});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (data.getStringExtra("element").length() == 0)
				elements.remove(data.getIntExtra("number", 0));
			else {
				String str = data.getExtras().getString("element");
				State stat = new State();
				stat.date = str.split(" ")[0];
				stat.time = str.split(" ")[1];
				stat.result = str.split(" ")[2];
				elements.set(data.getIntExtra("number", 0), stat);
			}
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.history, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_delete:
				new AlertDialog.Builder(HistoryActivity.this).setTitle("Удалить историю?").
				setMessage("Это приведет к удалению всей истории").
				setNegativeButton("Нет", null).
					setPositiveButton("Да", new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface p1, int p2) {
							elements.clear();
							adapter.notifyDataSetChanged();
						}
					}).show();
				break;
			case R.id.menu_export:
				try {
					StringBuilder element = new StringBuilder();
					for (State str : elements.toArray(new State[0]))
						element.append("," + str.date + " " + str.time + " " + str.result);
					if (element.length() != 0)
						element.deleteCharAt(0);
					OutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+ new Date().getTime() + ".otl"));
					fos.write(element.toString().getBytes());
					fos.flush();
					fos.close();
				} catch (Exception e) {}}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		super.onStop();
		StringBuilder element = new StringBuilder();
		for (State str : elements.toArray(new State[0]))
			element.append("," + str.date + " " + str.time + " " + str.result);
		if (element.length() != 0)
			element.deleteCharAt(0);
		getSharedPreferences("settings", MODE_PRIVATE).edit().putString("history", element.toString()).apply();
	}
}
