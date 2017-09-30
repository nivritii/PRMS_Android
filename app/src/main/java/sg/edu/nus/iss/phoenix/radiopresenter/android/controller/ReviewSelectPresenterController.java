package sg.edu.nus.iss.phoenix.radiopresenter.android.controller;

/**
 * Created by Raghu on 27/9/2017.
 */

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radiopresenter.android.delegate.RetrievePresentersDelegate;
import sg.edu.nus.iss.phoenix.radiopresenter.android.ui.ReviewSelectPresenterScreen;
import sg.edu.nus.iss.phoenix.radiopresenter.entity.RadioPresenter;


import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.ResourceBundle;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
/*import sg.edu.nus.iss.phoenix.radiopresenter.android.delegate.CreatePresenterDelegate;
import sg.edu.nus.iss.phoenix.radiopresenter.android.delegate.DeletePresenterDelegate;
import sg.edu.nus.iss.phoenix.radiopresenter.android.delegate.RetrievePresentersDelegate;
import sg.edu.nus.iss.phoenix.radiopresenter.android.delegate.UpdatePresenterDelegate;
import sg.edu.nus.iss.phoenix.radiopresenter.android.ui.MaintainPresenterScreen;
import sg.edu.nus.iss.phoenix.radiopresenter.android.ui.PresenterListScreen;*/
import sg.edu.nus.iss.phoenix.radiopresenter.android.ui.ReviewSelectPresenterScreen;
import sg.edu.nus.iss.phoenix.radiopresenter.entity.RadioPresenter;

public class ReviewSelectPresenterController {
    // Tag for logging.
    private static final String TAG = sg.edu.nus.iss.phoenix.radiopresenter.android.controller.ReviewSelectPresenterController.class.getName();

    private ReviewSelectPresenterScreen reviewSelectPresenterScreen;
    private RadioPresenter rpSelected = null;

    public void startUseCase() {
        rpSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectPresenterScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectPresenterScreen reviewSelectPresenterScreen) {
        this.reviewSelectPresenterScreen = reviewSelectPresenterScreen;
        new RetrievePresentersDelegate(this).execute("all");
    }

    public void presentersRetrieved(List<RadioPresenter> radioPresenters) {
        reviewSelectPresenterScreen.showPresenters(radioPresenters);
    }

    public void selectPresenter(RadioPresenter radioPresenter) {
        rpSelected = radioPresenter;
        Log.v(TAG, "Selected radio program: " + radioPresenter.getRadioPresenterName() + ".");
        // To call the base use case controller with the selected radio program.
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedPresenter(rpSelected);

    }

    public void selectCancel() {
        rpSelected = null;
        Log.v(TAG, "Cancelled the seleciton of radio presenter.");
        // To call the base use case controller without selection;
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedPresenter(rpSelected);
    }


}







