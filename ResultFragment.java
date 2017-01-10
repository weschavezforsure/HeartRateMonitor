package edu.pdx.jwaldrip.ece558f16.project4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jonathanwaldrip on 12/3/16.
 * Code Source BNRG Criminal Intent exercise
 */

public class ResultFragment extends Fragment {

    private Result mResult;
    private TextView mResultText;
    private TextView mTimeStampText;

    // Create new result object
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResult = new Result();
    }

    // inflate the view, populate with data from a result object, and return view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result, container, false);

        mResultText = (TextView) v.findViewById(R.id.result_title);
        mResultText.setText(mResult.getResult());

        mTimeStampText = (TextView) v.findViewById(R.id.result_timestamp);
        mTimeStampText.setText(mResult.getInstanceNumber());

        return v;
    }
}