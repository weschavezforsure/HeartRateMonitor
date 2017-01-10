package edu.pdx.jwaldrip.ece558f16.project4;

import java.util.Calendar;
import java.util.UUID;

/*
 * The Result object holds the date and readings of single
 * heart reate measurement
 */

public class Result {

    private UUID mId;           // unique identifier for each instance
    private String mResult;     // string containing the results of the heart rate measurement
    private String mInstanceNumber;  // date/time stamp of heart rate measurement

    // constructor
    public Result() {
        mId = UUID.randomUUID();
        mResult = "Today - 75";
        mInstanceNumber = "Result #0";
    }

    // getters and setters
    public UUID getId() {
        return mId;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public String getInstanceNumber() {
        return mInstanceNumber;
    }

    public void setInstanceNumber(String instanceNumber) {
        mInstanceNumber = instanceNumber;
    }

    @Override
    public String toString() {

        return (mInstanceNumber + "Heartrate: " + mResult);
    }
}
