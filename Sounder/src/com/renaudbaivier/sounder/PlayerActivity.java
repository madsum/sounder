package com.renaudbaivier.sounder;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayerActivity extends MenuActivity implements Runnable {
    /** Called when the activity is first created. */
	
	// Variables
	MediaPlayer mediaPlayer;
	TextView current;
	TextView remaining;
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
        timeprogress= (SeekBar) findViewById(R.id.timeprogress);
        
        // Uri
        // Peut Etre DataSource plus tard
        path = "sdcard";
        file = "mp3.mp3";
        pathfile = Uri.parse("file:///"+path+"/"+file);
        
        //MediaStore mediaStore = MediaStore.
		
        // Lecteur
        mediaPlayer = MediaPlayer.create(getBaseContext(), pathfile);
        
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
    			current.setText(String.valueOf(mediaPlayer.getCurrentPosition()));
    			remaining.setText(String.valueOf(timeprogress.getProgress()));
    		}
    	});
        currentThread = new Thread(this);
    	currentThread.start();
    }
    
    public void isFinished() {
    	//mediaPlayer.reset();
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
    
    @Override
    public void run() {
    	currentPosition = 0;
        total = mediaPlayer.getDuration();
        while (mediaPlayer!=null && currentPosition<total) {
        	try {
                Thread.sleep(1000);
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