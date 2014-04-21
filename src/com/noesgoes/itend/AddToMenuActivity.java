package com.noesgoes.itend;

import java.math.BigDecimal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.noesgoes.itend.db.DrinkDbAdapter;

public class AddToMenuActivity extends Activity {
	private static final String TAG = "AddToMenuActivity";

	private Spinner typeSpinner;
	private EditText nameText;
	private EditText descText;
	private EditText costText;
	private DrinkDbAdapter mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_to_menu);
		
		mDbHelper = new DrinkDbAdapter(this);
		mDbHelper.open();
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		typeSpinner = (Spinner) findViewById(R.id.drink_type);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.drink_types_array, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		typeSpinner.setAdapter(adapter);
		
		nameText = (EditText) findViewById(R.id.drink_name);
		descText = (EditText) findViewById(R.id.drink_description);
		costText = (EditText) findViewById(R.id.drink_cost);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	public void onAddToMenuClicked(View view) {
		String name = nameText.getText().toString();
		String desc = descText.getText().toString();
		BigDecimal cost = new BigDecimal(costText.getText().toString());
		int type = typeSpinner.getSelectedItemPosition();
		
		// TODO: Error checking? make sure values aren't blank
		
		mDbHelper.createDrinkListing(name, desc, cost, type);
		
		NavUtils.navigateUpFromSameTask(this);
	}
	
}
