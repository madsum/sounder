package com.renaudbaivier.sounder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
 
public class PlayListAdapter 
{
	private static final int VERSION_BDD = 1;
	
	private static final String NOM_BDD = "playlist.db";

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
	
	private static final int NUM_COL_ID = 0;
	private static final int NUM_COL_LIBELLE = 1;
	
	private static final int NUM_COL_ID_PLAY = 0;
	private static final int NUM_COL_ID_MUSIQUE = 1;
	private static final int NUM_COL_TITRE = 2;
	private static final int NUM_COL_ALBUM = 3;
	private static final int NUM_COL_ARTIST = 4;
	private static final int NUM_COL_FILEPATH = 5;
	
	private SQLiteDatabase bdd;
	private OpenHelper maBaseSQLite;
	
	public PlayListAdapter(Context context)
	{
		//On crée la BDD et sa table
		maBaseSQLite = new OpenHelper(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open()
	{
		//on ouvre la BDD en écriture
		bdd = maBaseSQLite.getWritableDatabase();
	}
 
	public void close()
	{
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD()
	{
		return bdd;
	}
 
	public long insertMusiqueIntoPlayList(Song song,String playList)
	{
		Cursor c = bdd.query(TABLE_PLAYLIST, new String[] {COL_ID, COL_LIBELLE}, COL_LIBELLE + " LIKE \"" + playList +"\"", null, null, null, null);
		if (c.getCount() == 0)
		return (Long) null;
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		int idPlayList = c.getInt(NUM_COL_ID);
		
		ContentValues values = new ContentValues();
		values.put(COL_ID_PLAY, idPlayList);
		values.put(COL_ALBUM, song.getAlbum());
		values.put(COL_TITRE, song.getTitre());
		values.put(COL_ARTIST, song.getArtist());
		values.put(COL_FILEPATH, song.getFilePath());
		return bdd.insert(TABLE_MUSIQUE, null, values);
	}
	
	public long createPlayList(String libelle)
	{
		ContentValues values = new ContentValues();
		values.put(COL_LIBELLE, libelle);
		return bdd.insert(TABLE_PLAYLIST, null, values);
	}
 
	public int update(int id, Song song)
	{
		ContentValues values = new ContentValues();
		values.put(COL_ID_PLAY, id);
		values.put(COL_ALBUM, song.getAlbum());
		values.put(COL_TITRE, song.getTitre());
		values.put(COL_ARTIST, song.getArtist());
		values.put(COL_FILEPATH, song.getFilePath());
		return bdd.update(TABLE_PLAYLIST, values, COL_ID + " = " +id, null);
	}
 
	public int removePlaylistWithID(int id)
	{
		return bdd.delete(TABLE_PLAYLIST, COL_ID + " = " +id, null);
	}
 
	/**
	 * Cette fonction permet de retourner
	 * le nom de toutes les playlist de la base
	 * @return
	 */
	public String[] getAllPlayLists()
	{
		Cursor c = bdd.query(TABLE_PLAYLIST, new String[] {COL_ID, COL_LIBELLE},null, null, null, null, null);
		int nbPlaylist = c.getCount();
		//Log.d("nb playlist"," "+nbPlaylist);
		String[] playlists = new String[nbPlaylist];
		for(int i=0;i<nbPlaylist;i++)
		{
			c.moveToPosition(i);
			playlists[i] = c.getString(NUM_COL_LIBELLE);
			//Log.d("nb playlist",c.getString(NUM_COL_LIBELLE));
		}
		c.close();
		return playlists;
	}
	
	/**
	 * Cette fonction retourne un 
	 * tableau de Song à partir d'un
	 * nom de playlist
	 * 
	 * @param libelle
	 * @return
	 */
	public Song[] getMusicOfPlaylist(String libelle)
	{		
		
		Cursor c2 = bdd.query(TABLE_PLAYLIST, new String[] {COL_ID, COL_LIBELLE}, COL_LIBELLE + " LIKE \"" + libelle +"\"", null, null, null, null);
		if (c2.getCount() == 0)
		return null;
		//Sinon on se place sur le premier élément
		c2.moveToFirst();
		int playlist = c2.getInt(NUM_COL_ID);
		c2.close();
		Cursor c = bdd.query(TABLE_MUSIQUE, new String[] {COL_ID_PLAY, COL_ID_MUSIQUE, COL_TITRE,COL_ALBUM,COL_ARTIST,COL_FILEPATH}, COL_ID_PLAY + " = \"" + playlist +"\"", null, null, null, null);
		if (c.getCount() == 0)
		return null;
		//Sinon on se place sur le premier élément
		c.moveToFirst();
		int nbMusic = c.getCount();
		Song[] songs = new Song[nbMusic];
		for(int i=0;i<nbMusic;i++)
		{
			c.moveToPosition(i);
			songs[i] = new Song(c.getString(NUM_COL_TITRE),c.getString(NUM_COL_ALBUM),c.getString(NUM_COL_ARTIST),c.getString(NUM_COL_FILEPATH),"0");
		}

		//On ferme le cursor
		c.close();
		//On retourne le livre
		return songs;
	}
	
	public int deleteAllPlaylist()
	{
		return bdd.delete(TABLE_PLAYLIST, null, null);
	}
 
}