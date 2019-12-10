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
import android.widget.Toast;

import com.example.movie.Database.DatabaseHelper;
import com.example.movie.R;

public class Login extends Fragment {
    DatabaseHelper myDB;
    Button btn_login, btn_register;
    EditText et_name, et_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_login, container, false);
        myDB= new DatabaseHelper(getActivity());
        btn_login=v.findViewById(R.id.btnlogin);
        btn_register=v.findViewById(R.id.btnregister);
        et_name=v.findViewById(R.id.username);
        et_password=v.findViewById(R.id.password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                String pass=et_password.getText().toString();
                Boolean check=myDB.checkUser(name,pass);
                if(name.equals("") || pass.equals("")){
                    Toast.makeText(getActivity(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(check==true){ //Boti 12345, Kati 12345, Beata 12345 letezik
                    MainScreen mainScreen=new MainScreen();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, mainScreen);
                    fr.commit();
                }
                else if(check==false){
                    Toast.makeText(getActivity(),"This user doesn't exist!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
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
