package sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ReviewSelectScheduleProgramController;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleProgramController;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

/**
 * Created by nivi on 9/7/2017.
 */

public class RetrieveScheduleProgramsDelegate extends AsyncTask<String, Void, String> {

    // Tag for logging
    private static final String TAG = RetrieveScheduleProgramsDelegate.class.getName();

    private ScheduleProgramController scheduleProgramController = null;
    private ReviewSelectScheduleProgramController reviewSelectScheduleProgramController = null;

    public RetrieveScheduleProgramsDelegate(ScheduleProgramController scheduleProgramController) {
        this.reviewSelectScheduleProgramController = null;
        this.scheduleProgramController = scheduleProgramController;
    }

    public RetrieveScheduleProgramsDelegate(ReviewSelectScheduleProgramController reviewSelectScheduleProgramController) {
        this.scheduleProgramController = null;
        this.reviewSelectScheduleProgramController = reviewSelectScheduleProgramController;
    }
    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse(PRMS_BASE_URL_RADIO_PROGRAM).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return e.getMessage();
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) jsonResp = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return jsonResp;
    }

    @Override
    protected void onPostExecute(String result) {
        List<ScheduleProgram> schedulePrograms = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray spArray = reader.getJSONArray("psList");

                for (int i = 0; i < spArray.length(); i++) {
                    JSONObject spJson = spArray.getJSONObject(i);
                    String name = spJson.getString("programName");
                    String dateOfProgram = spJson.getString("dateOfProgram");
                    String startTime = spJson.getString("startTime");
                    String duration = spJson.getString("duration");
                    Log.v(TAG, name);
                    schedulePrograms.add(new ScheduleProgram(name, dateOfProgram, startTime, duration));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (scheduleProgramController != null)
            scheduleProgramController.scheduleProgramsRetrieved(schedulePrograms);
        else if (reviewSelectScheduleProgramController != null)
            reviewSelectScheduleProgramController.scheduleProgramsRetrieved(schedulePrograms);
    }
}
