package sg.edu.nus.iss.phoenix.users.android.delegate;

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

import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.UpdateProgramDelegate;
import sg.edu.nus.iss.phoenix.users.android.controller.UserController;
import sg.edu.nus.iss.phoenix.users.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * Created by cherry on 27/09/2017.
 */

public class UpdateUserDelegate extends AsyncTask<User,Void,Boolean> {
    private static final String TAG = UpdateProgramDelegate.class.getName();

    private String username;
    private String roles;

    private final UserController userController;

    public UpdateUserDelegate(UserController userController){
        this.userController = userController;
    }

    @Override
    protected Boolean doInBackground(User... params) {
        username = params[1].getIdNo();
        roles = params[1].getRoles();

        Uri builtUri = Uri.parse(PRMS_BASE_URL_USER).buildUpon()
                .appendQueryParameter("id", params[0].getIdNo())
                .appendQueryParameter("name", params[0].getName())
                .appendQueryParameter("password", params[0].getPassword())
                .appendQueryParameter("roles", params[0].getRoles())
                .build();
        builtUri = Uri.withAppendedPath(builtUri,"item").buildUpon().
                build();
        Log.v(TAG + " UPDATE: ", builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("id", params[0].getIdNo());
            json.put("name", params[0].getName());
            json.put("roles", params[0].getRoles());

            json.put("password", params[0].getPassword());
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(512);
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
        userController.userUpdated(result.booleanValue(), username, roles);
    }
}
