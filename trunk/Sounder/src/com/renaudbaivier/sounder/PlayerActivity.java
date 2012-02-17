package com.renaudbaivier.sounder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerActivity extends Activity {
    /** Called when the activity is first created. */
	
	// Lecture MP3
	MediaPlayer mediaPlayer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        
        // Récupération du fichier
        // Ficher et chemin de TEST
        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.mp3); 
    }
    
    // Appui sur touche back
    // A mettre dans TOUTE les Activity !
    public void onBackPressed() {
    	
    }
    
    // Lecture du MP3
    public void play(View v) {
    	mediaPlayer.start();
    	ImageView pause = (ImageView) findViewById(R.id.pause);
    	pause.setVisibility(View.VISIBLE);
    	ImageView play = (ImageView) findViewById(R.id.play);
    	play.setVisibility(View.INVISIBLE);
    }
    
    // Pause du MP3
    public void pause(View v) {
    	mediaPlayer.pause();
    	ImageView pause = (ImageView) findViewById(R.id.pause);
    	pause.setVisibility(View.INVISIBLE);
    	ImageView play = (ImageView) findViewById(R.id.play);
    	play.setVisibility(View.VISIBLE);
    }
    
    // Stop du MP3
    public void stop(View v) {
    	mediaPlayer.stop();
    }
}