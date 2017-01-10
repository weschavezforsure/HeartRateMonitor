package edu.pdx.jwaldrip.ece558f16.project4;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jonathanwaldrip on 12/2/16.
 */

public class MainActivity extends AppCompatActivity {

    private Button mRunButton, mResultsButton, mClearButton;
    LoginDataBaseAdapter loginDataBaseAdapter;
    private static final int RESULTS_CODE = 123;
    private static final String EXTRA_RESULT_HEART_RATE =
            "edu.pdx.jwaldrip.project4_Heart_Rate";
    private int mHeartRate;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // public context member for use in dialog
        context = this;

        // Creates object for LoginDataBaseAdapter to gain access to database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();


        // when run button is pressed
        mRunButton = (Button) findViewById(R.id.run_button);
        mRunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Launch heart rate monitor activity
                Intent i = CameraActivity.newIntent(MainActivity.this);
                startActivityForResult(i, RESULTS_CODE);
            }
        });

        // when results button is pressed call results activity
        mResultsButton = (Button) findViewById(R.id.results_button);
        mResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = ResultListActivity.newIntent(MainActivity.this);
                startActivity(i);
            };
        });


        // when clear button is pressed show confirm dialog
        mClearButton = (Button) findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getSupportFragmentManager();
                ConfirmDeleteFragment dialog = ConfirmDeleteFragment
                        .newInstance();
                dialog.setTargetFragment(dialog, 0);
                dialog.show(manager, "TEST");
            };
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

    /**
     * Actions on activites closing
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // check to see if it is correct activity that was closed
        if (resultCode == RESULT_OK && requestCode == RESULTS_CODE) {
            if (data == null) {
                return;
            } else {

                // get heart rate from intent extra
                mHeartRate = CameraActivity.getHeartRate(data);

                // get current date/time
                Calendar timestamp = Calendar.getInstance();
                Date date = timestamp.getTime();

                // create new result object
                Result result = new Result();
                result.setResult(String.valueOf(mHeartRate));
                result.setInstanceNumber(date.toString());

                // Save the Data in Database
                loginDataBaseAdapter.insertEntry(result.getResult(), result.getInstanceNumber());
                Toast.makeText(getApplicationContext(), ("Saved!"), Toast.LENGTH_SHORT).show();

            }
        }
    }
}
