package com.example.voiceofgod;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EMAIL = "com.example.voiceofgod.EMAIL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    // Called when user clicks Save button 
    public void logoutButton(View view) {
    	LoginActivity.logout = true;    	
    	super.finish();
    }
    
    public void makeSlam(View view) {
 		Intent intent = new Intent(this, NewTeamSlam.class);
 		System.out.println("in saveSlam button pressed method");
 		startActivity(intent);
    }

 
}
