package sg.edu.nus.iss.phoenix.users.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
 * Created by cherry on 25/09/2017.
 */

public class UserListScreen extends AppCompatActivity{

    private static final String TAG = UserListScreen.class.getName();

    private ListView mListView;
    private UserAdapter mUserAdapter;
    private User selectedUser = null;
    private String username;
    private String roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        roles = intent.getStringExtra("username");

        ArrayList<User> users = new ArrayList<User>();
        mUserAdapter = new UserAdapter(this, users);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_userlist);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roles.contains("admin")) {
                    ControlFactory.getUserController().selectCreateUser();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No priviledge", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mListView = (ListView) findViewById(R.id.user_list);
        mListView.setAdapter(mUserAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        if (roles.contains("admin")) {
            ControlFactory.getUserController().onDisplayUserList(this);
        }else{
            ControlFactory.getUserController().onDisplayUserList(this, username);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_update:
                if (selectedUser == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a user first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected user.");
                }

                else {
                    //if (roles.contains("admin") || username.equals(selectedUser.getIdNo())) {
                        Log.v(TAG, "Viewing user: " + selectedUser.getName() + ".");
                        ControlFactory.getUserController().selectEditUser(selectedUser,username,roles);
                    //}
                    //else{
                     //   Toast.makeText(this, username + " " + selectedUser.getName(), Toast.LENGTH_SHORT).show();
                    //}
                }
                return true;

            case R.id.action_delete:
                Log.v(TAG, "Deleting user " + selectedUser.getName() + "...");
                ControlFactory.getUserController().selectDeleteUser(selectedUser);
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getUserController().maintainedUser();
    }

    public void showUsers(List<User> users) {
        mUserAdapter.clear();
        for (int i = 0; i < users.size(); i++) {
            mUserAdapter.add(users.get(i));
        }
    }
}
