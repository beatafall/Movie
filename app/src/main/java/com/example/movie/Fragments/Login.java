package com.example.movie.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie.Classes.User;
import com.example.movie.Database.DatabaseHelper;
import com.example.movie.R;
import java.util.List;

public class Login extends Fragment {
    DatabaseHelper myDB;
    Button btn_login;
    TextView tv_register;
    EditText et_name, et_password;
    List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_login, container, false);
        myDB= new DatabaseHelper(getContext());
        btn_login=v.findViewById(R.id.btnlogin);
        tv_register=v.findViewById(R.id.register);
        et_name=v.findViewById(R.id.username);
        et_password=v.findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String pass = et_password.getText().toString();
                if (name.equals("") || pass.equals("")) {
                    Toast.makeText(getActivity(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    String username = et_name.getText().toString();
                    String userpass = et_password.getText().toString();
                    userList=myDB.getAllUsers();
                    boolean b = false;
                    int id = 0;

                    for (int i=0; i<userList.size(); ++i) {
                        if(userList.get(i).getName().equals(username)){
                            b = true;
                            id = userList.get(i).getId();
                        }
                    }

                    if (b == true) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        Home home = new Home();
                        FragmentTransaction pr = getFragmentManager().beginTransaction();
                        pr.replace(R.id.fragment_container, home);
                        pr.remove(new Login());
                        home.setArguments(bundle);
                        pr.addToBackStack(null);
                        pr.commit();
                    } else {
                        Toast.makeText(getContext(), "This user doesn't exist!", Toast.LENGTH_SHORT).show();
                    }
                }
           }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register register=new Register();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, register);
                fr.commit();
            }
        });

        return v;
    }
}
