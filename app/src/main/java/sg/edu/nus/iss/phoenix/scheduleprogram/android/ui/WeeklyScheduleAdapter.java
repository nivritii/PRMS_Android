package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.WeeklySchedule;

/**
 * Created by nivi on 9/27/2017.
 */

public class WeeklyScheduleAdapter extends ArrayAdapter<WeeklySchedule> {

    public WeeklyScheduleAdapter(@NonNull Context context, ArrayList<WeeklySchedule> weeklySchedules) {
        super(context, 0, weeklySchedules);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_weekly_schedule, parent, false);
        }
        //    Word currentWord = getItem(position);
        WeeklySchedule currentWS = getItem(position);

        EditText wsStartDate = (EditText)listItemView.findViewById(R.id.maintain_ws_startDate_text_view);
        wsStartDate.setText(currentWS.getStartDate(), TextView.BufferType.NORMAL);
        wsStartDate.setKeyListener(null); // This disables editing.

        return listItemView;
    }
}
