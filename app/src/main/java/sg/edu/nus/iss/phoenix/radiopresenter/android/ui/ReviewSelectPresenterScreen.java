package sg.edu.nus.iss.phoenix.radiopresenter.android.ui;

/**
 * Created by Raghu on 27/9/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radiopresenter.android.ui.RadioPresenterAdapter;
import sg.edu.nus.iss.phoenix.radiopresenter.entity.RadioPresenter;

import static android.widget.AdapterView.*;

public class ReviewSelectPresenterScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = sg.edu.nus.iss.phoenix.radiopresenter.android.ui.ReviewSelectPresenterScreen.class.getName();

    private RadioPresenterAdapter mRPAdapter;
    // private ArrayAdapter<String> adapter = null;
    private ListView mListView;
    private RadioPresenter selectedRP = null;
    private Spinner spinner;
    private ArrayList<RadioPresenter> radioPresenters;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_select_presenter);



        // ArrayList<String> radioProgramNames = new ArrayList<String>();
        // mRPAdapter = new ArrayAdapter<String>(this, R.layout.activity_review_select_presenter,
        //        R.id.maintain_presenter_name_text_view, radioProgramNames);

        //Object[] mStringArray = radioPresenters.toArray();
       // Log.v(mRPAdapter);


      //  mRPAdapter = new RadioPresenterAdapter(this, radioPresenters);

        mListView = (ListView) findViewById(R.id.review_select_presenter_list);
        mListView.setAdapter(mRPAdapter);
        spinner = (Spinner)findViewById(R.id.spinner);

       spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Log.v("presenter",radioPresenters.get(position).getRadioPresenterName());
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });



            // Setup the item selection listener
        mListView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Log.v(TAG, "Radio presenter at position " + position + " selected.");
                RadioPresenter rp = (RadioPresenter) adapterView.getItemAtPosition(position);
                // Log.v(TAG, "Radio presenter name is " + rp.getRadioProgramName());
                selectedRP = rp;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getReviewSelectPresenterController().onDisplay(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_review_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_select:
                if (selectedRP == null) {
                    // Prompt for the selection of a radio presenter.
                    Toast.makeText(this, "Select a radio presenter first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected radio presenter.");
                }
                else {

                    Log.v(TAG, "Selected radio presenter: " + selectedRP.getRadioPresenterName() + "...");
                    Toast.makeText(this,"selected RadioPresenter" +selectedRP.getRadioPresenterName(),Toast.LENGTH_LONG).show();
                    ControlFactory.getReviewSelectPresenterController().selectPresenter(selectedRP);
                }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getReviewSelectProgramController().selectCancel();
    }

    public void showPresenters(List<RadioPresenter> radioPresenters) {
        this.radioPresenters = (ArrayList<RadioPresenter>) radioPresenters;
       /* mRPAdapter.clear();
        for (int i = 0; i < radioPresenters.size(); i++) {
            mRPAdapter.add(radioPresenters.get(i));
        }*/
       ArrayList<String> presenterNames = new ArrayList<>();
        for(int i=0;i<radioPresenters.size();i++)
        {
            presenterNames.add(radioPresenters.get(i).getRadioPresenterName());

        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,presenterNames);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }
}

