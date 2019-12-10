package com.example.movie.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movie.Database.DatabaseHelper;
import com.example.movie.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class Register extends Fragment {

    Button btn_register;
    EditText et_uName, et_uPass;
    DatabaseHelper db;
    Cursor cursor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_register, container, false);

        btn_register= v.findViewById(R.id.RegistrationButton);;
        et_uName = v.findViewById(R.id.Name);
        et_uPass = v.findViewById(R.id.UserPassword);
        db= new DatabaseHelper(getActivity());

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name= et_uName.getText().toString();
                String pass= et_uPass.getText().toString();
                Boolean check=db.checkUser(name,pass);
                if(name.equals("") || pass.equals("")){
                    Toast.makeText(getActivity(),"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(check==false){
                    db.insertUser(name,pass);
                    Toast.makeText(getActivity(),"You have registered successfully, now Log in!",Toast.LENGTH_SHORT).show();
                    Login login=new Login();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, login);
                    fr.commit();
                }
                else if(check==true){
                    Toast.makeText(getActivity(),"This user already exist!",Toast.LENGTH_SHORT).show();
                }

           }
        });

        return v;
    }


}
