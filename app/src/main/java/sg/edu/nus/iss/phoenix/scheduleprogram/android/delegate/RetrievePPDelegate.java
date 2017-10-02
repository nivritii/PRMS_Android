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

import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleProgramController;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

public class RetrievePPDelegate extends AsyncTask<String, Void, List<String>> {
    // Tag for logging
    private static final String TAG = RetrievePPDelegate.class.getName();

    private ScheduleProgramController scheduleProgramController = null;

    public RetrievePPDelegate(ScheduleProgramController scheduleProgramController) {
        this.scheduleProgramController = scheduleProgramController;
    }


    @Override
    protected List<String> doInBackground(String... params) {
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_USER).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            //return e.getMessage();
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

        return formListRP(jsonResp);
    }

    @Override
    protected void onPostExecute(List<String> radioPrograms) {
    }

    private List<String> formListRP(String jsonResp){
        List<String> radioPrograms = new ArrayList<>();

        if (jsonResp != null && !jsonResp.equals("")) {
            try {
                JSONObject reader = new JSONObject(jsonResp);
                JSONArray rpArray = reader.getJSONArray("userList");

                for (int i = 0; i < rpArray.length(); i++) {
                    JSONObject rpJson = rpArray.getJSONObject(i);
                    //String description = rpJson.getString("description");
                    String name = rpJson.getString("name");
                    //String typicalDuration = rpJson.getString("typicalDuration");
                    Log.v(TAG, name);
                    radioPrograms.add(name);
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        return radioPrograms;

        /*if (programController != null)
            programController.programsRetrieved(radioPrograms);
        else if (reviewSelectProgramController != null)
            reviewSelectProgramController.programsRetrieved(radioPrograms);*/
    }
}
