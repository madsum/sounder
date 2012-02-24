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

public class ListArtistActivity extends Activity {
      ListView musiclist;
      Cursor musiccursor;
      int music_column_index;
      int count;
      MediaPlayer mMediaPlayer;

      /** Called when the activity is first created. */
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listeartist);
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
          musiclist = (ListView) findViewById(R.id.MusicArtistList);
          musiclist.setAdapter(new MusicAdapter(getApplicationContext()));
         // musiclist.setOnItemClickListener(musicgridlistener);
    }

    private OnItemClickListener musicgridlistener = new OnItemClickListener() {
          public void onItemClick(AdapterView parent, View v, int position,
long id) {
                System.gc();
                music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                musiccursor.moveToPosition(position);
                String filename = musiccursor.getString(music_column_index);

                /**
                 * LA ON TRANSMET LE FILENAME A NOTRE AMI ROUKY
                 */
               /* try {
                      if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.reset();
                      }
                      mMediaPlayer.setDataSource(filename);
                      mMediaPlayer.prepare();
                      mMediaPlayer.start();
                } catch (Exception e) {

                }*/
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
                System.gc();
                TextView tv = new TextView(mContext.getApplicationContext());
                String id = null;
                if (convertView == null) {
                    //  music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
              	    //  music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
              	    music_column_index = musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                      musiccursor.moveToPosition(position);
                      id = musiccursor.getString(music_column_index);
                      music_column_index = musiccursor
.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                      musiccursor.moveToPosition(position);
                      //id += " Size):" + musiccursor.getString(music_column_index);
                      id += " Album):" +musiccursor
                      .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                      tv.setText(id);
                      //ici faire fonction qui permet denvoyer nom de musique avec le onclick
                                               
                    //  tv.setOnClickListener(new OnClickListener() {
							
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