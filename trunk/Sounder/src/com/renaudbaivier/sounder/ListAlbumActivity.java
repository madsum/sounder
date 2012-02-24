package com.renaudbaivier.sounder;

import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView.OnItemClickListener;

public class ListAlbumActivity extends Activity {
      ListView musiclist;
      Cursor musiccursor;
      int music_column_index;
      int count;

      /** Called when the activity is first created. */
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listealbum);
            init_phone_music_grid();
      }

      private void init_phone_music_grid() {
          System.gc();
          String[] proj = { MediaStore.Audio.Media._ID,
          				  MediaStore.Audio.Media.DATA,
          				  MediaStore.Audio.Media.DISPLAY_NAME,
          				  MediaStore.Audio.Media.ALBUM,
          				  MediaStore.Video.Media.SIZE };
          musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,proj, null, null, null);
          count = musiccursor.getCount();
          musiclist = (ListView) findViewById(R.id.MusicAlbumList);
         //musiclist.setAdapter(new MusicAdapter2(getApplicationContext()));
         // musiclist.setOnItemClickListener(musicgridlistener);
         //mMediaPlayer = new MediaPlayer();
    }

      /**
       * LA ON TRANSMET LE FILENAME A NOTRE AMI ROUKY
       
    private OnItemClickListener musicgridlistener = new OnItemClickListener() {
          public void onItemClick(AdapterView parent, View v, int position,
long id) {
                System.gc();
                music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                musiccursor.moveToPosition(position);
                String filename = musiccursor.getString(music_column_index);

               
           
          }
    };
    */

    public class MusicAdapter2 extends BaseAdapter {
          private Context mContext;

          public MusicAdapter2(Context c) {
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
                System.gc();
                TextView tv = new TextView(mContext.getApplicationContext());
                String id = null;
                if (convertView == null) {
                    //  music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
              	      music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                      musiccursor.moveToPosition(position);
                      id = musiccursor.getString(music_column_index);
                      music_column_index = musiccursor
.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                      musiccursor.moveToPosition(position);
							
//							@Override
//							public void onClick(View v) {
//								// TODO Auto-generated method stub
//								
//							}
//						})
                      
                     
                } else
                      tv = (TextView) convertView;
                return tv;
          }
    }
}