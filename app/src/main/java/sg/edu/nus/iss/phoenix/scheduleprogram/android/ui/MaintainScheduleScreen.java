package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.MaintainProgramScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;

/**
 * Created by nivi on 9/6/2017.
 */

public class MaintainScheduleScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = MaintainScheduleScreen.class.getName();

    private EditText mSPNameEditText;
    private EditText mSPDOPEditText;
    private EditText mSPStartTimeEditText,mSPEndTimeEditText,mSPPresenterEditText,mSPProducerEditText;
    private EditText mSPDurationEditText;
    private ScheduleProgram sp2edit = null;
    KeyListener mSPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule_program);

        // Find all relevant views that we will need to read user input from
        mSPNameEditText = (EditText) findViewById(R.id.maintain_schedule_name_text_view);
        mSPDOPEditText = (EditText) findViewById(R.id.maintain_schedule_date_text_view);
        mSPStartTimeEditText = (EditText) findViewById(R.id.maintain_schedule_sTime_text_view);
        mSPEndTimeEditText = (EditText) findViewById(R.id.maintain_schedule_eTime_text_view);
        mSPDurationEditText = (EditText) findViewById(R.id.maintain_schedule_duration_text_view);
        mSPPresenterEditText = (EditText) findViewById(R.id.maintain_schedule_presenter_text_view);
        mSPProducerEditText = (EditText) findViewById(R.id.maintain_schedule_producer_text_view);

        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mSPNameEditTextKeyListener = mSPNameEditText.getKeyListener();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getScheduleProgramController().onDisplayScheduleProgram(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new program slot, hide the "Delete" menu item.
        if (sp2edit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save radio program.
                if (sp2edit == null) { // Newly created.
                    Log.v(TAG, "Saving program slot " + mSPNameEditText.getText().toString() + "...");
                    ScheduleProgram sp = new ScheduleProgram(mSPNameEditText.getText().toString(),
                            mSPDOPEditText.getText().toString(), mSPStartTimeEditText.getText().toString(),
                            mSPDurationEditText.getText().toString(),mSPEndTimeEditText.getText().toString(),
                            mSPPresenterEditText.getText().toString(),mSPProducerEditText.getText().toString());
                    ControlFactory.getScheduleProgramController().selectCreateScheduleProgram(sp);
                }
                else { // Edited.
                    Log.v(TAG, "Saving program slot " + sp2edit.getName() + "...");
                    sp2edit.setDateOfProgram(mSPDOPEditText.getText().toString());
                    sp2edit.setStartTime(mSPStartTimeEditText.getText().toString());
                    sp2edit.setDuration(mSPDurationEditText.getText().toString());
                    sp2edit.setEndTime(mSPEndTimeEditText.getText().toString());
                    sp2edit.setPresenter(mSPPresenterEditText.getText().toString());
                    sp2edit.setProducer(mSPProducerEditText.getText().toString());
                    ControlFactory.getScheduleProgramController().selectUpdateScheduleProgram(sp2edit);
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                Log.v(TAG, "Deleting program slot" + sp2edit.getName() + "...");
                ControlFactory.getScheduleProgramController().selectDeleteScheduleProgram(sp2edit);
                return true;
            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                Log.v(TAG, "Canceling creating/editing program slot...");
                ControlFactory.getScheduleProgramController().selectCancelCreateEditScheduleProgram();
                return true;

        }

        return true;
    }

    public void createScheduleProgram() {
        this.sp2edit = null;
        mSPNameEditText.setText("", TextView.BufferType.EDITABLE);
        mSPDOPEditText.setText("", TextView.BufferType.EDITABLE);
        mSPStartTimeEditText.setText("", TextView.BufferType.EDITABLE);
        mSPEndTimeEditText.setText("", TextView.BufferType.EDITABLE);
        mSPDurationEditText.setText("", TextView.BufferType.EDITABLE);
        mSPPresenterEditText.setText("", TextView.BufferType.EDITABLE);
        mSPProducerEditText.setText("", TextView.BufferType.EDITABLE);
        mSPNameEditText.setKeyListener(mSPNameEditTextKeyListener);
    }

    public void editScheduleProgram(ScheduleProgram sp2edit) {
        this.sp2edit = sp2edit;
        if (sp2edit != null) {
            mSPNameEditText.setText(sp2edit.getName(), TextView.BufferType.NORMAL);
            mSPDOPEditText.setText(sp2edit.getDateOfProgram(), TextView.BufferType.EDITABLE);
            mSPStartTimeEditText.setText(sp2edit.getStartTime(), TextView.BufferType.EDITABLE);
            mSPEndTimeEditText.setText(sp2edit.getEndTime(), TextView.BufferType.EDITABLE);
            mSPDurationEditText.setText(sp2edit.getDuration(),TextView.BufferType.EDITABLE);
            mSPPresenterEditText.setText(sp2edit.getPresenter(),TextView.BufferType.EDITABLE);
            mSPProducerEditText.setText(sp2edit.getProducer(),TextView.BufferType.EDITABLE);
            mSPNameEditText.setKeyListener(null);
        }
    }
}
