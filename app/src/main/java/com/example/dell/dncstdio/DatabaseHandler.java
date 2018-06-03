package com.example.dell.dncstdio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context, Object name,
                           Object factory, int version) {
		// TODO Auto-generated constructor stub
		super(context,  DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	String password;

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "Login.db";

	private static final String TABLE_NAME= "register";
	public static final String KEY_ID = "id";
	public static final String FIRST_NAME = " first_name";
	public static final String LAST_NAME = "last_name";
	public static final String EMAIL_ID ="email_id";
	public static final String MOB_NO = "mobile_number";
	public static final String PASSWORD = "password";

	public static final String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + FIRST_NAME + " TEXT,"+ LAST_NAME + " TEXT,"+ EMAIL_ID + " TEXT , "
			+ MOB_NO + " TEXT," + PASSWORD + " TEXT " + ")";
	
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE);	
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

	void addregister(Registerdata registerdata)
	{  
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(FIRST_NAME,registerdata.getfirstName());
		values.put(LAST_NAME, registerdata. getlastName());
		values.put(EMAIL_ID, registerdata.getEmailId());
		values.put(MOB_NO, registerdata.getMobNo());
		values.put(PASSWORD, registerdata.getPassword());
		db.insert(TABLE_NAME, null, values);
		db.close();
	}  
	
	


	String getregister(String username){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_NAME ,null,  "email_id=?",new String[]{username},null, null, null, null);

		if(cursor.getCount()<1){
			cursor.close();
			return "Not Exist";
		}
		else if(cursor.getCount()>=1 && cursor.moveToFirst()){
			
			 password = cursor.getString(cursor.getColumnIndex(PASSWORD));
			cursor.close();
			
		}
		return password;
		

}
	

	public String getDatabaseName() {
		return DATABASE_NAME;
	}

	}
