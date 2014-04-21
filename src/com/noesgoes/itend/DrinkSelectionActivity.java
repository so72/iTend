package com.noesgoes.itend;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class DrinkSelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drink_selection);
		// Show the Up button in the action bar.
		setupActionBar();
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

	public void onBeerClicked(View view) {
		Intent intent = new Intent(this, BeerSelectionActivity.class);
		startActivity(intent);
	}
	
	public void onWineClicked(View view) {
		Intent intent = new Intent(this, WineSelectionActivity.class);
		startActivity(intent);
	}
	
	public void onMixedClicked(View view) {
		Intent intent = new Intent(this, MixedSelectionActivity.class);
		startActivity(intent);
	}
}
