package com.example.movie.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.movie.Classes.User;
import com.example.movie.Database.DatabaseHelper;
import com.example.movie.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Register extends Fragment {

    Button btn_register, btn_chooseImage;
    EditText et_uName, et_uPass;
    ImageView imageView;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_register, container, false);

        btn_register= v.findViewById(R.id.RegistrationButton);
        btn_chooseImage = v.findViewById(R.id.choose);
        et_uName = v.findViewById(R.id.Name);
        et_uPass = v.findViewById(R.id.UserPassword);
        imageView =  v.findViewById(R.id.userImage);
        db= new DatabaseHelper(getActivity());

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_uName.getText().toString();
                String pass = et_uPass.getText().toString();
                Boolean checkUser=db.checkUser(name,pass);

                if (name.equals("") || pass.equals("")) {
                    Toast.makeText(getActivity(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (checkUser==false) {
                    db.addUser(new User(name, pass));

                    Toast.makeText(getActivity(), "You have registered successfully, now Log in!", Toast.LENGTH_SHORT).show();
                    Login login = new Login();
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.fragment_container, login);
                    fr.commit();
                } else  {
                            Toast.makeText(getActivity(), "This user already exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

        });

        return v;
    }

}
