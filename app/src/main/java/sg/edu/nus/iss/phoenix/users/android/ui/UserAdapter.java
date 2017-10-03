package sg.edu.nus.iss.phoenix.users.android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.users.entity.User;

/**
 * Created by cherry on 27/09/2017.
 */

public class UserAdapter extends ArrayAdapter<User>{

    public UserAdapter(Context context,  ArrayList<User> users) {
        super(context, 0, users);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_create_user,parent,false);
        }
        User currentUser = getItem(position);

        EditText userId = (EditText)listItemView.findViewById(R.id.et_create_idno);
        userId.setText(currentUser.getIdNo(),TextView.BufferType.NORMAL);
        userId.setKeyListener(null);

        EditText userName = (EditText)listItemView.findViewById(R.id.et_create_username);
        userName.setText(currentUser.getName(),TextView.BufferType.NORMAL);
        userName.setKeyListener(null);

        EditText userDepa = (EditText)listItemView.findViewById(R.id.et_create_roles);
        userDepa.setText(currentUser.getRoles(),TextView.BufferType.NORMAL);
        userDepa.setKeyListener(null);

        EditText userPassword = (EditText)listItemView.findViewById(R.id.et_create_password);
        userPassword.setText(currentUser.getPassword(),TextView.BufferType.NORMAL);
        userPassword.setKeyListener(null);

        return listItemView;

    }

}
