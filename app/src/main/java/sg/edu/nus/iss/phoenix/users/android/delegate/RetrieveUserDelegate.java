package sg.edu.nus.iss.phoenix.users.android.delegate;

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

import sg.edu.nus.iss.phoenix.users.android.controller.ReviewSelectUserController;
import sg.edu.nus.iss.phoenix.users.android.controller.UserController;
import sg.edu.nus.iss.phoenix.users.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * Created by cherry on 27/09/2017.
 */

public class RetrieveUserDelegate extends AsyncTask<String, Void, String> {

    private static final String TAG = RetrieveUserDelegate.class.getName();

    private UserController userController = null;
    private ReviewSelectUserController reviewSelectUserController = null;

    public void RetrieveUserDelegate(UserController userController){
        this.reviewSelectUserController = null;
        this.userController = userController;
    }

    public void RetrieveUserDelegate(ReviewSelectUserController reviewSelectUserController){
        this.userController = null;
        this.reviewSelectUserController = reviewSelectUserController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse( PRMS_BASE_URL_USER).buildUpon().build();
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
        List<User> users = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray rpArray = reader.getJSONArray("userList");

                for (int i = 0; i < rpArray.length(); i++) {
                    JSONObject userJson = rpArray.getJSONObject(i);
                    String id = userJson.getString("id");
                    String name = userJson.getString("name");
                    String department = userJson.getString("dept");
                    String position = userJson.getString("role");
                    String addr = userJson.getString("addr");

                    users.add(new User(id, name, department,position,addr));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (userController != null)
            userController.usersRetrieved(users);
        else if (reviewSelectUserController != null)
            reviewSelectUserController.userRetrieved(users);
    }

}
