package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ProgramListScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;

/**
 * Created by nivi on 9/6/2017.
 */

public class ScheduleListScreen extends AppCompatActivity{
    // Tag for logging
    private static final String TAG = ScheduleListScreen.class.getName();

    private ListView mListView;
    private ScheduleProgramAdapter mSPAdapter;
    private ScheduleProgram selectedSP = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        ArrayList<ScheduleProgram> schedulePrograms = new ArrayList<ScheduleProgram>();
        mSPAdapter = new ScheduleProgramAdapter(this, schedulePrograms);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getScheduleProgramController().selectCreateScheduleProgram();
            }
        });

        mListView = (ListView) findViewById(R.id.schedule_pm_list);
        mListView.setAdapter(mSPAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                ScheduleProgram sp = (ScheduleProgram) adapterView.getItemAtPosition(position);
                selectedSP = sp;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getScheduleProgramController().onDisplayProgramList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view:
                if (selectedSP == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a program slot first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected program slot.");
                }
                else {
                    Log.v(TAG, "Viewing program slot: " + selectedSP.getName() + "...");
                    ControlFactory.getScheduleProgramController().selectEditScheduleProgram(selectedSP);
                }
        }

        return true;
    }

    public void showSchedulePrograms(List<ScheduleProgram> schedulePrograms) {
        mSPAdapter.clear();
        for (int i = 0; i < schedulePrograms.size(); i++) {
            mSPAdapter.add(schedulePrograms.get(i));
        }
    }
}
