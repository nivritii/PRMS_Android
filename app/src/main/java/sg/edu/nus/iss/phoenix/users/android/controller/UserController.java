package sg.edu.nus.iss.phoenix.users.android.controller;

/**
 * Created by cherry on 25/09/2017.
 */

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.users.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.users.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.users.android.delegate.RetrieveUserDelegate;
import sg.edu.nus.iss.phoenix.users.android.delegate.UpdateUserDelegate;
import sg.edu.nus.iss.phoenix.users.android.ui.CreateUserScreen;
import sg.edu.nus.iss.phoenix.users.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.users.entity.User;

public class UserController {

    private static final String TAG = UserController.class.getName();

    private CreateUserScreen createUserScreen;
    private UserListScreen userListScreen;
    private User userEdit;

    public void startUseCase(){
        userEdit = null;

        Intent intent = new Intent(MainController.getApp(),UserListScreen.class);

        MainController.displayScreen(intent);
    }

    public void startUseCase(String username, String roles){
        userEdit = null;

        Intent intent = new Intent(MainController.getApp(),UserListScreen.class);
        intent.putExtra("username",username);
        intent.putExtra("roles", roles);

        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserListScreen userListScreen){
        this.userListScreen = userListScreen;
        new RetrieveUserDelegate(this).execute("item","");
    }

    public void onDisplayUserList(UserListScreen userListScreen, String username){
        this.userListScreen = userListScreen;
        new RetrieveUserDelegate(this).execute("single", username);

    }

    public void usersRetrieved(List<User> users){
        userListScreen.showUsers(users);
    }

    public void selectCreateUser(String username, String roles) {
        userEdit = null;
        Intent intent = new Intent(MainController.getApp(),CreateUserScreen.class);
        intent.putExtra("username",username);
        intent.putExtra("roles",roles);
        MainController.displayScreen(intent);
    }

    public void selectEditUser(User user,String username, String roles) {
        userEdit = user;
        Log.v(TAG, "Editing user: " + user.getName() + "...");

        Intent intent = new Intent(MainController.getApp(), CreateUserScreen.class);
        intent.putExtra("roles",roles);
        intent.putExtra("username",username);
/*        Bundle b = new Bundle();
        b.putString("Name", radioProgram.getRadioProgramName());
        b.putString("Description", radioProgram.getRadioProgramDescription());
        b.putString("Duration", radioProgram.getRadioProgramDuration());
        intent.putExtras(b);
*/
        MainController.displayScreen(intent);
    }

    public void onDisplayUser(CreateUserScreen createUserScreen) {
        this.createUserScreen = createUserScreen;
        if (userEdit == null)
            createUserScreen.createUser();
        else
            createUserScreen.editUser(userEdit);
    }

    public void selectUpdateUser(User user, User userLogin) {
        new UpdateUserDelegate(this).execute(user,userLogin);
    }

    public void selectDeleteUser(User user, String username, String roles) {
        new DeleteUserDelegate(this).execute(user.getIdNo(), username, roles);
    }

    public void userDeleted(boolean success, String username, String roles) {
        // Go back to UserList screen with refreshed users.
        startUseCase(username, roles);
    }

    public void userUpdated(boolean success, String username, String roles) {
        // Go back to UserList screen with refreshed users.
        startUseCase(username, roles);
    }

    public void selectSaveUser(User user, User userLogin) {
        new CreateUserDelegate(this).execute(user,userLogin);
    }

    public void selectCreateUser(User user, String username, String roles) {
        new CreateUserDelegate(this).execute(user);
    }

    public void userCreated(boolean success, String username, String roles) {
        startUseCase(username, roles);
    }

    public void selectCancelCreateEditUser(String username, String roles) {
        startUseCase(username, roles);
    }

    public void maintainedUser() {
        ControlFactory.getMainController().maintainedUser();
    }

}
