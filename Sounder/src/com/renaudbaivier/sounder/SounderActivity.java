package com.renaudbaivier.sounder;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

public class SounderActivity extends MenuActivity {
    /** Called when the activity is first created. */
	
	//Liste contenant les chemins des fichiers mp3 de la sdcard
	private ArrayList<String> filePath = new ArrayList<String>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        //listeRepertoire(new File("/mnt/sdcard"));        
        
    }
    
    public void listerMusique(View v)
    {
    	Intent t = new Intent(SounderActivity.this, AllListActivity.class);
    	startActivity(t);
    	
    }
   

	// Pour DEV
    public void player(View v){
    	Intent t = new Intent(SounderActivity.this, PlayerActivity.class);
    	t.putExtra("liste", this.filePath);
    	startActivity(t);
    }
    
    public void ouvreOnglet(View v){
    	Intent t = new Intent(SounderActivity.this, TabsActivity.class);
    	startActivity(t);
    }
    
	/**
	 * Cette fonction permet de recuperer les fichiers mp3
	 * contenus dans la sdcard
	 * 
	 * @param repertoire
	 */
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
    	                		this.filePath.add(list[i].getPath());
    	                	}
    	                        // Appel récursif sur les sous-répertoires
    	                        listeRepertoire( list[i]);
    	                } 
                    } else
                    {
                    	Log.d("erreur: ","Erreur de lecture.");
                    }
            } 
    } 
    
}