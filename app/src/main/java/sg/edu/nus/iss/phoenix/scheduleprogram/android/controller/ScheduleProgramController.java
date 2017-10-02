package sg.edu.nus.iss.phoenix.scheduleprogram.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.RetrieveProgramsDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.MaintainProgramScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.CreateScheduleProgramDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.DeleteScheduleProgramDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrievePPDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveRadioProgramDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveScheduleProgramsDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveWeeklySchedulesDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.UpdateScheduleProgramDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.CopyScheduleScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ScheduleListScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.WeeklySchListScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.WeeklySchedule;

/**
 * Created by nivi on 9/6/2017.
 */

public class ScheduleProgramController {
    // Tag for logging.
    private static final String TAG = ScheduleProgramController.class.getName();

    private ScheduleListScreen scheduleListScreen;
    private WeeklySchListScreen weeklySchListScreen;
    private MaintainScheduleScreen maintainScheduleScreen;
    private CopyScheduleScreen copyScheduleScreen;
    private ScheduleProgram sp2edit = null;
    private WeeklySchedule ws2view = null;

    public static List<String> radioPrograms;
    public static List<String> presenters;
    public static List<String> producers;

    public void startUseCase() {
        sp2edit = null;

        Intent intent = new Intent(MainController.getApp(), WeeklySchListScreen.class);
        MainController.displayScreen(intent);
    }


    public void onDisplayWeeklySchList(WeeklySchListScreen weeklySchListScreen){
        this.weeklySchListScreen = weeklySchListScreen;
        new RetrieveWeeklySchedulesDelegate(this).execute("weeklyslots");
    }

    public void onDisplayProgramList(ScheduleListScreen scheduleListScreen) {
        this.scheduleListScreen = scheduleListScreen;
        new RetrieveScheduleProgramsDelegate(this).execute("weeklyslots/" +ws2view.getStartDate());
    }

    public void scheduleProgramsRetrieved(List<ScheduleProgram> schedulePrograms) {
        scheduleListScreen.showSchedulePrograms(schedulePrograms);
    }

    public void weeklySchedulesRetrieved(List<WeeklySchedule> weeklySchedules) {
        weeklySchListScreen.showWeeklySchedules(weeklySchedules);
    }

    public void selectCreateScheduleProgram() {
        sp2edit = null;

        try {
            radioPrograms = new RetrieveRadioProgramDelegate(this).execute("all").get();
            presenters = new RetrievePPDelegate(this).execute("presenter").get();
            producers = new RetrievePPDelegate(this).execute("producer").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void selectEditScheduleProgram(ScheduleProgram scheduleProgram) {
        sp2edit = scheduleProgram;
        Log.v(TAG, "Editing program slot: " + scheduleProgram.getName() + "..." );

        try {
            radioPrograms = new RetrieveRadioProgramDelegate(this).execute("all").get();
            presenters = new RetrievePPDelegate(this).execute("presenter").get();
            producers = new RetrievePPDelegate(this).execute("producer").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void selectWeeklyScheduleSlot(WeeklySchedule weeklySchedule){
        ws2view = weeklySchedule;
        Log.v(TAG, "Viewing Weekly slots: " + weeklySchedule.getStartDate() + "..." );

        Intent intent = new Intent(MainController.getApp(), ScheduleListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayScheduleProgram(MaintainScheduleScreen maintainScheduleScreen) {
        this.maintainScheduleScreen = maintainScheduleScreen;
        if (sp2edit == null) {
            maintainScheduleScreen.createScheduleProgram();
        }else {
            maintainScheduleScreen.editScheduleProgram(sp2edit);
        }
    }

    public void onDisplayCopyScheduleProgram(CopyScheduleScreen copyScheduleScreen) {
        this.copyScheduleScreen = copyScheduleScreen;
        copyScheduleScreen.copyScheduleProgram(sp2edit);
    }

    public void selectCreateScheduleProgram(ScheduleProgram sp) {
        new CreateScheduleProgramDelegate(this).execute(sp);
    }

    public void selectCopyScheduleProgram(ScheduleProgram scheduleProgram) {
        sp2edit = scheduleProgram;
        Log.v(TAG, "Copying program slot: " + scheduleProgram.getName() + "..." );

        Intent intent = new Intent(MainController.getApp(), CopyScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void scheduleProgramCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void selectUpdateScheduleProgram(ScheduleProgram sp) {
        new UpdateScheduleProgramDelegate(this).execute(sp);
    }

    public void scheduleProgramUpdated (boolean success){
        startUseCase();
    }

    public void selectDeleteScheduleProgram(ScheduleProgram sp) {
        new DeleteScheduleProgramDelegate(this).execute(sp);
    }

    public void scheduleProgramDeleted(boolean success) {
        startUseCase();
    }

    public void selectCancelCreateEditScheduleProgram(ScheduleProgram scheduleProgram) {
        // Go back to ProgramList screen with refreshed programs.
        sp2edit = scheduleProgram;
        startUseCase();
    }
}
