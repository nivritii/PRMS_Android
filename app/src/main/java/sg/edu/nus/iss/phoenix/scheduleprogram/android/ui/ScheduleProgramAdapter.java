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
import sg.edu.nus.iss.phoenix.scheduleprogram.entity.ScheduleProgram;

/**
 * Created by nivi on 9/6/2017.
 */

public class ScheduleProgramAdapter extends ArrayAdapter<ScheduleProgram> {

    public ScheduleProgramAdapter(@NonNull Context context, ArrayList<ScheduleProgram>schedulePrograms) {
        super(context, 0, schedulePrograms);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_schedule_program, parent, false);
        }
        //    Word currentWord = getItem(position);
       ScheduleProgram currentSP = getItem(position);

        EditText schedulePMName = (EditText)listItemView.findViewById(R.id.maintain_schedule_name_text_view);
        schedulePMName.setText(currentSP.getName(), TextView.BufferType.NORMAL);
        schedulePMName.setKeyListener(null); // This disables editing.

        EditText schedulePMDate = (EditText)listItemView.findViewById(R.id.maintain_schedule_date_text_view);
        schedulePMDate.setText(currentSP.getDateOfProgram(), TextView.BufferType.NORMAL);
        schedulePMDate.setKeyListener(null);

        EditText schedulePMStartTime= (EditText)listItemView.findViewById(R.id.maintain_schedule_sTime_text_view);
        schedulePMStartTime.setText(currentSP.getStartTime(), TextView.BufferType.NORMAL);
        schedulePMStartTime.setKeyListener(null);

        EditText schedulePMEndTime= (EditText)listItemView.findViewById(R.id.maintain_schedule_eTime_text_view);
        schedulePMEndTime.setText(currentSP.getEndTime(), TextView.BufferType.NORMAL);
        schedulePMEndTime.setKeyListener(null);

        EditText schedulePMDuration = (EditText)listItemView.findViewById(R.id.maintain_schedule_duration_text_view);
        schedulePMDuration.setText(currentSP.getDuration(), TextView.BufferType.NORMAL);
        schedulePMDuration.setKeyListener(null);

        EditText schedulePMPresenter = (EditText)listItemView.findViewById(R.id.maintain_schedule_presenter_text_view);
        schedulePMPresenter.setText(currentSP.getPresenter(), TextView.BufferType.NORMAL);
        schedulePMPresenter.setKeyListener(null);

        EditText schedulePMProducer = (EditText)listItemView.findViewById(R.id.maintain_schedule_producer_text_view);
        schedulePMProducer.setText(currentSP.getProducer(), TextView.BufferType.NORMAL);
        schedulePMProducer.setKeyListener(null);

        return listItemView;
    }
}
