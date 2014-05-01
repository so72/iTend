package com.noesgoes.itend.db;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrinkDbAdapter {
	private static final String TAG = "DrinkDbAdapter";
	
	private DrinkDbHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private final Context mContext;
	
	private static final String DB_NAME = "drinkdata";
	private static final String DRINKS_TABLE = "drinks";
	private static final int DB_VERSION = 2;
	
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_COST = "cost";
	public static final String KEY_TYPE = "type";
	
	public static final int BEER_TYPE = 0;
	public static final int WINE_TYPE = 1;
	public static final int MIXED_TYPE = 2;
	
	private static final String DB_CREATE =
			"CREATE TABLE " + DRINKS_TABLE + " (" + KEY_ID + " integer primary key autoincrement, "
			+ KEY_NAME + " text not null, " + KEY_DESCRIPTION + " text not null, "
			+ KEY_COST + " text not null, " + KEY_TYPE + " integer not null);";
	
	private static class DrinkDbHelper extends SQLiteOpenHelper {
		
		DrinkDbHelper(Context context) {
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
	public DrinkDbAdapter(Context context) {
		this.mContext = context;
	}
	
	/**
	 * Open or create the drink database.
	 * 
	 * @return a reference to itself, to allow chaining
	 * @throws SQLException if database couldn't be opened or created
	 */
	public DrinkDbAdapter open() throws SQLException {
		mDbHelper = new DrinkDbHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	/**
	 * Create a new drink listing
	 * 
	 * @param name the drink name
	 * @param description the drink description
	 * @param cost the cost of the drink
	 * @return the ID or -1 if the insert failed
	 */
	public long createDrinkListing(String name, String description, BigDecimal cost, int type) {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_DESCRIPTION, description);
		initialValues.put(KEY_COST, cost.setScale(2, RoundingMode.CEILING).toPlainString());
		initialValues.put(KEY_TYPE, type);
		
		return mDb.insert(DRINKS_TABLE, null, initialValues);
	}
	
	/**
	 * Returns a list of drinks of type type
	 * 
	 * @param type the type of drink
	 * @return cursor over all drinks of type type
	 */
	public Cursor getAllDrinks(int type) {
		String[] args = { type + "" };
		String[] columns = {KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_COST, KEY_TYPE};
		return mDb.query(DRINKS_TABLE, columns, KEY_TYPE + "=?", args, null, null, null);
	}
	
	public Cursor getDrinkByID(long id) {
		String[] args = {id + ""};
		String[] columns = {KEY_NAME, KEY_DESCRIPTION, KEY_COST};
		Cursor cursor = mDb.query(DRINKS_TABLE, columns, KEY_ID + "=?", args, null, null, null);
		cursor.moveToFirst();
		return cursor;
	}

}
