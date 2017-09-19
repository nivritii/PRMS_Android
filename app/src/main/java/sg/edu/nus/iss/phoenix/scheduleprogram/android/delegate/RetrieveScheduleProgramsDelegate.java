package sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ReviewSelectScheduleProgramController;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleProgramController;

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
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
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
}
