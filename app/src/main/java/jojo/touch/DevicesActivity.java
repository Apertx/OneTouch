package jojo.touch;
import android.app.*;
import android.bluetooth.*;
import android.content.*;
import android.os.*;
import java.util.*;
import android.widget.*;

public class DevicesActivity extends Activity {
	BluetoothAdapter bluetoothAdapter;
	ListView list;
	List<String> devices;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(getSharedPreferences("settings", MODE_PRIVATE).getInt("theme", android.R.style.Theme_Material_Light));
		super.onCreate(savedInstanceState);
		devices = new ArrayList<String>();
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter.isEnabled()) {
			bluetoothAdapter.startDiscovery();
			registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
		} else
			startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 10);
		list = new ListView(this);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, devices);
		list.setAdapter(adapter);
		setContentView(list);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (!devices.contains(device.getName())) {
					devices.add(device.getName());
					String deviceHardwareAddress = device.getAddress();
					adapter.notifyDataSetChanged();
				}
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			bluetoothAdapter.startDiscovery();
			registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(receiver);
		bluetoothAdapter.cancelDiscovery();
	}
}
