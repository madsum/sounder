package com.renaudbaivier.sounder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends MenuActivity implements Runnable {
    /** Called when the activity is first created. */
	
	// Variables
	MediaPlayer mediaPlayer;
	TextView current;
	Date current_d;
	SimpleDateFormat current_d_timeFormat;
	String current_d_time;
	TextView remaining;
	Date remaining_d;
	SimpleDateFormat remaining_d_timeFormat;
	String remaining_d_time;
	ImageView play;
	ImageView pause;
	TextView artist;
	TextView album;
	TextView track;
	SeekBar timeprogress;
	String path;
	String file;
	Uri pathfile;
	int currentPosition;
	int total;
	Intent tSounder;
	Thread currentThread;
	ImageView loop_off;
	ImageView loop_on;
	boolean loop;
	TextView loop_t;
	Animation loop_t_a;
	int instance=0;
	boolean keep_alive_thread=true;
	float mLastTouchX=0;
	float mLastTouchY=0;

	
	 //liste de lecture courante
   private ArrayList<Song> currentPlayList = new ArrayList<Song>();
	int currentSong = 0;
   
	


	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        // Variables populate
        play = (ImageView) findViewById(R.id.play);
        pause = (ImageView) findViewById(R.id.pause);
        artist = (TextView) findViewById(R.id.artist);
        album = (TextView) findViewById(R.id.album);
        track = (TextView) findViewById(R.id.track);
        current = (TextView) findViewById(R.id.current);
        remaining = (TextView) findViewById(R.id.remaining);
        timeprogress = (SeekBar) findViewById(R.id.timeprogress);
        loop_off = (ImageView) findViewById(R.id.loop_off);
        loop_on = (ImageView) findViewById(R.id.loop_on);
        loop = false;
        loop_t = (TextView) findViewById(R.id.loop_t);
        loop_t_a = AnimationUtils.loadAnimation(this, R.anim.translate);
        path = "mnt/sdcard";
        file = "mp3.mp3";
        
     mediaPlayer=new MediaPlayer();
   

     Song lastSong = this.getLastSongPreference(this, "song_relica");
     if(!lastSong.getFilePath().equals("N/A")){
    	 this.addASong(lastSong);
    	 this.loadLastSong();
    	 
     }
     
    // testInstanceWithExtra();
 
    }
    
    public void loadLastSong(){
    	if (!mediaPlayer.isPlaying())
    	{
    		if( this.currentPlayList.size()>0){
    			if(instance<=0){


    				try {

    					unSynchronizeSeekBar();

    					mediaPlayer=null;

    					mediaPlayer=new MediaPlayer();
    					mediaPlayer.setDataSource(this.currentPlayList.get(this.currentSong).getFilePath());
    					mediaPlayer.prepare();

    					instance++;
    				} catch (IllegalArgumentException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (IllegalStateException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			this.synchronizeSeekBar();
    			loadInformation();
    			refreshTacks();
    		}
    		
    	}
    }
    public void saveLastSongPreference(Activity activity,String namespace,Song value){
  	  SharedPreferences prefs = activity.getSharedPreferences(namespace, this.MODE_PRIVATE);
  	  SharedPreferences.Editor editor = prefs.edit();
  	  
  	  editor.putString("title_song", value.getTitre());
  	editor.putString("album_song", value.getAlbum());
  	editor.putString("artist_song", value.getArtist());
  	editor.putString("filename_song", value.getFilePath());
  	  editor.commit();
  	}

  	
  	public Song getLastSongPreference(Activity activity,String namespace){
  	  SharedPreferences prefs = activity.getSharedPreferences(namespace,this.MODE_PRIVATE);
  String titre=  prefs.getString("title_song", "N/A");
  String album =	prefs.getString("album_song", "N/A");
  String artist =	prefs.getString("artist_song", "N/A");
  String filepath =	prefs.getString("filename_song", "N/A");
  	Song lastSong=new Song(titre, album, artist, filepath);
  	  return lastSong;
  	}
    public void testInstanceWithExtra(){
    	try {
    		String function = getIntent().getCharSequenceExtra("function").toString();
    		if(function.equals("add1")){
    	    	
        		Song song = getIntent().getExtras().getParcelable("song");
        		this.addASong(song);
        		if(instance==0){
        			this.play();
        		}
        	}
    	}catch (NullPointerException e) {
    		
    		
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
    }
    
private void unSynchronizeSeekBar(){
	keep_alive_thread=false;     
}
    private void synchronizeSeekBar(){
    	keep_alive_thread=true;
    	// La seekbar pour la visibilite de la lecture et le deplacement
        timeprogress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    		@Override
    		public void onStopTrackingTouch(SeekBar seekBar) {
    			mediaPlayer.start();
    			pause.setVisibility(View.VISIBLE);
    	    	play.setVisibility(View.INVISIBLE);
    		}

    		@Override
    		public void onStartTrackingTouch(SeekBar seekBar) {
    			mediaPlayer.pause();
    			pause.setVisibility(View.INVISIBLE);
    	    	play.setVisibility(View.VISIBLE);
    		}

    		@Override
    		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    			if(fromUser){
    				mediaPlayer.seekTo(progress);
    			}    			
    			current_d = new Date();
    	        current_d.setTime(mediaPlayer.getCurrentPosition());
    	        current_d_timeFormat = new SimpleDateFormat("mm:ss");
    	        current_d_time = current_d_timeFormat.format(current_d).toString();
    	        current.setText(current_d_time);
    	        
    	        remaining_d = new Date();
    	        remaining_d.setTime(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition());
    	        remaining_d_timeFormat = new SimpleDateFormat("mm:ss");
    	        remaining_d_time = remaining_d_timeFormat.format(remaining_d).toString();
    	        remaining.setText("-" + remaining_d_time);
    		}
    	});
        
        // Si le loop est actif
        // ! Probleme avec la seekbar !
        mediaPlayer.setOnCompletionListener(new OnCompletionListener(){
        	public void onCompletion(MediaPlayer arg0) {
        		if(loop == false){
        		ff();
        		}
        
            };
        });
        
        // Listener de lanimation
        loop_t_a.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
                    // Ici, l'animation est terminŽe !
            }
    });
        
        
        
        currentThread = new Thread(this);
    currentThread.start();
 
    }
    
    @Override
    public void onDestroy() {
        Log.i("DUMMY DEBUG", "onDestroy()");
        this.saveLastSongPreference(this, "song_relica", this.currentPlayList.get(currentSong));
        
        currentThread=null;
        unSynchronizeSeekBar();
        mediaPlayer.release();
        mediaPlayer=null;
        //AAAAAAAARRRRRRGGGGG PAS BEAU  rafistolage avant de trouver mieu
        System.exit(0);
       
        super.onDestroy();
    }

    
    
    protected void onResume() {
        super.onResume();
        if (mediaPlayer.isPlaying()) { 	
	    	pause.setVisibility(View.VISIBLE);
	    	play.setVisibility(View.INVISIBLE);
    	}
    }
    

    // Lecture du MP3
    public void play(View v) {
    	if (!mediaPlayer.isPlaying())
    	{
    		if( this.currentPlayList.size()>0){
    			if(instance<=0){
    				
    	    		
    				try {
    					
    					unSynchronizeSeekBar();
        				
        				mediaPlayer=null;
        				
        				mediaPlayer=new MediaPlayer();
						mediaPlayer.setDataSource(this.currentPlayList.get(this.currentSong).getFilePath());
						mediaPlayer.prepare();
						
						instance++;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			this.synchronizeSeekBar();
    			loadInformation();
    			refreshTacks();
    			//ca.execute();
    			
    			mediaPlayer.start();
            	pause.setVisibility(View.VISIBLE);
            	play.setVisibility(View.INVISIBLE);	
    		}else{
    			Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
    		}
    		
    	}
    }
 // Lecture du MP3
    public void play() {
    	if (!mediaPlayer.isPlaying())
    	{
    		
    		if( this.currentPlayList.size()>0){
    			if(instance<=0){
    				mediaPlayer=new MediaPlayer();
    				try {
						mediaPlayer.setDataSource(this.currentPlayList.get(this.currentSong).getFilePath());
						instance++;
						mediaPlayer.prepare();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			this.synchronizeSeekBar();
    			loadInformation();
    			refreshTacks();
    			mediaPlayer.start();
            	pause.setVisibility(View.VISIBLE);
            	play.setVisibility(View.INVISIBLE);	
    		}else{
    			Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
    			this.currentSong=0;
    		}
    		
    	}
    }
    
 // Lecture du MP3 aprés interuption
    public void stopAndPlay(String filename) {
    	if (mediaPlayer.isPlaying())
    	{
    		mediaPlayer.stop();
    		
    	}
    	
    	try {
    		
    		currentThread=null;
    		unSynchronizeSeekBar();
    		mediaPlayer.reset();
    		mediaPlayer=null;
    		mediaPlayer=new MediaPlayer();
    		mediaPlayer.setDataSource(filename);
    		instance++;
			mediaPlayer.prepare();
			
			
			this.play();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
       
        
    	
    }
    
    public void loadInformation(){
    	Song song = this.currentPlayList.get(currentSong);
    TextView tv =	(TextView) findViewById(R.id.album);
    tv.setText(song.getAlbum());
    tv =	(TextView) findViewById(R.id.artist);
    tv.setText(song.getArtist());
    tv =	(TextView) findViewById(R.id.track);
    tv.setText(song.getTitre());
    }
    
    // Pause du MP3
    public void pause(View v) {
    	if (mediaPlayer.isPlaying()) {
    		mediaPlayer.pause();    	
	    	pause.setVisibility(View.INVISIBLE);
	    	play.setVisibility(View.VISIBLE);
    	}
    }
    
    // Stop du MP3
    public void stop(View v) {
    	if (mediaPlayer.isPlaying()) {
    		mediaPlayer.stop();    	
    	}
    }
    
    // REW du MP3
    public void rew(View v) {
    	if(this.currentPlayList.size()<=0){
    		Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
    	}else{
currentSong--;
if(currentSong<0){
	currentSong=this.currentPlayList.size()-1;
}
this.stopAndPlay(this.currentPlayList.get(currentSong).getFilePath());
    	}
    }
    
    // FF (non Laurent ce nest pas FonkyFamily) du MP3
    // appui court = piste suivante
    // appui long = avance rapide
    public void ff(View v) {
    	if(this.currentPlayList.size()<=0){
    		Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
    	}else{
currentSong++;
if(currentSong>=this.currentPlayList.size()){
	currentSong=0;
}
this.stopAndPlay(this.currentPlayList.get(currentSong).getFilePath());
    }
    }
    
    
    public void ff() {
    	if(this.currentPlayList.size()<=0){
    		Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
    	}else{
currentSong++;
if(currentSong>=this.currentPlayList.size()){
	currentSong=0;
}
this.stopAndPlay(this.currentPlayList.get(currentSong).getFilePath());
    }
    }
    
    // Loop
    public void loop(View v) {
    	if (loop == false) {
    		loop_off.setVisibility(View.INVISIBLE);
        	loop_on.setVisibility(View.VISIBLE);
        	loop = true;
        	loop_t.startAnimation(loop_t_a);
    	} else {
    		loop_off.setVisibility(View.VISIBLE);
        	loop_on.setVisibility(View.INVISIBLE);
        	loop = false;
    	}
    	mediaPlayer.setLooping(loop);
    }
    
    // Utils
    @Override
    public void run() {
    	currentPosition = 0;
        total = mediaPlayer.getDuration();
        while (mediaPlayer != null && currentPosition < total && keep_alive_thread ) {
        	try {
                
                currentPosition = mediaPlayer.getCurrentPosition();
                timeprogress.setMax(mediaPlayer.getDuration());
                timeprogress.setProgress(currentPosition);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
            	mediaPlayer=null;
                return;
            }            
           
        }
    }
    
    // A faire dans chaque class
    public void onBackPressed() {
    	Intent i = new Intent(Intent.ACTION_MAIN);
    	i.addCategory(Intent.CATEGORY_HOME);
    	startActivity(i);

    	
    }
    
    private void loadNewCurrentList(Song[] songs,int startindex){
    	this.currentPlayList.clear();
    	this.instance=0;
    	this.currentSong=startindex;
    	for(Song song : songs){
    		this.addASong(song);
    	}
    	this.stopAndPlay(this.currentPlayList.get(currentSong).getFilePath());
    }
    
    //recupération d'une instruction externe  ex. nouvelle chanson
    @Override
    protected void onNewIntent(Intent intent) {
    	try {
    		String function = intent.getCharSequenceExtra("function").toString();
    		if(function.equals("add1")){
    	    	
        		Song song = intent.getExtras().getParcelable("song");
        		this.addASong(song);
        		if(instance==0){
        			this.play();
        		}
        		
        		
        		
        		
        		
        		
        	}
    		if(function.equals("addMultiple")){
   	    	 Parcelable[] parcels =	intent.getExtras().getParcelableArray("newlist");
   	    	Song[] songs = new Song[parcels.length];
   	    	int i=0;
   	    	for (Parcelable par : parcels){
   	    	     songs[i]=(Song)par;   
   	    	     i++;
   	    	}

   	    	 
   	    	 
   	    	 int startIndex =	intent.getIntExtra("startIndex",0);
   	    	 this.loadNewCurrentList(songs, startIndex);
   		}
    	}catch (NullPointerException e) {
    		
    		
			// TODO: handle exception
		}
    	
    	/*catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
    	
    	
    	
    }
    
  //ajout d'une chanson 
	private void addASong(Song song){
		this.currentPlayList.add(song);
		refreshTacks();
	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    final int action = ev.getAction();
	    
		switch (action) {
	    case MotionEvent.ACTION_DOWN: {
	         float x = ev.getX();
	         float y = ev.getY();
	        
	        // Remember where we started
	        mLastTouchX = x;
	        mLastTouchY = y;
	        break;
	    }
	        
	    case MotionEvent.ACTION_UP: {
	    	 float x = ev.getX();
	         float y = ev.getY();
	        float test = Math.abs(x-mLastTouchX);
	        if(test>=50){
	        Intent	tabs = new Intent(this, com.renaudbaivier.sounder.TabsActivity.class);
	            
                startActivityForResult(tabs,22);
	        }
	        
	        break;
	    }
	    }
	    
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        	case R.id.player_m:
        		tPlayer = new Intent(this, PlayerActivity.class);
        		
               startActivity(tPlayer);
               return true;
           case R.id.exit_m:
           	finishActivity(22);
               finish();
               return true;
           case R.id.search_m:
           	tPlayer = new Intent(this, com.renaudbaivier.sounder.TabsActivity.class);
          
               startActivityForResult(tPlayer,22);
           	return true;
           case R.id.settings_m:
        	   this.currentPlayList.clear();
        	   instance=0;
        	   break;
           	
        }
        return false;
   }
	
	
	public void refreshTacks(){
		TextView tv = (TextView) findViewById(R.id.nb_track);
		tv.setText(String.format("%d/%d", (this.currentSong+1),this.currentPlayList.size()));
	}
}