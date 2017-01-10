package edu.pdx.jwaldrip.ece558f16.project4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by jonathanwaldrip on 12/3/16.
 * Code Source BNRG Criminal Intent exercise
 */

public class ResultListFragment extends Fragment {

    private RecyclerView mResultRecyclerView;
    private ResultAdapter mAdapter;
    private ArrayList<Result> mResults;
    LoginDataBaseAdapter loginDataBaseAdapter;

    // Create new RecyvlerView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_result_list, container, false);

        mResultRecyclerView = (RecyclerView) v
                .findViewById(R.id.result_recycler_view);
        mResultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Creates object for LoginDataBaseAdapter to gain access to database
        loginDataBaseAdapter=new LoginDataBaseAdapter(ResultListActivity.context);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // call updateUI method to pupulate the RecyclerView
        updateUI();

        return v;
    }

    // create ArrayList of results from database
    private void updateUI() {

        mResults = new ArrayList<>();

        // walk through database row by row
        for (int i = 1; i <= 100000; i++) {
            Result result = new Result();
            String s = loginDataBaseAdapter.getEntry(String.valueOf(i));

            // end of result is reached
            if (s.equals("Not Yet Recorded")) {
                if (i == 1) {
                    Toast.makeText(ResultListActivity.context, ("No Results to Display \nPlease Press Back and Then Run"), Toast.LENGTH_LONG).show();
                }
                break;

                // add returned string from database to ArrayList
            } else {
                result.setInstanceNumber("Result #" + i);
                result.setResult(s);
                mResults.add(result);
            }
        }

        // create and set adapter for the RecyclerView with ArrayList created above
        mAdapter = new ResultAdapter(mResults);
        mResultRecyclerView.setAdapter(mAdapter);
    }


    // Build the entry for the RecyclerView with results
    private class ResultHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mResultText;
        private TextView mTimeStampText;
        private Result mResult;

        public ResultHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mResultText = (TextView) itemView.findViewById(R.id.list_item_result_title_text_view);
            mTimeStampText = (TextView) itemView.findViewById(R.id.list_item_result_date_text_view);

        }

        // Bind each results to each entry in RecyclerView
        public void bindResult(Result result) {
            mResult = result;
            mResultText.setText(mResult.getResult());
            mTimeStampText.setText(mResult.getInstanceNumber());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mResult.getResult() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultHolder> {

        private ArrayList<Result> mResults;

        public ResultAdapter(ArrayList<Result> results) {
            mResults = results;
        }

        // create view for result holder
        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_result, parent, false);
            return new ResultHolder(view);
        }

        // bind view holder with individual results
        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            Result result = mResults.get(position);
            holder.bindResult(result);
        }

        // Get number of results in list
        @Override
        public int getItemCount() {
            return mResults.size();
        }
    }
}