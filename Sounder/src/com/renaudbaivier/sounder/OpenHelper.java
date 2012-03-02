package com.renaudbaivier.sounder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
 
public class OpenHelper extends SQLiteOpenHelper
{
	private static final String TABLE_PLAYLIST = "table_playlist";
	private static final String COL_ID = "ID_playlist";
	private static final String COL_LIBELLE = "Libelle";
 
	private static final String TABLE_MUSIQUE = "table_musique";
	private static final String COL_ID_PLAY = "ID_playlist";
	private static final String COL_ID_MUSIQUE = "ID_musique";
	private static final String COL_TITRE = "titre";
	private static final String COL_ALBUM = "album";
	private static final String COL_ARTIST = "artist";
	private static final String COL_FILEPATH = "filepath";
	
	private static final String CREATE_BDD1 = "CREATE TABLE " + TABLE_PLAYLIST + " ("
	+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + COL_LIBELLE + " TEXT NOT NULL);";
 
	private static final String CREATE_BDD2 = "CREATE TABLE " + TABLE_MUSIQUE + " ("
	+ COL_ID_PLAY + " INTEGER , " + COL_ID_MUSIQUE + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
	 COL_TITRE +" TEXT NOT NULL,"+ COL_ALBUM + " TEXT NOT NULL,"+COL_ARTIST + " TEXT NOT NULL,"
	 +COL_FILEPATH + " TEXT NOT NULL);";
	
	public OpenHelper(Context context, String name, CursorFactory factory, int version) 
	{
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_BDD1);
		db.execSQL(CREATE_BDD2);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE " + TABLE_PLAYLIST + ";");
		db.execSQL("DROP TABLE " + TABLE_MUSIQUE + ";");
		onCreate(db);
	}
 
}