package sg.edu.nus.iss.phoenix.radiopresenter.android.delegate;

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


import sg.edu.nus.iss.phoenix.radiopresenter.android.controller.PresenterController;
import sg.edu.nus.iss.phoenix.radiopresenter.android.controller.ReviewSelectPresenterController;
import sg.edu.nus.iss.phoenix.radiopresenter.entity.RadioPresenter;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PRESENTER;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;

public class RetrievePresentersDelegate extends AsyncTask<String, Void, String> {
    // Tag for logging
    private static final String TAG = RetrievePresentersDelegate.class.getName();

    private PresenterController programController = null;
    private ReviewSelectPresenterController reviewSelectPresenterController = null;

    public RetrievePresentersDelegate(PresenterController programController) {
        this.reviewSelectPresenterController = null;
        this.programController = programController;
    }

    public RetrievePresentersDelegate(ReviewSelectPresenterController reviewSelectPresenterController) {
        this.programController = null;
        this.reviewSelectPresenterController = reviewSelectPresenterController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_RADIO_PRESENTER).buildUpon().build();
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
        Log.v("Databaseresult",jsonResp);
        return jsonResp;
    }

    @Override
    protected void onPostExecute(String result) {
        List<RadioPresenter> radioPresenters = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                Log.v("InsidePostExecute",result);
                JSONObject reader = new JSONObject(result);
                JSONArray rpArray = reader.getJSONArray("rpList");

                for (int i = 0; i < rpArray.length(); i++) {
                    JSONObject rpJson = rpArray.getJSONObject(i);
                    //String description = rpJson.getString("description");
                        String name = rpJson.getString("name");
                     //String typicalDuration = rpJson.getString("typicalDuration");
                    Log.v(TAG, name);
                    radioPresenters.add(new RadioPresenter(name, "", ""));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (reviewSelectPresenterController != null)
            reviewSelectPresenterController.presentersRetrieved(radioPresenters);
        else if (reviewSelectPresenterController != null)
            reviewSelectPresenterController.presentersRetrieved(radioPresenters);
    }
}
