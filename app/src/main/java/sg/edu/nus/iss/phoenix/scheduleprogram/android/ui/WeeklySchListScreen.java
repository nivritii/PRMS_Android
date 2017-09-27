package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.WeeklySchedule;

/**
 * Created by nivi on 9/27/2017.
 */

public class WeeklySchListScreen extends AppCompatActivity {

    // Tag for logging
    private static final String TAG = ScheduleListScreen.class.getName();

    private ListView mListView;
    private WeeklyScheduleAdapter mWSAdapter;
    private WeeklySchedule selectedWS = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule_list);

        ArrayList<WeeklySchedule> weeklySchedules = new ArrayList<WeeklySchedule>();
        mWSAdapter = new WeeklyScheduleAdapter(this, weeklySchedules);

        mListView = (ListView) findViewById(R.id.weekly_sch_list);
        mListView.setAdapter(mWSAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                WeeklySchedule ws = (WeeklySchedule) adapterView.getItemAtPosition(position);
                selectedWS = ws;
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

        ControlFactory.getScheduleProgramController().onDisplayWeeklySchList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_review_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_select:
                if (selectedWS == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a weekly slot first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no weekly slot.");
                }
                else {
                    Log.v(TAG, "Viewing weekly slot: " + selectedWS.getStartDate()  + "...");
                    ControlFactory.getScheduleProgramController().selectWeeklyScheduleSlot(selectedWS);
                }
                return true;

        }
        return true;
    }

    public void showWeeklySchedules(List<WeeklySchedule> weeklySchedules) {
        mWSAdapter.clear();
        for (int i = 0; i < weeklySchedules.size(); i++) {
            mWSAdapter.add(weeklySchedules.get(i));
        }
    }
}
