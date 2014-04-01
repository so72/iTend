package com.noesgoes.itend;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
//import android.view.Menu;

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

}
