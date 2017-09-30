package sg.edu.nus.iss.phoenix.radiopresenter.android.ui;
/**
 * Created by Raghu on 27/9/2017.
 */

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
import sg.edu.nus.iss.phoenix.radiopresenter.entity.RadioPresenter;

public class RadioPresenterAdapter extends ArrayAdapter<RadioPresenter> {

    public RadioPresenterAdapter(@NonNull Context context,  ArrayList<RadioPresenter> radioPresenters) {
        super(context, 0, radioPresenters);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_radio_presenter, parent, false);
        }
        //    Word currentWord = getItem(position);
        RadioPresenter currentRP = getItem(position);

        EditText radioPMName = (EditText)listItemView.findViewById(R.id.maintain_presenter_name_text_view);
        radioPMName.setText(currentRP.getRadioPresenterName(), TextView.BufferType.NORMAL);
        radioPMName.setTextIsSelectable(false);
        radioPMName.setKeyListener(null); // This disables editing.

        /*EditText radioPMDesc = (EditText)listItemView.findViewById(R.id.maintain_program_desc_text_view);
        radioPMDesc.setText(currentRP.getRadioPresenterDescription(), TextView.BufferType.NORMAL);
        radioPMDesc.setKeyListener(null);

        EditText radioPMDuration = (EditText)listItemView.findViewById(R.id.maintain_program_duration_text_view);
        radioPMDuration.setText(currentRP.getRadioPresentermailid(), TextView.BufferType.NORMAL);
        radioPMDuration.setKeyListener(null);
*/
        return listItemView;
    }
}
