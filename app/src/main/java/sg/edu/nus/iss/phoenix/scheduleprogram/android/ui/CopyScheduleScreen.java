package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.controller.ScheduleProgramController;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;

/**
 * Created by nivi on 9/25/2017.
 */

public class CopyScheduleScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = CopyScheduleScreen.class.getName();

    private EditText mSPNameEditText;
    private EditText mSPDOPEditText;
    private EditText mSPStartTimeEditText,mSPEndTimeEditText,mSPPresenterEditText,mSPProducerEditText;
    private EditText mSPDurationEditText;
    private ScheduleProgram sp2edit = null;
    KeyListener mSPNameEditTextKeyListener = null;

    Spinner spinnerRp;
    Spinner spinnerPresenter;
    Spinner spinnerProducer;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule_program);

        spinnerRp = (Spinner) findViewById(R.id.spinnerRP);
        spinnerPresenter = (Spinner) findViewById(R.id.spinnerPresenter);
        spinnerProducer = (Spinner) findViewById(R.id.spinnerProducer);

        // Find all relevant views that we will need to read user input from
        mSPNameEditText = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        mSPDOPEditText = (EditText) findViewById(R.id.maintain_schedule_date_text_view);
        mSPStartTimeEditText = (EditText) findViewById(R.id.maintain_schedule_sTime_text_view);
        mSPEndTimeEditText = (EditText) findViewById(R.id.maintain_schedule_eTime_text_view);
        mSPDurationEditText = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        //mSPPresenterEditText = (EditText) findViewById(R.id.maintain_schedule_presenter_text_view);
        //mSPProducerEditText = (EditText) findViewById(R.id.maintain_schedule_producer_text_view);

        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
//        mSPNameEditTextKeyListener = mSPNameEditText.getKeyListener();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getScheduleProgramController().onDisplayCopyScheduleProgram(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor_copy_sch, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new program slot, hide the "Delete" menu item.
        /*if (sp2edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save_copy:
                // Save radio program.
                Log.v(TAG, "Saving program slot " + spinnerRp.getSelectedItem().toString() + "...");
                ScheduleProgram sp = new ScheduleProgram(spinnerRp.getSelectedItem().toString(),
                            mSPDOPEditText.getText().toString(), mSPStartTimeEditText.getText().toString(),
                            mSPDurationEditText.getText().toString(),mSPEndTimeEditText.getText().toString(),
                            spinnerPresenter.getSelectedItem().toString(),spinnerProducer.getSelectedItem().toString());
                ControlFactory.getScheduleProgramController().selectCreateScheduleProgram(sp);
                return true;

            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel_copy:
                Log.v(TAG, "Canceling creating/editing program slot...");
                ControlFactory.getScheduleProgramController().selectCancelCreateEditScheduleProgram(sp2edit);
                return true;

        }

        return true;
    }

    private void addItemsOnSpinner(List<String> list, Spinner spinner) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private List<String> sortList(List<String> stringList, String first){
        stringList.set(0, first);
        return stringList;
    }


    public void copyScheduleProgram(ScheduleProgram sp2edit) {
        this.sp2edit = sp2edit;
        if (sp2edit != null) {

            addItemsOnSpinner(sortList(ScheduleProgramController.radioPrograms, sp2edit.getName()), spinnerRp);
            addItemsOnSpinner(sortList(ScheduleProgramController.presenters, sp2edit.getPresenter()), spinnerPresenter);
            addItemsOnSpinner(sortList(ScheduleProgramController.producers, sp2edit.getProducer()), spinnerProducer);

            //mSPNameEditText.setText(sp2edit.getName(), TextView.BufferType.NORMAL);
            mSPDOPEditText.setText(sp2edit.getDateOfProgram(), TextView.BufferType.EDITABLE);
            mSPStartTimeEditText.setText(sp2edit.getStartTime(), TextView.BufferType.EDITABLE);
            mSPEndTimeEditText.setText(sp2edit.getEndTime(), TextView.BufferType.EDITABLE);
            mSPDurationEditText.setText(sp2edit.getDuration(),TextView.BufferType.EDITABLE);
            //mSPPresenterEditText.setText(sp2edit.getPresenter(),TextView.BufferType.EDITABLE);
            //mSPProducerEditText.setText(sp2edit.getProducer(),TextView.BufferType.EDITABLE);

            //mSPNameEditText.setKeyListener(null);
        }
    }
}
