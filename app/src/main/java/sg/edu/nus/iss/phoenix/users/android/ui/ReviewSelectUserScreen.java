package sg.edu.nus.iss.phoenix.users.android.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.users.entity.User;

/**
 * Created by cherry on 27/09/2017.
 */

public class ReviewSelectUserScreen extends AppCompatActivity{

    private static final String TAG = ReviewSelectUserScreen.class.getName();

    private UserAdapter mUserAdapter;
    private ListView mListView;
    private User selectedUser = null;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_select_user);

        ArrayList<User> users = new ArrayList<User>();

        mUserAdapter = new UserAdapter(this,users);

        mListView = (ListView) findViewById(R.id.review_select_user_list);
        mListView.setAdapter(mUserAdapter);

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio program at position " + position + " selected.");
                User user = (User) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgramName());
                selectedUser = user;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getReviewSelectUserController().onDisplay(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_review_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_select:
                if (selectedUser == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a user first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected user.");
                }
                else {
                    Log.v(TAG, "Selected user: " + selectedUser.getName() + ".");
                    ControlFactory.getReviewSelectUserController().selectUser(selectedUser);
                }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getReviewSelectUserController().selectCancel();
    }

    public void showUsers(List<User> users){
        mUserAdapter.clear();
        for(int i = 0; i< users.size(); i ++){
            mUserAdapter.add(users.get(i));
        }
    }
}
