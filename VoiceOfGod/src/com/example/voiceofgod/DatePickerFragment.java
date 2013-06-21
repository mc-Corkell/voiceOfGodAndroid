package com.example.voiceofgod;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class DatePickerFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day + "/"+ month + "/" + year;
        this.mListener.onComplete(date);
        
    }
    
    public static interface OnCompleteListener {
    	public abstract void onComplete(String date);
    }
    
    private OnCompleteListener mListener;

	 // make sure the Activity implemented it
	 public void onAttach(Activity activity) {
	     try {
	         this.mListener = (OnCompleteListener)activity;
	     }
	     catch (final ClassCastException e) {
	         throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
	     }
	 }    
}