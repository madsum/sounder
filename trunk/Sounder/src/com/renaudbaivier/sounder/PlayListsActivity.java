package com.renaudbaivier.sounder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PlayListsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Play lists tab");
        setContentView(textview);
    }
}
