package com.example.voiceofgod;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class oldMainActivity extends Activity {
	public final static String USER_NAME = "com.example.voiceofgod.USERNAME";
	public final static String EMAIL = "com.example.voiceofgod.EMAIL";
	public final static String PASSWORD = "com.example.voiceofgod.PASSWORD";
	public final static String CITY = "com.example.voiceofgod.CITY";
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
    public void saveUser(View view) {
    	Intent intent = new Intent(this, SaveUserActivity.class);
    	EditText editText = (EditText) findViewById(R.id.new_user_message);
    	String username = editText.getText().toString();
    	intent.putExtra(USER_NAME, username);
    	startActivity(intent);
    	
    }
 
}
