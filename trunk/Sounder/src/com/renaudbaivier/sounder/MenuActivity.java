package com.renaudbaivier.sounder;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MenuActivity extends Activity {
    
	public boolean onCreateOptionsMenu(Menu menu) {
    	 
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        
        return true;
	}
 
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
         	case R.id.player_m:
         		Intent t = new Intent(MenuActivity.this, PlayerActivity.class);
            	startActivity(t);
            return true;
            case R.id.exit_m:
                finish();
               return true;
         }
         return false;
    }
    
    public void onBackPressed() {
    	super.onBackPressed();
    }
}