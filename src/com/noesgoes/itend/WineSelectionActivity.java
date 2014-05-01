package com.noesgoes.itend;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.noesgoes.itend.db.DrinkDbAdapter;
import com.noesgoes.itend.db.OrderDbAdapter;

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
        String[] from = new String[]{DrinkDbAdapter.KEY_NAME, DrinkDbAdapter.KEY_DESCRIPTION, DrinkDbAdapter.KEY_COST};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1, R.id.text2, R.id.text3};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter beers = 
            new SimpleCursorAdapter(this, R.layout.drink_row, notesCursor, from, to);
        setListAdapter(beers);
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Cursor wineCursor = mDbHelper.getDrinkByID(id);
		startManagingCursor(wineCursor);
		
		String wineName = wineCursor.getString(wineCursor.getColumnIndex("name"));
		String wineCost = wineCursor.getString(wineCursor.getColumnIndex("cost"));
		String wineDescription = wineCursor.getString(wineCursor.getColumnIndex("description"));

		OrderDbAdapter orderDbAdapter = new OrderDbAdapter(this);
		orderDbAdapter.open();
		orderDbAdapter.addDrinkToOrder(wineName, wineDescription, wineCost);
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
