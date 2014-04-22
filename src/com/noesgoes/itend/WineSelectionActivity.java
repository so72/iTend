package com.noesgoes.itend;

import com.noesgoes.itend.db.DrinkDbAdapter;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class WineSelectionActivity extends ListActivity {

	private DrinkDbAdapter mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beer_selection);
		mDbHelper = new DrinkDbAdapter(this);
        mDbHelper.open();
        fillData();
        registerForContextMenu(getListView());
		
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
    private void fillData() {
        Cursor notesCursor = mDbHelper.getAllDrinks(1);
        startManagingCursor(notesCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{DrinkDbAdapter.KEY_NAME};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter beers = 
            new SimpleCursorAdapter(this, R.layout.drink_row, notesCursor, from, to);
        setListAdapter(beers);
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
