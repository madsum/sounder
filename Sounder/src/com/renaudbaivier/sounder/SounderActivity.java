package com.renaudbaivier.sounder;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class SounderActivity extends Activity {
    /** Called when the activity is first created. */
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        listeRepertoire(new File("/mnt/sdcard"));
    }
    
    // Pour DEV
    public void player(View v){
    	Intent t = new Intent(SounderActivity.this, PlayerActivity.class);
    	startActivity(t);
    }
    

    public void listeRepertoire ( File repertoire ) {
            
            if ( repertoire.isDirectory ( ) ) {
                    File[] list = repertoire.listFiles();
                    if (list != null){
    	                for ( int i = 0; i < list.length; i++)
    	                {
    	                	if(list[i].isFile()&&list[i].getName().endsWith(".mp3"))
    	                	{
    	                		Log.d("AbsolutePath",list[i].getAbsolutePath());
    	                		Log.d("Path",list[i].getPath());
    	                		Log.d("name",list[i].getName());
    	                	}
    	                        // Appel r�cursif sur les sous-r�pertoires
    	                        listeRepertoire( list[i]);
    	                } 
                    } else
                    {
                    	Log.d("erreur: ","Erreur de lecture.");
                    }
            } 
    } 
    
}