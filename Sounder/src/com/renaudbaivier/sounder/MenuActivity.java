package com.renaudbaivier.sounder;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewDebug.FlagToString;
import android.widget.SeekBar;

public class MenuActivity extends Activity {
	
	Intent tPlayer;
    static final int tabsRequestCode=1;
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
            	finishActivity(22);
                finish();
                return true;
            case R.id.search_m:
            	tPlayer = new Intent(this, com.renaudbaivier.sounder.TabsActivity.class);
           
                startActivityForResult(tPlayer,22);
            	return true;
            	
         }
         return false;
    }
}