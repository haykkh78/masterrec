package haykkh.android.personale.com.masterrec;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.app.Dialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class detail extends Fragment {

    private static final int REQUEST_CONTACT = 2;
    final String LOG_TAG = "myLogs";

    onSomeEventListener someEventListener;
    //---------widget list on detail form
    TextView view_client;
    TextView view_date;
    Button sel_client;
    TimePicker startime;
    TimePicker finishtime;
    Spinner proc;
    EditText descr;
    CheckBox vip;

    //--------------------------

    // variables for initialization

    int beginhour, beginmin, endhour, endmin;

    public  long cur_id=-1;
    String xclient;
    String xstarttime;
    String xfinishtime;
    String xproc;
    String xdescr;
    String xdate;
    String xvip;



    LayoutInflater myinflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


   // @Override
    public  View v;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_detail, null);
         myinflater=inflater;

       //-------------------------------------------------------
        startime= (TimePicker) v.findViewById(R.id.timebegin);
        finishtime= (TimePicker) v.findViewById(R.id.timeend);
        startime.setIs24HourView(true);
        finishtime.setIs24HourView(true);
        //--------------------------------------------------------
        view_client = (TextView) v.findViewById(R.id.editClient);
        view_date = (TextView) v.findViewById(R.id.date_view);
        sel_client = (Button) v.findViewById(R.id.sel_client);
        sel_client.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });
        //----------------------------------------------------------
        proc= (Spinner)v.findViewById(R.id.procedurelist);
        descr= (EditText) v.findViewById(R.id.editDescription);
        vip= (CheckBox) v.findViewById(R.id.checkVIP);
        //------------------------------------------------------------

        startime.setCurrentHour(beginhour);
        startime.setCurrentMinute(beginmin);
        finishtime.setCurrentHour(endhour);
        finishtime.setCurrentMinute(endmin);

        view_client.setText(xclient);
        view_date.setText(xdate);
        proc.setPrompt(xproc);
        descr.setText(xdescr);
     //   vip.isChecked(xvip);


        return v;

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
         if (requestCode == REQUEST_CONTACT) {
            Uri contactUri = data.getData();
// Определение полей, значения которых должны быть
// возвращены запросом.
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };
// Выполнение запроса - contactUri здесь выполняет функции
// условия "where"
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);
// Проверка получения результатов
            if (c.getCount() == 0) {
                c.close();
                return;
            }
// Извлечение первого столбца данных - имени подозреваемого.
            c.moveToFirst();
            String client = c.getString(0);
             view_client.setText(client);
            c.close();
        }
    }


    public String getsqlstring(String cur_date)
    {

        String sqlstring="";

        if (cur_id>0)
        {sqlstring= " update tasks set date = '"+ cur_date+"', timebegin ='"+ startime.getCurrentHour()+ ":" + startime.getCurrentMinute() +
                "', timeend = '"+finishtime.getCurrentHour()+":"+finishtime.getCurrentMinute() +
                "' , client = '"+view_client.getText()+"', proc = '"+proc.getSelectedItem().toString()+"',  descr = '"+
                descr.getText() + "' where id="+cur_id;}
        else
        sqlstring= "INSERT INTO tasks (date, timebegin, timeend, client, proc,  descr   ) "+
                " VALUES ( '" +  cur_date +  "', '" + startime.getCurrentHour()+ ":" + startime.getCurrentMinute()+
                "', '"+ finishtime.getCurrentHour()+":"+finishtime.getCurrentMinute()+ "', '"+ view_client.getText()+
                "', '" +proc.getSelectedItem().toString()+"', '"+ descr.getText()+"') ";


        Log.d(LOG_TAG, sqlstring);
        return sqlstring;
    }

    public void setviewmode(Cursor cur)
    {
    //    v = this.inflater.inflate(R.layout.fragment_detail, null);
            v = myinflater.inflate(R.layout.fragment_detail_view, null);
        Log.d(LOG_TAG, "waiting for new view");

    }



public void add_task(String begin, String end){

    Log.d(LOG_TAG, "setting time ");
    int pos= begin.lastIndexOf(":");
 beginhour= Integer.parseInt(begin.substring(0, pos));
 beginmin=Integer.parseInt(begin.substring(pos + 1));
  //  startime.setCurrentHour(16);
  //  startime.setCurrentMinute(55);
    Log.d(LOG_TAG, "setting begin time completed"+beginhour+":"+beginmin);
    pos= end.lastIndexOf(":");
    endhour=Integer.parseInt(end.substring(0, pos));
    endmin=Integer.parseInt(end.substring(pos + 1));
    Log.d(LOG_TAG, "setting time completed"+endhour+":"+endmin);

}


    public void reedit_task(Cursor cur)
    {
        Log.d(LOG_TAG, "reedit task");

        if (cur.moveToFirst()) {

            //Log.d(LOG_TAG, "in function");
            //   date_view.setText(cur.getString(1));
            cur_id=cur.getInt(0);
            xclient=cur.getString(4);
            xstarttime = cur.getString(2);
            xfinishtime=cur.getString(3);
            xproc =cur.getString(5);
            xdescr = cur.getString(6);
            xvip =cur.getString(7);
           xdate=cur.getString(1);

        } else
            Log.d(LOG_TAG, "0 rows");
        //cur.close();
    }

}