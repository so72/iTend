package com.noesgoes.itend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onBuyDrink(View view) {
		Intent selectDrink = new Intent(this, DrinkSelectionActivity.class);
		startActivity(selectDrink);
	}
	
	public void onPayTab(View view) {
		Intent payTab = new Intent(this, PayTabActivity.class);
		startActivity(payTab);
	}
	
	public void onGetHelp(View view) {
		Intent helpScreen = new Intent(this, GetHelpActivity.class);
		startActivity(helpScreen);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_admin:
			Intent admin = new Intent(this, AddToMenuActivity.class);
			startActivity(admin);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
