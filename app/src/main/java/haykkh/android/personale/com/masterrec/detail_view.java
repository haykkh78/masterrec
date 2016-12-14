package haykkh.android.personale.com.masterrec;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class detail_view extends Fragment {


    final String LOG_TAG = "myLogs";

        //---------widget list on detail form
    TextView client_view;
    TextView starttime_view;
    TextView finishtime_view;
    TextView proc_view;
    TextView descr_view;
    TextView date_view;
    TextView vip_view;
  public  long cur_id;
    String client;
    String starttime;
    String finishtime;
    String proc;
    String descr;
    String date;
    String vip;

    //--------------------------

    LayoutInflater myinflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }


   // @Override
    public  View v;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_detail_view, null);
         myinflater=inflater;

    client_view = (TextView) v.findViewById(R.id.client_view);
    starttime_view= (TextView) v.findViewById(R.id.starttime_view);
    finishtime_view= (TextView) v.findViewById(R.id.finishtime_view);
    proc_view= (TextView) v.findViewById(R.id.proc_view);
    descr_view= (TextView) v.findViewById(R.id.descr_view);
    date_view= (TextView) v.findViewById(R.id.date_view);
    vip_view= (TextView) v.findViewById(R.id.vip_view);
    client_view.setText(client);
    starttime_view.setText(starttime);
    finishtime_view.setText(finishtime);
    proc_view.setText(proc);
    descr_view.setText(descr);
    date_view.setText(date);
    vip_view.setText(vip);


       // date_view.setText("test");
        Log.d(LOG_TAG, "in view create");
        return v;

    }






    public void setviewmode(Cursor cur)
    {
        Log.d(LOG_TAG, "in new fragment");

        if (cur.moveToFirst()) {

            /*
            "id integer primary key autoincrement,"
                    + "date data,"
                    + "timebegin  time,"
                    + "timeend  time,"
                    + "client text,"
                    + "proc   text,"
                    + "descr text,"
                    + "vip int" + ");");
             */
/*
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("id") );
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("date") );
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("timebegin") );
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("timeend") );
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("client") );
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("proc") );
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("descr") );
            Log.d(LOG_TAG,"id"+ cur.getColumnIndex("vip") );

            Log.d(LOG_TAG,"val"+ cur.getString(1) );
            Log.d(LOG_TAG,"val"+ cur.getString(2) );
            Log.d(LOG_TAG,"val"+ cur.getString(3) );
            Log.d(LOG_TAG,"val"+ cur.getString(4) );
            Log.d(LOG_TAG,"val"+ cur.getString(5) );
            Log.d(LOG_TAG, "val" + cur.getString(6));
            Log.d(LOG_TAG, "in function");
  */       //   date_view.setText(cur.getString(1));
            cur_id=cur.getInt(0);
            client=cur.getString(4);
            starttime = cur.getString(2);
            finishtime=cur.getString(3);
            proc =cur.getString(5);
            descr = cur.getString(6);
            vip =cur.getString(7);
            date=cur.getString(1);
        } else
            Log.d(LOG_TAG, "0 rows");
            cur.close();

    }

}