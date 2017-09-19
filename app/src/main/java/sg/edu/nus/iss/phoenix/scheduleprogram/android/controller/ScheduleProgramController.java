package sg.edu.nus.iss.phoenix.scheduleprogram.android.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveScheduleProgramsDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ScheduleListScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;

/**
 * Created by nivi on 9/6/2017.
 */

public class ScheduleProgramController {
    // Tag for logging.
    private static final String TAG = ScheduleProgramController.class.getName();

    private ScheduleListScreen scheduleListScreen;
    private MaintainScheduleScreen maintainScheduleScreen;
    private ScheduleProgram sp2edit = null;

    public void startUseCase() {
        sp2edit = null;
        Intent intent = new Intent(MainController.getApp(), ScheduleListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayProgramList(ScheduleListScreen scheduleListScreen) {
        this.scheduleListScreen = scheduleListScreen;
        new RetrieveScheduleProgramsDelegate(this).execute("all");
    }

}
