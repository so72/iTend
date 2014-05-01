package com.noesgoes.itend;

import com.noesgoes.itend.db.DrinkDbAdapter;
import com.noesgoes.itend.db.OrderDbAdapter;
import android.app.ListActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class PayTabActivity extends ListActivity {

	private OrderDbAdapter mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_tab);
		mDbHelper = new OrderDbAdapter(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());

		// Show the Up button in the action bar.
		setupActionBar();
	}
	
    private void fillData() {
        Cursor drinkCursor = mDbHelper.getAllDrinks();
        startManagingCursor(drinkCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{OrderDbAdapter.KEY_DRINK_NAME, OrderDbAdapter.KEY_DRINK_DESCRIPTION, OrderDbAdapter.KEY_COST};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1, R.id.text2, R.id.text3};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter drinks = 
            new SimpleCursorAdapter(this, R.layout.drink_row, drinkCursor, from, to);
        setListAdapter(drinks);
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
}