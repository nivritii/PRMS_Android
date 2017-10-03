package sg.edu.nus.iss.phoenix.core.android.controller;

import android.app.Application;
import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.users.entity.User;

public class MainController {
    private static Application app = null;
    private String username;
    private MainScreen mainScreen;

    public static Application getApp() {
        return app;
    }

    public static void setApp(Application app) {
        MainController.app = app;
    }

    public static void displayScreen(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(intent);
    }

    public void startUseCase(String username, String roles) {
        this.username = username;

        Intent intent = new Intent(MainController.getApp(), MainScreen.class);
        intent.putExtra("roles", roles);
        intent.putExtra("username",username);
        MainController.displayScreen(intent);
    }

    public void onDisplay(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        mainScreen.showUsername(username);
    }

    public void selectMaintainProgram() {

        ControlFactory.getProgramController().startUseCase();
    }
    public void selectMaintainUser(String username, String roles){
        ControlFactory.getUserController().startUseCase(username, roles);
    }

    public void maintainedProgram() {

        startUseCase(username,"");
    }
    public void maintainedUser(){startUseCase(username,"");}

    public void selectLogout() {
        username = "<not logged in>";
        ControlFactory.getLoginController().logout();
    }

    public void selectMaintainSchedule(String username,String roles) {
        // This is the placeholder for starting the Maintain Schedule use case.
       ControlFactory.getScheduleProgramController().startUseCase(username, roles);

    }

    // This is a dummy operation to test the invocation of Review Select Radio Program use case.
    public void selectedProgram(RadioProgram rpSelected) {
        startUseCase(username,"");
    }
    public void selectedUser(User userSelected) { startUseCase(username,"");}
}
