package com.example.voiceofgod;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
//<#if parentActivityClass != "">
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
//</#if>

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	public final static String EMAIL = "com.example.voiceofgod.EMAIL";

	public static boolean logout = false; 
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // Values for email and password at the time of the login attempt.
    private String mEmail;
    private String mPassword;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy(); 
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        //check if wanted to logout 
    	if (logout) {
        	SharedPreferences.Editor editor = sharedPref.edit();
        	editor.remove(getString(R.string.current_email));
        	editor.commit();
        	logout=false;
    	}
        
       //check if already logged in 
        mEmail = sharedPref.getString(getString(R.string.current_email), null);
     	if (mEmail != null) {
     		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
     		intent.putExtra(EMAIL, mEmail);
     		startActivity(intent);
     	} else { // if not logged in, run login activity 
	        setContentView(R.layout.activity_login);
	        setupActionBar();
	
	        // Set up the login form.
	        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
	        mEmailView = (EditText) findViewById(R.id.email);
	        mEmailView.setText(mEmail);
	
	        mPasswordView = (EditText) findViewById(R.id.password);
	        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	            @Override
	            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
	                if (id == R.id.login || id == EditorInfo.IME_NULL) {
	                    attemptLogin();
	                    return true;
	                }
	                return false;
	            }
	        });
	
	        mLoginFormView = findViewById(R.id.login_form);
	        mLoginStatusView = findViewById(R.id.login_status);
	        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
	
	        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                attemptLogin();
	            }
	        });
     	}
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                // TODO: If Settings has multiple levels, Up should navigate up
                // that hierarchy.
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (mPassword.length() < 4) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!mEmail.contains("@")) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            mAuthTask = new UserLoginTask();
           // AsyncTask<Void, Void, Boolean> login =
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
        	VoiceOfGodDbHelper mDbHelper = new VoiceOfGodDbHelper(getBaseContext()); // Not sure what getBaseContext means

            try {
            	SQLiteDatabase db = mDbHelper.getReadableDatabase();
            	// Define a projection that specifies which columns from the database
            	// you will actually use after this query.
            	String[] projection = {
            		VoiceOfGodContract.UserEntry._ID,
            	    VoiceOfGodContract.UserEntry.COLUMN_NAME_ENTRY_ID,
            	    VoiceOfGodContract.UserEntry.COLUMN_NAME_EMAIL,
            	    VoiceOfGodContract.UserEntry.COLUMN_NAME_PASSWORD,
            	    VoiceOfGodContract.UserEntry.COLUMN_NAME_USERNAME,
            	    };
            	
            	String selection = VoiceOfGodContract.UserEntry.COLUMN_NAME_EMAIL + " = ? ";
            	
            	String[] selectionArgs = {mEmail};
            	
            	// How you want the results sorted in the resulting Cursor
            	//String sortOrder = VoiceOfGodContract.UserEntry.COLUMN_NAME_UPDATED + " DESC";
            	Cursor c = db.query(
            		VoiceOfGodContract.UserEntry.TABLE_NAME,  // The table to query
            	    projection,                               // The columns to return
            	    selection,                                // The columns for the WHERE clause
            	    selectionArgs,                            // The values for the WHERE clause
            	    null,                                     // don't group the rows
            	    null,                                  // don't filter by row groups
            	    null //sortOrder                                 // The sort order
            	    );
            	
            	boolean userExists = c.moveToFirst();
            	// not sure when you should throw this exception or why
            	if (false) {
            		throw new InterruptedException(); 
            	}
            	
            	if (userExists) {
                	boolean correctPassword = c.getString(3).equals(mPassword);

            		if(correctPassword) {
            			return true; // found a matching user and email tuple
            		} else {
            			return false; // incorrect password. should display warning message
            		}
            	}

            	
            } catch (InterruptedException e) {
               return false;
           }

            // User not found, register new user 
            // Gets the data repository in write mode
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(VoiceOfGodContract.UserEntry.COLUMN_NAME_ENTRY_ID, ""); // really not sure about this. 
            values.put(VoiceOfGodContract.UserEntry.COLUMN_NAME_EMAIL, mEmail);
            values.put(VoiceOfGodContract.UserEntry.COLUMN_NAME_PASSWORD, mPassword); // Store their password and email! 
            values.put(VoiceOfGodContract.UserEntry.COLUMN_NAME_USERNAME, "");
            values.put(VoiceOfGodContract.UserEntry.COLUMN_NAME_LOCATION, "");

            // Insert the new row
            long newRowId;
            newRowId = db.insert(VoiceOfGodContract.UserEntry.TABLE_NAME, null,values);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
            	
            	System.out.println("entered information in login fomr woot");
            	//Write the username so user doesn't have to login all the time 
            	SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
            	SharedPreferences.Editor editor = sharedPref.edit();
            	editor.putString(getString(R.string.current_email), mEmail);
            	editor.commit();

            	//start the next activity! 
            	Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            	intent.putExtra(EMAIL, mEmail);
                System.out.println("before starting the activity");

            	startActivity(intent);
               // finish();  -- this activity will always be here so if you logout you'll go back to the login page :)
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
        

    }
}
