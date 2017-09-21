package sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.CreateProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleProgramController;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;

/**
 * Created by nivi on 9/21/2017.
 */

public class CreateScheduleProgramDelegate extends AsyncTask<ScheduleProgram, Void, Boolean> {

    // Tag for logging
    private static final String TAG = CreateScheduleProgramDelegate.class.getName();

    private final ScheduleProgramController scheduleProgramController;

    public CreateScheduleProgramDelegate(ScheduleProgramController scheduleProgramController) {
        this.scheduleProgramController = scheduleProgramController;
    }

    @Override
    protected Boolean doInBackground(ScheduleProgram... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_RADIO_PROGRAM).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"createps").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("programName", params[0].getName());
            json.put("dateOfProgram", params[0].getDateOfProgram());
            json.put("startTime", params[0].getStartTime());
            json.put("duration", params[0].getDuration());
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(256);
            Log.v(TAG, "Http PUT response " + httpURLConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        scheduleProgramController.scheduleProgramCreated(result.booleanValue());
    }

}
