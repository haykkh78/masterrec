package haykkh.android.personale.com.masterrec;

//import haykkh.android.personale.com.masterrec.head_gen.onSomeEventListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.widget.DatePicker;

import java.util.Calendar;

import static android.os.Debug.waitForDebugger;

public  class MainActivity extends Activity implements onSomeEventListener {


   // variable declaration--------------------------------------------
    general frag1;
    detail frag2;
    detail_view frag3;
    head_gen head1;
    head_detail head2;
    head_detail_view head3;
    FragmentTransaction fTrans, fHead;

    // DB objects
    ContentValues cv = new ContentValues();
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor curs;
    //---------------------------

    final String LOG_TAG = "myLogs";

    //-------------------------------
    int DIALOG_DATE = 1;
    int myYear;
    int myMonth;
    int myDay;
    //-----------------------------------

    public String current_date; // selected date in main window
    public long task_id; // current task id. if new task =-1. if task not selected =0
    public String state; // mode of task view: Edit or View. List -  no task selected.
    //----------------------------------------------------------
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATE = "stt";

    @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
  /*      super.onSaveInstanceState(savedInstanceState);
        Log.d(LOG_TAG, "onSaveInstanceState");
        savedInstanceState.putString(KEY_DATE, current_date);
        Log.d(LOG_TAG, "onSaveInstanceState - current date added" + current_date);
        Log.d(LOG_TAG, "state - "+state);
        Log.d(LOG_TAG, "task id - "+ task_id);
        if ((state.equals("Edit"))||(state.equals("View")))
        {
            savedInstanceState.putLong(KEY_ID, task_id);
            Log.d(LOG_TAG, "onSaveInstanceState - task id added" + task_id);
            savedInstanceState.putString(KEY_STATE, state);
            Log.d(LOG_TAG, "onSaveInstanceState - state added" + state);
        }
        else
        {
            savedInstanceState.putLong(KEY_ID, 0);
            Log.d(LOG_TAG, "onSaveInstanceState - task id added" + 0);
            savedInstanceState.putString(KEY_STATE, "List");
            Log.d(LOG_TAG, "onSaveInstanceState - state added List");

        }
*/    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // waitForDebugger();// for debugging
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("myLogs", " Startig applicatiion: activity.oncreate()");

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        //----------------------------------------------------------
        // attaching fragments
        frag1 = new general();
        frag2 = new detail();
        frag3 = new detail_view();
        head1 = new head_gen();
        head2 = new head_detail();
        head3 = new head_detail_view();

// restore

    // initialization of current date
        final Calendar cal = Calendar.getInstance();
        myYear = cal.get(Calendar.YEAR);
        myMonth = cal.get(Calendar.MONTH);
        myDay = cal.get(Calendar.DAY_OF_MONTH);
        current_date = (myDay + "/" + (myMonth + 1) + "/" + myYear);
        state="List";
        task_id=0;
        Log.d(LOG_TAG, "initializing first launch 3 main variables");


