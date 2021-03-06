package com.example.voiceofgod;

import android.provider.BaseColumns;

public class VoiceOfGodContract implements BaseColumns {
	
	public static abstract class UserEntry implements BaseColumns {
	    public static final String TABLE_NAME = "users";
	    public static final String COLUMN_NAME_ENTRY_ID = "userid";
	    public static final String COLUMN_NAME_EMAIL = "email";
	    public static final String COLUMN_NAME_PASSWORD = "password";
	    public static final String COLUMN_NAME_USERNAME = "userName";
	    public static final String COLUMN_NAME_LOCATION = "location";
	    // later add ability to link users to Facebook 
	}
	
	// prevents accidental instantiation
	private VoiceOfGodContract() {}

}
