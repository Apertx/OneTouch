package jojo.touch;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;

public class ErrorsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(getSharedPreferences("settings", MODE_PRIVATE).getInt("theme", android.R.style.Theme_Material_Light));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.errors);
		final TextView text = findViewById(R.id.error_disp);
		findViewById(R.id.er1).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("Проблемы с глюкометром.\n\nНЕ ПОЛЬЗУЙТЕСЬ глюкометром.");
				}
			});
		findViewById(R.id.er2).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("Использованная тест-полоска.\n\nИспользуйте новую тест-полоску.");
				}
			});
		findViewById(R.id.er3).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("Образец был нанесен до того, как глюкометр был готов к работе.\n\nИспользуйте новую тест-полоску.");
				}
			});
		findViewById(R.id.er4).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("Тест-полоска повреждена.\n\nИспользуйте новую тест-полоску.");
				}
			});
		findViewById(R.id.er5).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("На полоску было нанесено недостаточное количество крови.\n\nИспользуйте новую тест-полоску.");
				}
			});
		findViewById(R.id.er6).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("Возможно, вы не нанесли на полоску образец крови.\n\nИспользуйте новую тест-полоску.");
				}
			});
		findViewById(R.id.hit).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("Глюкометр перегрелся (температура более 44°C) и не может правильно работать.\n\nПеренесите глюкометр и тест-полоски в более прохладное место.");
				}
			});
		findViewById(R.id.lot).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					text.setText("Глюкометр чрезмерно охладился (температура ниже 10°C) и не может правильно работать.\n\nПеренесите глюкометр и тест-полоски в более теплое место.");
				}
			});
	}
	
}