        if (savedInstanceState != null)
        {
            Log.d(LOG_TAG, "start restoring instance");
            current_date = savedInstanceState.getString(KEY_DATE);
            task_id= savedInstanceState.getLong(KEY_ID);
            state=savedInstanceState.getString(KEY_STATE);

            Log.d(LOG_TAG, "restored current_date "+ current_date);
            Log.d(LOG_TAG, "restored task_id "+task_id);
            Log.d(LOG_TAG, "restored state "+state);


            if (state.equals("View"))  // restoring task  screen in view mode
            {
                Log.d(LOG_TAG, "restoring view mode");
             // This part need to include in separate function!!!!
                String    sqlstring = "SELECT * from tasks where id= "+ task_id;
                Cursor c;
                c = db.rawQuery(sqlstring,null);
                if (c != null && c.moveToFirst())
                {
                    long   lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
                    Log.d("myLogs", "call string"+lastId);
                }

                //------------relace fragments to view mode
                fHead = getFragmentManager().beginTransaction();
                fHead.remove(head2);
                fHead.remove(head1);
                fHead.add(R.id.frgmHead, head3);
                fHead.commit();
                fTrans = getFragmentManager().beginTransaction();
                fTrans.remove(frag2);
                fTrans.remove(frag1);
                fTrans.add(R.id.frgmCont, frag3);
                fTrans.commit();
                frag3.setviewmode(c);
            }

            if (state.equals("Edit"))
            {
                Log.d(LOG_TAG, "restoring edit mode");
                state="Edit";
            }


            if(state.equals("List"))
            {
                Log.d(LOG_TAG, "restoring list  mode");
              //  back_to_main();

                Log.d(LOG_TAG, " calling Load_Task_list()");
          //      frag1 = new general();
                Load_Task_list();
                Log.d(LOG_TAG, " Set current date in general head ");
        //        head1.setCurrent_date(current_date);
//                head1.updateCurrent_date(current_date);
         /*       fHead = getFragmentManager().beginTransaction();
                fHead.add(R.id.frgmHead, head1);
                fHead.commit();
                fTrans = getFragmentManager().beginTransaction();
                fTrans.add(R.id.frgmCont, frag1);
                fTrans.commit();
           */ //    Load_Task_list();


            }


        }
        else
        { //  first launch
         //   Load_Task_list();
            Log.d(LOG_TAG, "first launch mode");
            Log.d(LOG_TAG, " calling Load_Task_list()");
        //    frag1 = new general();
            Load_Task_list();
            Log.d(LOG_TAG, " Set current date in general head ");
            head1.setCurrent_date(current_date);
            fHead = getFragmentManager().beginTransaction();
            fHead.add(R.id.frgmHead, head1);
            fHead.commit();
            fTrans = getFragmentManager().beginTransaction();
            fTrans.add(R.id.frgmCont, frag1);
            fTrans.commit();
            Log.d("myLogs", "first launch completed");
            //-------------------------------
        }



    }

    public void someEvent(String s) {

        // add Case statement for s operation!!!

        switch (s) {
           case "SaveTask":  // saves task in edit mode and going to view mode
                SaveTask();
                break;
            case "Select_Date": // changed the date in head of general screen, then calling

                Log.d("myLogs", "date changed");
                // Update_Task_list(); to update task list for new selected day
                SelectDate();
            //    Update_Task_list();

                break;
            case "Load_Task_List":// load task list in general screen  for current_date
                Load_Task_list();
                Log.d("myLogs", "in case");
                break;
            case "EditCurTask": // open task in edit mode (new or task_id)
                reEditTask();
                break;
            case "Close":
                back_to_main();
                break;

        }
    }


    public void back_to_main(){

        Log.d("myLogs", "in the back_to_main()");
        head1.setCurrent_date(current_date);
        fHead = getFragmentManager().beginTransaction();
        fHead.remove(head2);
        fHead.remove(head3);
        fHead.add(R.id.frgmHead, head1);
        fHead.commit();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.remove(frag2);
        fTrans.remove(frag3);
        fTrans.add(R.id.frgmCont, frag1);
        fTrans.commit();
        Load_Task_list();
      // head1.updateCurrent_date(current_date);
        state="List";
        task_id=0;

    }

    public void reEditTask(){

       detail_view frag3 = (detail_view)getFragmentManager().findFragmentById(R.id.frgmCont);
        task_id =  frag3.cur_id; /// maybe need to change/delete this command
        state="Edit";
        Log.d("myLogs", "editable task id" + task_id);

        fHead = getFragmentManager().beginTransaction();
        fHead.remove(head3);
        fHead.add(R.id.frgmHead, head2);
        fHead.commit();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.remove(frag3);
        fTrans.add(R.id.frgmCont, frag2);
        fTrans.commit();

        String sqlstring = "SELECT * from tasks where id = "+ task_id ;
        curs = db.rawQuery(sqlstring,null);
        frag2.reedit_task( curs);

    }





    //--------------------selecting date-----------------
        public void SelectDate() {
            Log.d("myLogs", "open dialog to change date");
            showDialog(DIALOG_DATE);
      //      Log.d("myLogs", "the date is changed");

        }



            public void Update_Task_list(){
// this function working by updating date
           String sqlstring = "SELECT * from tasks where date = '"+current_date+ "' order by timebegin  ASC ";
           curs = db.rawQuery(sqlstring,null);
                Log.d("myLogs", "calling get_task_list function of general fragment");
           frag1.get_task_list(current_date, curs);
//                Fragment head1 = getFragmentManager().findFragmentById(R.id.frgmHead);
                //  head1= (head_gen)head1;
  //              ((head_gen) head1).updateCurrent_date(current_date);
                Log.d("myLogs", "update date in head general fragment");
                head1.updateCurrent_date(current_date);
    }
    public void Load_Task_list(){
// this function working by creating/loading fragment
        String sqlstring = "SELECT * from tasks where date = '"+current_date+ "' order by timebegin  ASC ";
        curs = db.rawQuery(sqlstring,null);
        Log.d("myLogs", "in activity cursor has "+ curs.getCount());
        Log.d("myLogs", "calling general fragment load_task_list function to give current day tasks cursor");
  //      frag1.curs=curs;
        frag1.load_task_list(current_date, curs);


    }

    protected Dialog onCreateDialog(int id) {

        Log.d("myLogs", "oncreatedialog");
        final Calendar cal = Calendar.getInstance();
        myYear = cal.get(Calendar.YEAR);
        myMonth = cal.get(Calendar.MONTH);
        myDay = cal.get(Calendar.DAY_OF_MONTH);
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
           tpd.getDatePicker().setCalendarViewShown(true);
           tpd.getDatePicker().setSpinnersShown(false);
           tpd.getDatePicker().init(myYear,myMonth,myDay,null);

            return tpd;
        }

        return super.onCreateDialog(id);

    }

    OnDateSetListener myCallBack = new OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Log.d("myLogs", "in listener");
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            Fragment frag2 = getFragmentManager().findFragmentById(R.id.frgmHead);
            ((TextView) frag2.getView().findViewById(R.id.selecteddate)).setText(myDay + "/" + (myMonth + 1) + "/" + myYear);
            current_date = myDay + "/" + (myMonth + 1) + "/" + myYear;
            Log.d("myLogs", "date changed to " + current_date);
            Log.d("myLogs", "calling function Update_Task_list");
            //  Load_Task_list();
            back_to_main();
        }
    };

    //-----------------------------------------------------------


    public  void SaveTask()
    {

        Log.d(LOG_TAG, "SAVE");
        detail frag2 = (detail)getFragmentManager().findFragmentById(R.id.frgmCont);
        Log.d("myLogs", "call string");
        String sqlstring=   frag2.getsqlstring(current_date);
        db.execSQL(sqlstring);
        Log.d("myLogs", "call string");
        sqlstring = "SELECT * from tasks order by id DESC limit 1";
        Cursor c;
        long   lastId=0;
        c = db.rawQuery(sqlstring,null);
      //  Cursor c = db.query("tasks","id", null,null,);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
            Log.d("myLogs", "call string"+lastId);
        }
