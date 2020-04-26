package jojo.touch;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import android.bluetooth.*;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getSharedPreferences("settings", MODE_PRIVATE).getInt("theme", android.R.style.Theme_Material_Light));
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
        final EditText text = new EditText(this);
		text.setEms(3);
		text.setGravity(17);
		text.setRawInputType(2002);
		text.setTextSize(48);
		layout.setGravity(17);
		layout.addView(text);
		setContentView(layout);
		text.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View p1, int p2, KeyEvent p3) {
					if (p3.getAction() == KeyEvent.ACTION_UP && text.getText().length() > 2)
						if (text.getText().charAt(text.length() - 2) == '.') {
							String str = getSharedPreferences("settings", MODE_PRIVATE).getString("history", "");
							Date date = new Date();
							if (str != "")
								str += ",";
							str += ("" + date.getYear() + "/" + date.getMonth() + "/" + date.getDay() + " " + date.getHours() + ":" + date.getMinutes() + " " + text.getText()).substring(1);
							getSharedPreferences("settings", MODE_PRIVATE).edit().putString("history", str).apply();
							Toast.makeText(MainActivity.this, "Данные сохранены: " + text.getText(), 1).show();
							text.setText("");
						}
					return false;
				}
			});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_devices:
				startActivity(new Intent(MainActivity.this, DevicesActivity.class));
				break;
			case R.id.menu_history:
				startActivity(new Intent(MainActivity.this, HistoryActivity.class));
				break;
			case R.id.menu_errors:
				startActivity(new Intent(MainActivity.this, ErrorsActivity.class));
				break;
			case R.id.menu_settings:
				startActivity(new Intent(MainActivity.this, SettingsActivity.class));
				break;
			case R.id.menu_about:
				new AlertDialog.Builder(MainActivity.this).setTitle("О приложении").
					setMessage("Это приложение сделано специально для мамы. Живи долго и счастливо!").
					setPositiveButton("Ок", null).show();
				break;
			case R.id.menu_exit:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
