package jojo.touch;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class EditActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int style = getSharedPreferences("settings", MODE_PRIVATE).getInt("theme", android.R.style.Theme_Material_Light);
		int theme[] = {
			android.R.style.Theme_Material_Light,
			android.R.style.Theme_Material,
			android.R.style.Theme_Holo_Light,
			android.R.style.Theme_Holo,
		};
		int theme_dialog[] = {
			android.R.style.Theme_Material_Light_Dialog_MinWidth,
			android.R.style.Theme_Material_Dialog_MinWidth,
			android.R.style.Theme_Holo_Light_Dialog_MinWidth,
			android.R.style.Theme_Holo_Dialog_MinWidth,
		};
		for (byte i = 0; i < 3; i ++)
			if (style == theme[i])
				setTheme(theme_dialog[i]);
		super.onCreate(savedInstanceState);
		String element[] = getIntent().getExtras().getString("element", "").split(" ");
		setContentView(R.layout.edit);
		final TextView date = findViewById(R.id.edit_date);
		final TextView time = findViewById(R.id.edit_time);
		final TextView result = findViewById(R.id.edit_result);
		date.setText(element[0]);
		date.setHint(element[0]);
		time.setText(element[1]);
		time.setHint(element[1]);
		result.setText(element[2]);
		result.setHint(element[2]);
		findViewById(R.id.edit_ok).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					String res = result.getText().toString();
					if (!res.contains("."))
						res += ".0";
					setResult(RESULT_OK, new Intent().putExtra("element", "" + date.getText() + " " + time.getText() + " " + res).putExtra("number", getIntent().getExtras().getInt("number")));
					finish();
				}
			});
		findViewById(R.id.edit_delete).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					setResult(RESULT_OK, new Intent().putExtra("element", "").putExtra("number", getIntent().getExtras().getInt("number")));
					finish();
				}
			});
	}
}
