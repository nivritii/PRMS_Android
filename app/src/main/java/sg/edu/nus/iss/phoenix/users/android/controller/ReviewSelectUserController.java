package sg.edu.nus.iss.phoenix.users.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.users.android.delegate.RetrieveUserDelegate;
import sg.edu.nus.iss.phoenix.users.android.ui.ReviewSelectUserScreen;
import sg.edu.nus.iss.phoenix.users.entity.User;

/**
 * Created by cherry on 27/09/2017.
 */

public class ReviewSelectUserController {

    private static final String TAG = ReviewSelectUserController.class.getName();

    private ReviewSelectUserScreen reviewSelectUserScreen;
    private User userSelected = null;

    public void startUseCase(){
        userSelected = null;
        Intent intent = new Intent(MainController.getApp(),ReviewSelectUserScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectUserScreen reviewSelectUserScreen){
        this.reviewSelectUserScreen = reviewSelectUserScreen;
        new RetrieveUserDelegate().execute("all");
    }

    public void userRetrieved(List<User> users){
        reviewSelectUserScreen.showUsers(users);
    }

    public void selectUser(User user){
        userSelected = user;
        Log.v(TAG,"select user: " + user.getName() + ".");
        ControlFactory.getMainController().selectedUser(userSelected);
    }

    public void selectCancel(){
        userSelected = null;
        Log.v(TAG,"selected user cancelled.");
        ControlFactory.getMainController().selectedUser(userSelected);
    }


}
