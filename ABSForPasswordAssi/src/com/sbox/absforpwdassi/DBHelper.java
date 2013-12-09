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
		//CursorFactory����Ϊnull,ʹ��Ĭ��ֵ
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL("CREATE TABLE IF NOT EXISTS PwdInfo" +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, account VARCHAR, pwd VARCHAR, info TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS UserInfo" +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, account VARCHAR, pwd VARCHAR, info TEXT)");
		
		//��ʼ������
		db.execSQL("INSERT INTO USERINFO VALUES(null,'N','','FLAG_REGISTER')");
	}

	//���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		//db.execSQL("INSERT INTO USERINFO VALUES(1,'N','','FLAG_REGISTER')");
		//db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
	}
}