//------------relace fragments to view mode
        fHead = getFragmentManager().beginTransaction();
        fHead.remove(head2);
        fHead.remove(head1);
        fHead.add(R.id.frgmHead, head3);
        fHead.commit();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.remove(frag2);
        fTrans.remove(frag1);

        fTrans.add(R.id.frgmCont, frag3);
        fTrans.commit();

        frag3.setviewmode(c);
        state="View";
        task_id=lastId;
    }


    public void onClick(View v) {
        fHead = getFragmentManager().beginTransaction();
        fHead.remove(head2);
        // fHead.remove(frag1);
        fHead.add(R.id.frgmHead, head3);
        fHead.commit();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.remove(frag2);
        fTrans.add(R.id.frgmCont, frag3);
        fTrans.commit();
    }


    public void EditTask (String taskID, String start, String finish)
    {

        Log.d("myLogs", " in the EditTask");
        if (taskID.equals("-1")) {

            Log.d("myLogs", "add new task");

            fHead = getFragmentManager().beginTransaction();
            fHead.remove(head1);
            fHead.add(R.id.frgmHead, head2);
            fHead.commit();
            fTrans = getFragmentManager().beginTransaction();
            fTrans.remove(frag1);
            fTrans.add(R.id.frgmCont, frag2);
            fTrans.commit();

            Log.d("myLogs", "add new task");
            frag2.add_task(start, finish);
        }

        else {

            Log.d("myLogs", "edit selected task");
            fHead = getFragmentManager().beginTransaction();
            fHead.remove(head1);
            fHead.add(R.id.frgmHead, head3);
            fHead.commit();
            fTrans = getFragmentManager().beginTransaction();
            fTrans.remove(frag1);
            fTrans.add(R.id.frgmCont, frag3);
            fTrans.commit();

            String sqlstring = "SELECT * from tasks where id = "+taskID ;

            curs = db.rawQuery(sqlstring,null);

            frag3.setviewmode( curs);
            Log.d("myLogs", "view selected task");
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

*/

    // DB class realization--------------------------------------------------
    class DBHelper extends SQLiteOpenHelper {

     //   private static final String LOG_TAG = OlimpicRaceSQLhelper.class.getName();

 //       private static final String DB_NAME = "masterrec";
    //    private static final String DB_FOLDER = "/data/data/"
    //            + App.getInstance().getPackageName() + "/databases/";
  //      private static final String DB_PATH = DB_FOLDER + DB_NAME;
       // private static final String DB_ASSETS_PATH = "db/" + DB_NAME;
   //     private static final int DB_VERSION = 1;

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "masterrec", null, 1);
        }

        @Override
        public void onCreate( SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
        //    int clearCount = db.delete("tasks", null, null);

            db.execSQL("create table tasks ("
                    + "id integer primary key autoincrement,"
                    + "date data,"
                    + "timebegin  time,"
                    + "timeend  time,"
                    + "client text,"
                    + "proc   text,"
                    + "descr text,"
                    + "vip int" + ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
