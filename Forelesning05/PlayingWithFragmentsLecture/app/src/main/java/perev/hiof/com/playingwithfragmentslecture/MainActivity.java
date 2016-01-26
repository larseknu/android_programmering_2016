package perev.hiof.com.playingwithfragmentslecture;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String _dynamicFragments = "DynamicFragments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtnAdd(View view){
        FragmentManager fragmentManager = getFragmentManager();
        HappyFragment happyFragment = new HappyFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainer, happyFragment, _dynamicFragments);
        fragmentTransaction.addToBackStack("Add");
        fragmentTransaction.commit();
    }

    public void onClickBtnRemove(View view){
        FragmentManager fragmentManager = getFragmentManager();
        SadFragment sadFragment = new SadFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment oldFragment = fragmentManager.findFragmentByTag(_dynamicFragments);

        if(oldFragment != null){
            fragmentTransaction.remove(oldFragment);
        }
        fragmentTransaction.add(R.id.fragmentContainer, sadFragment, _dynamicFragments);

        fragmentTransaction.addToBackStack("Remove");
        fragmentTransaction.commit();

    }

    public void onClickBtnReplace(View view){
        FragmentManager fragmentManager = getFragmentManager();
        HappyFragment happyFragment = new HappyFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentContainer, happyFragment, _dynamicFragments);
        fragmentTransaction.addToBackStack("Replace");
        fragmentTransaction.commit();
    }

    public void onClickBtnDetach(View view){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.detach(fragmentManager.findFragmentByTag(_dynamicFragments));
        fragmentTransaction.addToBackStack("Detach");
        fragmentTransaction.commit();
    }

    public void onClickBtnAttach(View view){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.attach(fragmentManager.findFragmentByTag(_dynamicFragments));
        fragmentTransaction.addToBackStack("Attach");
        fragmentTransaction.commit();
    }

    public void onClickBtnPrevious(View view){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }
}
