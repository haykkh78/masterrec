package haykkh.android.personale.com.masterrec;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class general extends Fragment {


    int[] colors = new int[2];
    LayoutInflater  myInflanter;
    View v;
    View.OnClickListener ontskclc;
    onSomeEventListener someEventListener;
   public Cursor task_curs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d("myLogs", " in onCreate of general");
    //    Log.d("myLogs", "cursor has "+ task_curs.getCount());
//        someEventListener.someEvent("Load_Task_List");
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("myLogs", " in onAttache of general");
     //   Log.d("myLogs", " cursor has "+ task_curs.getCount());
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.fragment_general, null);
        myInflanter = inflater;
        Log.d("myLogs", " in onCreateView  of general");
     //   Log.d("myLogs", " cursor has "+ task_curs.getCount());
        Log.d("mylog", "Starting   create view in General fragment");
                ontskclc = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Меняем текст в TextView (tvOut)
                        Log.d("mylog", "task pressed" + v.getId());
                TextView bgn= (TextView)v.findViewById(R.id.tvbegin);
                TextView endd = (TextView)v.findViewById(R.id.tvend);
                        Log.d("mylog", "begin time text" + bgn.getText());
                        Log.d("mylog", "end time text" + endd.getText());
                someEventListener.EditTask(Integer.toString(v.getId()), (String) bgn.getText(), (String) endd.getText());
            }



        };

/*
        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");
*/
//        Log.d("mylog", "updating current day task list from view create") ;
        someEventListener.someEvent("Load_Task_List");

  //------------------------------------------------------------
     //   Log.d("myLogs", "updating task list for current day");
        String  lastend, nextbegin;
        Log.d("myLogs", "view" + v.toString());
        LinearLayout linLayout = (LinearLayout) v.findViewById(R.id.linLayout);
        linLayout.removeAllViews();
        View item = myInflanter.inflate(R.layout.tasks, linLayout, false);
        item.setId(-1);
        item.setOnClickListener(ontskclc);
        TextView tvName = (TextView) item.findViewById(R.id.tvName);
        tvName.setText("...");
        TextView tvbegin = (TextView) item.findViewById(R.id.tvbegin);
        tvbegin.setText("9:00");
        TextView tvend = (TextView) item.findViewById(R.id.tvend);
        tvend.setText("9:00");
        item.getLayoutParams().width = AbsListView.LayoutParams.MATCH_PARENT;
        linLayout.addView(item);
        Log.d("myLogs", "empty task created");
        Log.d("myLogs", "in view_create  cursor has "+ task_curs.getCount());


        if (task_curs.moveToFirst()) {
            Log.d("myLogs", "creating tasks from cursor");
            do {

                item = myInflanter.inflate(R.layout.tasks, linLayout, false);
                item.setId(task_curs.getInt(0));
                item.setOnClickListener(ontskclc);
                tvName = (TextView) item.findViewById(R.id.tvName);
                tvName.setText(task_curs.getString(4));
                tvbegin = (TextView) item.findViewById(R.id.tvbegin);
                tvbegin.setText(task_curs.getString(2));
                tvend = (TextView) item.findViewById(R.id.tvend);
                tvend.setText(task_curs.getString(3));
                item.getLayoutParams().width = AbsListView.LayoutParams.MATCH_PARENT;
                //         item.setBackgroundColor(colors[i % 2]);
                linLayout.addView(item);
                Log.d("myLogs", "next task created");


            }while (task_curs.moveToNext());

        } else
            //         Log.d(LOG_TAG, "0 rows");
            task_curs.close();


        return v;
    }

    public void load_task_list(String sel_date, Cursor cur ) {


        task_curs = cur;
        Log.d("myLogs", "in load_task_list. cursor has "+ task_curs.getCount());

    }

    public void get_task_list(String sel_date, Cursor cursx ) {

      //  curs=cursx;
        v=null;
        Log.d("myLogs", "creating new view in get_task_list ");
        v = myInflanter.inflate(R.layout.fragment_general,null);
        Log.d("myLogs", " new view created");


        Log.d("myLogs", "updating task list for current day");
        String  lastend, nextbegin;
        Log.d("myLogs", "view"+v.toString());
        LinearLayout linLayout = (LinearLayout) v.findViewById(R.id.linLayout);
        linLayout.removeAllViews();
            View item = myInflanter.inflate(R.layout.tasks, linLayout, false);
            item.setId(-1);
            item.setOnClickListener(ontskclc);
            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            tvName.setText("...");
            TextView tvbegin = (TextView) item.findViewById(R.id.tvbegin);
            tvbegin.setText("9:00");
            TextView tvend = (TextView) item.findViewById(R.id.tvend);
            tvend.setText("9:00");
            item.getLayoutParams().width = AbsListView.LayoutParams.MATCH_PARENT;
            linLayout.addView(item);
        Log.d("myLogs", "empty task created");
        if (cursx.moveToFirst()) {

    do {

        item = myInflanter.inflate(R.layout.tasks, linLayout, false);
        item.setId(cursx.getInt(0));
        item.setOnClickListener(ontskclc);
        tvName = (TextView) item.findViewById(R.id.tvName);
        tvName.setText(cursx.getString(4));
        tvbegin = (TextView) item.findViewById(R.id.tvbegin);
        tvbegin.setText(cursx.getString(2));
        tvend = (TextView) item.findViewById(R.id.tvend);
        tvend.setText(cursx.getString(3));
        item.getLayoutParams().width = AbsListView.LayoutParams.MATCH_PARENT;
        //         item.setBackgroundColor(colors[i % 2]);
        linLayout.addView(item);
        Log.d("myLogs", "next task created");


    }while (cursx.moveToNext());

        } else
   //         Log.d(LOG_TAG, "0 rows");
        cursx.close();

    }

}