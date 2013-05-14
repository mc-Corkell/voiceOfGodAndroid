package com.example.voiceofgod;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;


public class UserActivity extends Activity {
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_user);
	        setupActionBar();
	        
	        
	 }
	 
	    /**
	     * Set up the {@link android.app.ActionBar}, if the API is available.
	     */
	    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	    private void setupActionBar() {
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            // Show the Up button in the action bar.
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	        }
	    }
}
