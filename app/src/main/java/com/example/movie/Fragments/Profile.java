package com.example.movie.Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movie.Classes.User;
import com.example.movie.Database.DatabaseHelper;
import com.example.movie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.net.Uri.parse;
import static com.example.movie.Database.DatabaseHelper.IMAGE;

public class Profile extends Fragment {

    TextView changepassword;
    TextView userName;
    Button btn_saveImage,btn_chooseImage, btn_savePassword;
    ImageView image;
    DatabaseHelper db;
    User user;
    List<User> userimg;

    private static final int RESULT_LOAD_IMAGE=1;
    String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        changepassword = v.findViewById(R.id.changepass);
        userName = v.findViewById(R.id.name);
        btn_saveImage = v.findViewById(R.id.save);
        image = v.findViewById(R.id.userImage);
        btn_chooseImage = v.findViewById(R.id.choose);
        btn_savePassword = v.findViewById(R.id.btnchangepass);

        db =  new DatabaseHelper(getActivity());

        Bundle bundle = this.getArguments();
        final int id= bundle.getInt("id");
        user = db.getUser(id);
        userName.setText(user.getName());
        changepassword.setText(user.getPassword());

        btn_savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View viewDialog = LayoutInflater.from(getContext()).inflate(R.layout.setpassword, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(viewDialog);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et_pw = viewDialog.findViewById(R.id.newpass);
                        user.setPassword(et_pw.getText().toString());
                        db.updatePassword(user);
                        changepassword.setText(et_pw.getText().toString());
                    }
                });

                AlertDialog d = builder.create();
                d.show();

            }
        });

        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,RESULT_LOAD_IMAGE);
            }
        });

        btn_saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertImage(user);
                Log.d("img",user.getImage());
            }
        });


        BottomNavigationView bottomNavigationView = v.findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.home:
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",id);
                        Home home=new Home();
                        home.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container,home);
                        ft.commit();
                        break;
                    case R.id.nowplay:
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("id", id);
                        NowPlaying now = new NowPlaying();
                        now.setArguments(bundle2);
                        FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                        ft3.replace(R.id.fragment_container, now);
                        ft3.commit();
                        break;
                    case R.id.favorite:
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("id",id);
                        Favorites favorites = new Favorites();
                        favorites.setArguments(bundle3);
                        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                        ft2.replace(R.id.fragment_container,favorites);
                        ft2.commit();
                        break;
                    case R.id.profile:
                        break;
                }
                return true;
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!=null)
        {
            Uri selectedImage = data.getData();
            path = selectedImage.toString();
            image.setImageURI(selectedImage);
            user.setImage(path);
        }
    }

}
