package com.renaudbaivier.sounder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListTitreActivity extends Activity 
{
      ListView musiclist;
      Cursor musiccursor;
      int music_column_index;
      int count;
      Context ctx;
      //MediaPlayer mMediaPlayer;

      /** Called when the activity is first created. */
      @Override
      public void onCreate(Bundle savedInstanceState) 
      {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listetitre);
            ctx=getApplicationContext();
            init_phone_music_grid();
      }

      private void init_phone_music_grid() {
          //  System.gc();
            String[] proj = { MediaStore.Audio.Media._ID,
            				  MediaStore.Audio.Media.DATA,
            				  MediaStore.Audio.Media.DISPLAY_NAME,
            				  MediaStore.Audio.Media.ARTIST,
            				  MediaStore.Audio.Media.ALBUM,
            				  MediaStore.Audio.Media.TITLE,
            				 //MediaStore.Audio.Media.ALBUM_ART
            };
            musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,proj, null, null, null);
            count = musiccursor.getCount();
            musiclist = (ListView) findViewById(R.id.MusicTitreList);
            musiclist.setAdapter(new MusicAdapter(getApplicationContext()));
            musiclist.setOnItemClickListener(musicgridlistener);
            
      }

      /**
       * LA ON TRANSMET LE FILENAME A NOTRE AMI ROUKY
       */
      private OnItemClickListener musicgridlistener = new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position,
long id) {
            	
            	  //System.gc();
                  
                  musiccursor.moveToPosition(position);
                 
                  music_column_index =  musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                  String title = musiccursor.getString(music_column_index);
                  music_column_index =  musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                  String album = musiccursor.getString(music_column_index);
                  music_column_index =  musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                  String artist = musiccursor.getString(music_column_index);
                  music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                  String filename = musiccursor.getString(music_column_index);
                 // music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ART);
                //  String albumArt = musiccursor.getString(music_column_index);
                      
                  Song song = new Song(title, album, artist, filename);
            
                 // appelle de la methode d'ajout du player
                  Intent intent = new Intent(ctx,com.renaudbaivier.sounder.PlayerActivity.class);
                 String methode = "add1";
                 
                intent.putExtra("function", methode);
                 // intent.putExtra("song", filename);
                intent.putExtra("song", song);
                  startActivity(intent);
                /* 
          	  Toast.makeText(getApplicationContext(), "Vous avez cliqué sur le titre: "+artist, Toast.LENGTH_SHORT).show();
              */  
          	  
            }
      };

      public class MusicAdapter extends BaseAdapter {
            private Context mContext;

            public MusicAdapter(Context c) {
                  mContext = c;
            }

            public int getCount() {
                  return count;
            }

            public Object getItem(int position) {
                  return position;
            }

            public long getItemId(int position) {
                  return position;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                  //System.gc();
                  TextView tv = new TextView(mContext.getApplicationContext());
                  String id = null;
                  if (convertView == null) {
                        music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                        musiccursor.moveToPosition(position);
                        id = musiccursor.getString(music_column_index);
                        musiccursor.moveToPosition(position);
                        tv.setText(id);
                        
                       
                  } else
                        tv = (TextView) convertView;
                  return tv;
            }
      }
}
