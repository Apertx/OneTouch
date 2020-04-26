package jojo.touch;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.view.*;

public class SettingsActivity extends Activity {
	int style;
	EditText max_res;
	EditText min_res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		style = getSharedPreferences("settings", MODE_PRIVATE).getInt("theme", android.R.style.Theme_Material_Light);
		setTheme(style);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		final String themes[] = {
			"Светлая тема",
			"Темная тема",
			"Светлая Holo",
			"Темная Holo",
		};
		final int theme[] = {
			android.R.style.Theme_Material_Light,
			android.R.style.Theme_Material,
			android.R.style.Theme_Holo_Light,
			android.R.style.Theme_Holo,
		};
		max_res = findViewById(R.id.max_result);
		min_res = findViewById(R.id.min_result);
		max_res.setText(String.valueOf(getSharedPreferences("settings", MODE_PRIVATE).getFloat("maxResult", (float) 10.0)));
		min_res.setText(String.valueOf(getSharedPreferences("settings", MODE_PRIVATE).getFloat("minResult", (float) 3.9)));
		final Spinner spinner = findViewById(R.id.themes);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, themes);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		for (byte i = 0; i < 3; i++)
			if (style == theme[i])
				spinner.setSelection(i);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
					style = theme[p3];
					spinner.setPrompt(themes[p3]);
				}
				@Override
				public void onNothingSelected(AdapterView<?> p1) {}
			});
	}

	@Override
	protected void onStop() {
		String max_result = max_res.getText().toString();
		String min_result = min_res.getText().toString();
		if (max_result.length() == 0)
			max_result = "10.0";
		if (!max_result.contains("."))
			max_result += ".0";
		if (min_result.length() == 0)
			min_result = "3.9";
		if (!min_result.contains("."))
			min_result += ".0";
		getSharedPreferences("settings", MODE_PRIVATE).edit().putInt("theme", style).putFloat("maxResult", Float.parseFloat(max_result)).putFloat("minResult", Float.parseFloat(min_result)).apply();
		super.onStop();
	}
}
