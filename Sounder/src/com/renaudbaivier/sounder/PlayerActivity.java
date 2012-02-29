package com.renaudbaivier.sounder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

	
	 //liste de lecture courante
   private ArrayList<String> currentPlayList = new ArrayList<String>();
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
        //pathfile = Uri.parse("file:////mnt/sdcard/SleepAway.mp3");
    
        
        // Lecteur
       // mediaPlayer = MediaPlayer.create(getBaseContext(), pathfile);
     
     /*String filename= "/mnt/sdcard/SleepAway.mp3";*/
     mediaPlayer=new MediaPlayer();
   /*  instance++;
		try {
			mediaPlayer.setDataSource(filename);
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
		}*/
		
   
      
        // Listener de lanimation
        loop_t_a.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
                    // Ici, l'animation est terminée !
            }
    });
        
        
        
       // currentThread = new Thread(this);
    	//currentThread.start();
    }
    

    private void synchronizeSeekBar(){
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
        		/*if(instance<currentPlayList.size() || loop){
        			
        		}else{
        		mediaPlayer.reset();
        		mediaPlayer=null;
        		
        		pause.setVisibility(View.INVISIBLE);
            	play.setVisibility(View.VISIBLE);
            	timeprogress.setProgress(0);
            	current.setText("00:00");
            	remaining.setText("-00:00");
        		}*/
            };
        });
        
        // Listener de lanimation
        loop_t_a.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
                    // Ici, l'animation est terminée !
            }
    });
        
        
        
        currentThread = new Thread(this);
    currentThread.start();
 
    }
    
    @Override
    public void onDestroy() {
        Log.i("DUMMY DEBUG", "onDestroy()");
        currentThread=null;
        mediaPlayer.release();
        mediaPlayer=null;
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
    					currentThread=null;
        				mediaPlayer.reset();
        				mediaPlayer=null;
        				mediaPlayer=new MediaPlayer();
						mediaPlayer.setDataSource(this.currentPlayList.get(this.currentSong));
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
						mediaPlayer.setDataSource(this.currentPlayList.get(this.currentSong));
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
    			mediaPlayer.start();
            	pause.setVisibility(View.VISIBLE);
            	play.setVisibility(View.INVISIBLE);	
    		}else{
    			Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
    			this.currentSong=0;
    		}
    		
    	}
    }
    
 // Lecture du MP3 aprÈs interuption
    public void stopAndPlay(String filename) {
    	if (mediaPlayer.isPlaying())
    	{
    		mediaPlayer.stop();
    		
    	}
    	
    	try {
    		
    		currentThread=null;
    		mediaPlayer.reset();
    		mediaPlayer=null;
    		mediaPlayer=new MediaPlayer();
    		mediaPlayer.setDataSource(filename);
    		instance++;
			mediaPlayer.prepare();
			
			/*reconnexion a la seekbare
			mediaPlayer.setOnCompletionListener(new OnCompletionListener(){
	        	public void onCompletion(MediaPlayer arg0) {
	        		pause.setVisibility(View.INVISIBLE);
	            	play.setVisibility(View.VISIBLE);
	            	timeprogress.setProgress(0);
	            	current.setText("00:00");
	            	remaining.setText("-00:00");
	            };
	        });*/
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
this.stopAndPlay(this.currentPlayList.get(currentSong));
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
this.stopAndPlay(this.currentPlayList.get(currentSong));
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
this.stopAndPlay(this.currentPlayList.get(currentSong));
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
        while (mediaPlayer != null && currentPosition < total) {
        	try {
                Thread.sleep(100);
                currentPosition = mediaPlayer.getCurrentPosition();
                timeprogress.setMax(mediaPlayer.getDuration());
                timeprogress.setProgress(currentPosition);
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
    	tSounder = new Intent(PlayerActivity.this, SounderActivity.class);
        startActivity(tSounder);
    }
    
    
    
    //recupÈration d'une instruction externe  ex. nouvelle chanson
    @Override
    protected void onNewIntent(Intent intent) {
    	try {
    		String function = intent.getCharSequenceExtra("function").toString();
    		if(function.equals("add1")){
    	    	
        		String song = intent.getCharSequenceExtra("song").toString();
        		this.addASong(song);
        	}
    	}catch (NullPointerException e) {
    		
    		
			// TODO: handle exception
		}catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    	
    }
    
  //ajout d'une chanson 
	private void addASong(String song){
		this.currentPlayList.add(song);
	
	}
	

}