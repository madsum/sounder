package com.renaudbaivier.sounder;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity {
	
	Intent tPlayer;
    
	public boolean onCreateOptionsMenu(Menu menu) {
    	 
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        
        return true;
	}
 
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
         	case R.id.player_m:
         		tPlayer = new Intent(MenuActivity.this, PlayerActivity.class);
                startActivity(tPlayer);
                return true;
            case R.id.exit_m:
                finish();
                return true;
         }
         return false;
    }
    
    public void onBackPressed() {
    	// Permet plusieurs Activity du player
    	// Trouver une SOLUTION
    	//super.onBackPressed();
    }
}