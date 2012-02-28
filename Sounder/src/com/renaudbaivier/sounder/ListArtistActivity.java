package com.renaudbaivier.sounder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ExpandableListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

	public class ListArtistActivity extends ExpandableListActivity {
	    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	try{
    		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listeartist);
        SimpleExpandableListAdapter expListAdapter =
			new SimpleExpandableListAdapter(
					this,
					createGroupList(), 				
					R.layout.group_row,				
					new String[] { "Group Item" },	
					new int[] { R.id.row_name },	
					createChildList(),				
					R.layout.child_row,				
					new String[] {"Sub Item"},		
					new int[] { R.id.grp_child}		
				);
			setListAdapter( expListAdapter );		

    	}catch(Exception e){
    		System.out.println("Errrr +++ " + e.getMessage());
    	}
        
    }
    /* creatin the HashMap for the children */
	private List<ArrayList<HashMap<String, String>>> createChildList() {
    	 ArrayList<ArrayList<HashMap<String, String>>> result2 = new ArrayList<ArrayList<HashMap<String, String>>>();
    	 ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
    	 
 		String[] proj = { 
 				  MediaStore.Audio.Media.ARTIST_ID,
  				  MediaStore.Audio.Media.ARTIST,
  				  MediaStore.Audio.Media.DISPLAY_NAME,
  				  };
 		Cursor musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,proj, null, null, MediaStore.Audio.Media.ARTIST_ID);
 		int count = musiccursor.getCount();
 		
 		ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();
 		for(int i=0; i<count; i++)
 		{					
 			
 			musiccursor.moveToPosition(i);
			String groupepalbum = musiccursor.getString(musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			HashMap<String, String> album = new HashMap<String, String>();
			album.put( "Group Item", groupepalbum );
			
			if(!result.contains(album))
			{
 				secList = new ArrayList<HashMap<String, String>>();
			}
 						
			
 			String titrealbum = musiccursor.getString(musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
 			HashMap<String,String> titre = new HashMap<String, String>();
 			titre.put( "Sub Item", titrealbum );
 			secList.add(titre);
			
 			if(!result2.contains(secList))
			{
 				result2.add(secList);
			}
 			
 			Log.d("album:",""+album.toString());
			Log.d("titre:",""+titre.toString());
 				result.add(album);
 			//result2.add(secList);
 			//secList = new ArrayList<HashMap<String, String>>();
  		}	  	    
 			
 	  	  return (List<ArrayList<HashMap<String, String>>>)result2;

    }
    /* Creating the Hashmap for the row */
	private List<HashMap<String, String>> createGroupList() {
		 ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		 
		String[] proj = { 
				  MediaStore.Audio.Media.ARTIST_ID,
				  MediaStore.Audio.Media.ARTIST,
 				  };
		Cursor musiccursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,proj, null, null,MediaStore.Audio.Media.ARTIST_ID);
		int count = musiccursor.getCount();
		for(int i=0; i<count; i++)
		{
			musiccursor.moveToPosition(i);
			String groupepalbum = musiccursor.getString(musiccursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			HashMap<String, String> album = new HashMap<String, String>();
			album.put( "Group Item", groupepalbum );
			Log.d("album groupe", album.toString());
			if(!result.contains(album))
			result.add(album);
			
					
		}	  	    
	  	  return (List<HashMap<String, String>>)result;
    }

	public void  onContentChanged  () {
    	System.out.println("onContentChanged");
	    super.onContentChanged();
    }
    /* This function is called on each child click */
    public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {
    	System.out.println("Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " + childPosition);
    	return true;
    }

    /* This function is called on expansion of the group */
    public void  onGroupExpand  (int groupPosition) {
    	try{
    		 System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
    	}catch(Exception e){
    		System.out.println(" groupPosition Errrr +++ " + e.getMessage());
    	}
    }
	
}