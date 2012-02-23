package com.renaudbaivier.sounder;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
	ProgressBar timeprogress;
	String path;
	String file;
	Uri pathfile;
	int currentPosition;
	int total;
	
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
        timeprogress= (ProgressBar) findViewById(R.id.timeprogress);
        
        // Uri
        // Peut Etre DataSource plus tard
        path = "sdcard";
        file = "mp3.mp3";
        pathfile = Uri.parse("file:///"+path+"/"+file);
		
        // Lecteur
        mediaPlayer = MediaPlayer.create(getBaseContext(), pathfile);		
    }
    
    // Temporaire evite de devoir aller shooter lappli dans les settings !
    // Travailler sur le onResume qui merde pour linstant
    // Ne pas oublier de supprimer ce type de commentaire aussi...
    public void onStop() {
    	super.onStop();
    	mediaPlayer.stop();
    }
    
    // Lecture du MP3
    public void play(View v) {
    	if (!mediaPlayer.isPlaying())
    	{
	    	mediaPlayer.start();
	    	pause.setVisibility(View.VISIBLE);
	    	play.setVisibility(View.INVISIBLE);
	    	//mediaPlayer.setOnCompletionListener(listener)
	    	//timeprogress.setProgress(0);
	    	timeprogress.setMax(mediaPlayer.getDuration());
	    	new Thread(this).start();
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
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }            
            timeprogress.setProgress(currentPosition);
        }
    }
    
    // Pour plus tard : wifiLock.release();
}