package haykkh.android.personale.com.masterrec;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.widget.DatePicker;

import java.util.Calendar;


public class head_gen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    onSomeEventListener someEventListener;

    TextView sel_Date;
    int DIALOG_DATE = 1;
    int myYear;
    int myMonth;
    int myDay;
    private String Current_date;

    public String getCurrent_date() {
        return Current_date;
    }

    public void setCurrent_date(String current_date) {
        Current_date = current_date;
      //  sel_Date.setText(current_date);
    }
    public void updateCurrent_date(String current_date) {
       // Current_date = current_date;
          sel_Date.setText(current_date);
        Log.d("myLogs", "date changed in head of general");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_head_gen, null);
        sel_Date = (TextView) v.findViewById(R.id.selecteddate);

        //------------------------date initialization
     //   final Calendar c = Calendar.getInstance();
     //   myYear = c.get(Calendar.YEAR);
     //   myMonth = c.get(Calendar.MONTH);
     //   myDay = c.get(Calendar.DAY_OF_MONTH);
     //   Log.d("myLogs", " initialize current date");
        sel_Date.setText(getCurrent_date());
        //----------------------------------------------------------
        sel_Date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("myLogs", "textview Click");
            someEventListener.someEvent("Select_Date");
            }
        });
        //------------------------------------------------------



        return v;
    }
}
