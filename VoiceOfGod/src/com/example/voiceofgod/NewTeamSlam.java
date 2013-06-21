package com.example.voiceofgod;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NewTeamSlam extends Activity { //implements DatePickerFragment.OnCompleteListener{
	private int numberTeams; 
	private String date; 
	private boolean radioChecked;
	
	public void onRadioButtonClicked(View view) {
		radioChecked = ((RadioButton) view).isChecked();
		switch(view.getId()) {
		case R.id.radio_3:
			if (radioChecked) 
				numberTeams = 3;
			break;
		case R.id.radio_4:
			if (radioChecked)
				numberTeams = 4;
			break;
		case R.id.radio_5:
			if (radioChecked)
				numberTeams = 5;
			break;
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	

	public void onComplete(String date) {
		this.date =  date; 
	}
	
	@Override
	protected void onStart() {
        super.onStart();
        System.out.println("in the start method!");
        setContentView(R.layout.activity_new_team_slam);
	}

	
    // Called when user clicks Save button 
    public void saveSlam(View view) {
		System.out.println("number of teams was set to " + numberTeams);
		System.out.println("date was set to " + date); 
		finish();
    }
}
