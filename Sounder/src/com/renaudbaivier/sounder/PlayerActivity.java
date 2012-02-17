package com.renaudbaivier.sounder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerActivity extends Activity {
    /** Called when the activity is first created. */
	
	// Variables
	MediaPlayer mediaPlayer;
	TextView current;
	ImageView play;
	ImageView pause;
	TextView artist;
	TextView album;
	TextView track;
	String path;
	String file;
	Uri pathfile;
	
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
        
        // Uri
        // Peut Etre DataSource plus tard
        path = "sdcard";
        file = "mp3.mp3";
        pathfile = Uri.parse("file:///"+path+"/"+file);
		
        // Lecteur
		mediaPlayer = MediaPlayer.create(getBaseContext(), pathfile);
        
        // Artiste

        
        // Album
        
        // Piste
        
        
        // Current du MP3
        //current.setText(String.valueOf(mediaPlayer.getCurrentPosition()));
    }
    
    // Appui sur touche back
    // A mettre dans TOUTE les Activity !
    public void onBackPressed() {
    	
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
    	//mediaPlayer.stop();
    }
    
    // REW du MP3
    public void rew(View v) {
    	
    }
    
    // FF (non Laurent ce nest pas FonkyFamily) du MP3
    public void ff(View v) {
    	
    }
    
    public void run() {
    	
    }
    
    // Pour plus tard : wifiLock.release();
}