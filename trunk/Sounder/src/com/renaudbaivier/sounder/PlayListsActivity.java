package com.renaudbaivier.sounder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;


	public class PlayListsActivity extends ExpandableListActivity {
	    /** Called when the activity is first created. */
		
		private PlayListAdapter playListBdd;
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	try{
    		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);
        
        playListBdd = new PlayListAdapter(getApplicationContext());
        playListBdd.open();
       
        /**
         * insertion playlist OK
         */
       /* playListBdd.createPlayList("LaurentPlayList");
       playListBdd.createPlayList("RenaudPlayList");
       playListBdd.createPlayList("ManuPlayList");*/
        
        /**
         * insertion musiques dans playlist OK
         */
        /* String titre1 ="Earth Wind and Fire - September";
        String album1 ="musique";
        String artiste1 ="<unknown>";
        String path1 ="/mnt/sdcard/musique/Earth Wind and Fire - September.mp3";

        String titre2 ="Guess Whos Back";
        String album2 ="100% Hip Hop - The 100 Best HipHop Songs Ever";
        String artiste2 ="Rakim";
        String path2 ="/mnt/sdcard/musique/rap us/Rakim - Guess Whos Back.mp3";

        String titre3 ="Get down saturday night";
        String album3 ="funk";
        String artiste3 ="Shalamar";
        String path3 ="/mnt/sdcard/musique/funk/Shalamar - Get down saturday night.mp3";

        String titre4 ="Get Low";
        String album4 ="100% Hip Hop - The 100 Best HipHop Songs Ever";
        String artiste4 ="Lil' Jon & The Eastside Boyz";
        String path4 ="/mnt/sdcard/musique/rap us/Lil' Jon & The Eastside Boyz - Get Low.mp3";


        String titre5 =" Ante Up (Robbin Hoodz Theory)";
        String album5 ="Hip Hop A Tribute To Urban Culture";
        String artiste5 ="M.O.P";
        String path5 ="/mnt/sdcard/musique/rap us/m.o.p-ante_up_(robbin_hoodz_theory).mp3";

        String titre6 ="Bad Intentions-FIX";
        String album6 ="100% Hip Hop - The 100 Best HipHop Songs Ever";
        String artiste6 ="Dr Dre";
        String path6 ="/mnt/sdcard/musique/rap us/Dr. Dre - Bad Intentions.mp3";

        String titre7 =" Get ready tonight";
        String album7 ="funk";
        String artiste7 ="Shalamar";
        String path7 ="/mnt/sdcard/musique/funk/Shalamar - A night to remember.mp3";
        
       playListBdd.insertMusiqueIntoPlayList(new Song(titre1,album1,artiste1,path1), "LaurentPlayList");
       playListBdd.insertMusiqueIntoPlayList(new Song(titre2,album2,artiste2,path2), "LaurentPlayList");
       playListBdd.insertMusiqueIntoPlayList(new Song(titre3,album3,artiste3,path3), "LaurentPlayList");
       
       playListBdd.insertMusiqueIntoPlayList(new Song(titre4,album4,artiste4,path4), "RenaudPlayList");
       playListBdd.insertMusiqueIntoPlayList(new Song(titre5,album5,artiste5,path5), "RenaudPlayList");
       
       playListBdd.insertMusiqueIntoPlayList(new Song(titre6,album6,artiste6,path6), "ManuPlayList");
       playListBdd.insertMusiqueIntoPlayList(new Song(titre7,album7,artiste7,path7), "ManuPlayList");*/
                
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
    	 
 		String[] lists = playListBdd.getAllPlayLists();
        for(int i= 0; i<lists.length;i++)
        {
        	//Log.d("playlist: ",lists[i]);
        	HashMap<String, String> p = new HashMap<String, String>();
			p.put("Group Item", lists[i] );
			result.add(p);
				
			  ArrayList<HashMap<String, String>> secList = new ArrayList<HashMap<String, String>>();
			  Song[] lolo = playListBdd.getMusicOfPlaylist(lists[i]);
		        for(Song s : lolo)
		        {
		        	HashMap<String, String> titre = new HashMap<String, String>();
					titre.put("Sub Item", s.getTitre());
		        	secList.add(titre);
		        }
		        
			result2.add(secList);
			
			
        }	
 			  	    
 			
 	  	  return (List<ArrayList<HashMap<String, String>>>)result2;

    }
    /* Creating the Hashmap for the row */
	private List<HashMap<String, String>> createGroupList() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		String[] lists = playListBdd.getAllPlayLists();
	        for(int i= 0; i<lists.length;i++)
	        {
	        	//Log.d("playlist: ",lists[i]);
	        	HashMap<String, String> p = new HashMap<String, String>();
				p.put("Group Item", lists[i] );
				result.add(p);
	        }		
			
			  	    
	  	  return (List<HashMap<String, String>>)result;
    }

	public void  onContentChanged  () {
    	System.out.println("onContentChanged");
	    super.onContentChanged();
    }
    /* This function is called on each child click */
    public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) 
    {
    	HashMap playlist = (HashMap)parent.getExpandableListAdapter().getGroup(groupPosition);
  		String myPlaylist = (String) playlist.get("Group Item");
	  		
	  	Intent t = new Intent(getApplicationContext(),com.renaudbaivier.sounder.PlayerActivity.class);
	  	t.putExtra("function","addMultiple");
	  	t.putExtra("newlist",this.playListBdd.getMusicOfPlaylist(myPlaylist));
	  	t.putExtra("startIndex",childPosition);
	  	startActivity(t);
  	
  	//System.out.println("Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " + childPosition);
  	return true;
    }

    /* This function is called on expansion of the group */
    public void  onGroupExpand  (int groupPosition) {
    	try{
    		Log.d("onGroupExpand",""+groupPosition);
    		 System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
    	}catch(Exception e){
    		System.out.println(" groupPosition Errrr +++ " + e.getMessage());
    	}
    }
	
}