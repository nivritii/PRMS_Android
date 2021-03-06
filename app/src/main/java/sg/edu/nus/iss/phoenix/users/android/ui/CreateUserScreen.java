package sg.edu.nus.iss.phoenix.users.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.users.entity.User;

/**
 * Created by cherry on 25/09/2017.
 */

public class CreateUserScreen extends AppCompatActivity {

    private static final String TAG = CreateUserScreen.class.getName();

    private EditText mIdEditText;
    private EditText mNameEditText;
    private EditText mRoleEditText;

    private EditText mPasswordEditText;

    private User userEdit = null;
    private String roles;
    private String username;

    KeyListener mIdEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        roles = intent.getStringExtra("roles");

        // Find all relevant views that we will need to read user input from
        mIdEditText = (EditText) findViewById(R.id.et_create_idno);
        mNameEditText = (EditText) findViewById(R.id.et_create_username);
        mRoleEditText = (EditText) findViewById(R.id.et_create_roles);

        mPasswordEditText = (EditText) findViewById(R.id.et_create_password);
        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mIdEditTextKeyListener = mIdEditText.getKeyListener();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getUserController().onDisplayUser(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);


            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save radio program.
                if (userEdit == null) { // Newly created.
                    Log.v(TAG, "Saving user " + mIdEditText.getText().toString() + "...");
                    User user = new User(mIdEditText.getText().toString(),mPasswordEditText.getText().toString(),mNameEditText.getText().toString(),
                            mRoleEditText.getText().toString() );
                    User userLogin = new User(username, roles);
                    ControlFactory.getUserController().selectSaveUser(user, userLogin);
                }
                else { // Edited.
                    Log.v(TAG, "Update user " + userEdit.getName() + "...");
                    userEdit.setIdNo(mIdEditText.getText().toString());
                    userEdit.setName(mNameEditText.getText().toString());
                    userEdit.setRoles(mRoleEditText.getText().toString());
                    userEdit.setPassword(mPasswordEditText.getText().toString());
                    User userLogin = new User(username, roles);
                    ControlFactory.getUserController().selectUpdateUser(userEdit, userLogin);
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                Log.v(TAG, "Deleting user " + userEdit.getName() + "...");
                User userLogin = new User(username, roles);
                ControlFactory.getUserController().selectDeleteUser(userEdit, username,roles);
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                Log.v(TAG, "Canceling creating/editing user...");
                ControlFactory.getUserController().selectCancelCreateEditUser(username, roles);
                return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "Canceling creating/editing user...");
        ControlFactory.getUserController().selectCancelCreateEditUser(username, roles);
    }

    public void createUser() {
        this.userEdit = null;
        mIdEditText.setText("", TextView.BufferType.EDITABLE);
        mNameEditText.setText("", TextView.BufferType.EDITABLE);
        mRoleEditText.setText("", TextView.BufferType.EDITABLE);
        mPasswordEditText.setText("", TextView.BufferType.EDITABLE);
        mIdEditText.setKeyListener(mIdEditTextKeyListener);
    }

    public void editUser(User userEdit) {
        this.userEdit = userEdit;
        if (userEdit != null) {
            mIdEditText.setText(userEdit.getIdNo(),TextView.BufferType.NORMAL);
            mIdEditText.setFocusable(false);
            mIdEditText.setFocusableInTouchMode(false);
            mNameEditText.setText(userEdit.getName(),TextView.BufferType.EDITABLE);
            mRoleEditText.setText(userEdit.getRoles(),TextView.BufferType.EDITABLE);
            mPasswordEditText.setText(userEdit.getPassword(),TextView.BufferType.EDITABLE);
            mIdEditText.setKeyListener(null);
        }
    }
}
