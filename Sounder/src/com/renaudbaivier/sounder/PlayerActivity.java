package com.renaudbaivier.sounder;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
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
        path = "sdcard";
        file = "mp3.mp3";
        pathfile = Uri.parse("file:///"+path+"/"+file);
        
        //MediaStore mediaStore = MediaStore.
		
        // Lecteur
        mediaPlayer = MediaPlayer.create(getBaseContext(), pathfile);
        
        // Recuperation des tags
        
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
        		pause.setVisibility(View.INVISIBLE);
            	play.setVisibility(View.VISIBLE);
            	timeprogress.setProgress(0);
            	current.setText("00:00");
            	remaining.setText("-00:00");
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
    
    // Lecture du MP3
    public void play(View v) {
    	if (!mediaPlayer.isPlaying())
    	{
    		mediaPlayer.start();
        	pause.setVisibility(View.VISIBLE);
        	play.setVisibility(View.INVISIBLE);
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

    }
    
    // FF (non Laurent ce nest pas FonkyFamily) du MP3
    // appui court = piste suivante
    // appui long = avance rapide
    public void ff(View v) {

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
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }            
            timeprogress.setProgress(currentPosition);
        }
    }
    
    // A faire dans chaque class
    public void onBackPressed() {
    	tSounder = new Intent(PlayerActivity.this, SounderActivity.class);
        startActivity(tSounder);
    }
}