package com.noesgoes.itend.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OrderDbAdapter {
	private static final String TAG = "OrderDbAdapter";
	
	private OrderDbHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private final Context mContext;

	private static final String DB_NAME = "orderdata";
	private static final String ORDER_TABLE = "orders";
	private static final int DB_VERSION = 2;
	
	public static final String KEY_ID = "_id";
	public static final String KEY_DRINK_NAME = "drink_name";
	public static final String KEY_DRINK_DESCRIPTION = "drink_description";
	public static final String KEY_COST = "cost";
	
	private static final String DB_CREATE =
			"CREATE TABLE " + ORDER_TABLE 
			+ " (" + KEY_ID + " integer primary key autoincrement, "
			+ KEY_DRINK_NAME + " text not null, " 
			+ KEY_DRINK_DESCRIPTION + " text not null, "
			+ KEY_COST + " text not null);";
	
	private static class OrderDbHelper extends SQLiteOpenHelper {
		
		OrderDbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS drinks");
			onCreate(db);
		}	
	}
	
	/**
	 * Constructor - stores context for DB creation
	 * 
	 * @param context the context in which to create the DB
	 */
	public OrderDbAdapter(Context context) {
		this.mContext = context;
	}

	/**
	 * Open or create the drink database.
	 * 
	 * @return a reference to itself, to allow chaining
	 * @throws SQLException if database couldn't be opened or created
	 */
	public OrderDbAdapter open() throws SQLException {
		mDbHelper = new OrderDbHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * Add a drink to order
	 * 
	 * @param name the drink name
	 * @param cost the cost of the drink
	 * @return the ID or -1 if the insert failed
	 */
	public long addDrinkToOrder(String name, String description, String cost) {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(KEY_DRINK_NAME, name);
		initialValues.put(KEY_DRINK_DESCRIPTION, description);
		initialValues.put(KEY_COST, cost);

		return mDb.insert(ORDER_TABLE, null, initialValues);
	}
	
	/**
	 * Returns a list of drinks of type type
	 * 
	 * @param type the type of drink
	 * @return cursor over all drinks of type type
	 */
	public Cursor getAllDrinks() {
		String[] columns = {KEY_DRINK_NAME, KEY_DRINK_DESCRIPTION, KEY_COST};
		return mDb.query(ORDER_TABLE, columns, null, null, null, null, null);
	}
}
