package haykkh.android.personale.com.masterrec;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class head_detail extends Fragment {

    onSomeEventListener someEventListener;

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
        View v = inflater.inflate(R.layout.fragment_head_detail, null);

        // button listeners---------------------------------

        // Save
        Button btnsave = (Button) v.findViewById(R.id.Save);
        btnsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                someEventListener.someEvent("SaveTask");

            }
        });

        // Close
        Button btnclose = (Button) v.findViewById(R.id.Close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                someEventListener.someEvent("Close");

            }
        });


        //---------------------------------------------------------------

        return v;
    }


}
