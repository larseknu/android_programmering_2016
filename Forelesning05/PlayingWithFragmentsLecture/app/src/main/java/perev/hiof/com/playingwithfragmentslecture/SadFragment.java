package perev.hiof.com.playingwithfragmentslecture;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SadFragment extends Fragment {

    private static final String LOGTAG = "SadFragment";

    public SadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sad, container, false);
    }

    @Override
    public void onAttach(Activity activity){
        Log.d(LOGTAG, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Log.d(LOGTAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart(){
        Log.d(LOGTAG, "onStart");
        super.onStart();
    }

    @Override
    public void onStop(){
        Log.d(LOGTAG, "onStop");
        super.onStop();
    }

    @Override
    public void onPause(){
        Log.d(LOGTAG, "onPause");
        super.onPause();
    }

    @Override
    public void onResume(){
        Log.d(LOGTAG, "onResume");
        super.onResume();
    }

    @Override
    public void onDestroyView(){
        Log.d(LOGTAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy(){
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach(){
        Log.d(LOGTAG, "onDetach");
        super.onDetach();
    }

}
