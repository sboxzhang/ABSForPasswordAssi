package com.sbox.absforpwdassi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper 
{

	private static final String DATABASE_NAME = "my_pwd_asst.db";
	private static final int DATABASE_VERSION = 1;
	
	public DBHelper(Context context)
	{
		//CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL("CREATE TABLE IF NOT EXISTS PwdInfo" +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, account VARCHAR, pwd VARCHAR, info TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS UserInfo" +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, account VARCHAR, pwd VARCHAR, info TEXT)");
		
		//初始化数据
		db.execSQL("INSERT INTO USERINFO VALUES(null,'N','','FLAG_REGISTER')");
	}

	//如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		//db.execSQL("INSERT INTO USERINFO VALUES(1,'N','','FLAG_REGISTER')");
		//db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
	}
}

