package edu.pdx.jwaldrip.ece558f16.project4;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by jonathanwaldrip on 12/3/16.
 * Code Source BNRG Criminal Intent exercise
 */

public class ResultListActivity extends SingleFragmentActivity {

    public static Context context;

    @Override
    protected Fragment createFragment() {

        // public context member for use in fragment
        context = this;

        // create new fragment
        return new ResultListFragment();
    }

    // intent used to launch this activity
    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ResultListActivity.class);
        return i;
    }
}
