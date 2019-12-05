package com.example.movie.Fragments;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.example.movie.Database.DatabaseHelper;
import com.example.movie.R;

public class Login extends Fragment {
    DatabaseHelper myDB;
    Button btn_login;
    EditText et_name, et_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_login, container, false);
        myDB= new DatabaseHelper(getActivity());
        btn_login=v.findViewById(R.id.btnlogin);
        et_name=v.findViewById(R.id.username);
        et_password=v.findViewById(R.id.password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  myDB.insertUser(et_name.getText().toString(),et_password.getText().toString());
//                  Log.i("login",et_name.getText().toString());
//                  Log.i("login",et_password.getText().toString());
                MainScreen mainScreen=new MainScreen();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, mainScreen);
                fr.commit();
            }
        });
        return v;
    }
}
