package sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleProgramController;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_PROGRAM;

/**
 * Created by nivi on 9/25/2017.
 */

public class DeleteScheduleProgramDelegate extends AsyncTask<String, Void, Boolean> {

    // Tag for logging
    private static final String TAG = DeleteScheduleProgramDelegate.class.getName();

    private final ScheduleProgramController scheduleProgramController;

    public DeleteScheduleProgramDelegate(ScheduleProgramController scheduleProgramController) {
        this.scheduleProgramController = scheduleProgramController;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // Encode the name of radio program in case of the presence of special characters.
        String duration = null;
        String dateOfProgram = null;
        try {
            duration = URLEncoder.encode(params[0], "UTF-8");
            dateOfProgram = URLEncoder.encode(params[1], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE_PROGRAM).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"delete").buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, duration).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, dateOfProgram).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setUseCaches (false);
            System.out.println(httpURLConnection.getResponseCode());
            Log.v(TAG, "Http DELETE response " + httpURLConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        scheduleProgramController.scheduleProgramDeleted(result.booleanValue());
    }
}
