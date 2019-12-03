package com.example.afterschoolapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.afterschoolapplication.ClassObjects.Student;
import com.example.afterschoolapplication.ClassObjects.Teacher;
import com.example.afterschoolapplication.DB.AppDatabase;
import com.example.afterschoolapplication.DB.TeacherDAO;

import java.util.List;

public class NewAccount extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mRegisterBtn;
    private String mSecretPassword = "admin";

    // Room teacher DAO
    TeacherDAO teacherDAO;

    // List of all teachers
    List<Teacher> mTeachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        mUsername = (EditText) findViewById(R.id.usernameEntry);
        mPassword = (EditText) findViewById(R.id.passwordEntry);
        mRegisterBtn = (Button) findViewById(R.id.registerButton);

        teacherDAO = Room.databaseBuilder(this,AppDatabase.class, AppDatabase.dbTeacherName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getTeacherDAO();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTeacher();
            }
        });

        mRegisterBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                teacherDAO.nukeTeachers();
                makeToast();
                return false;
            }
        });

    }

    public void makeToast() {
        Toast.makeText(this,"Teacher list has been cleared!", Toast.LENGTH_SHORT).show();
    }

    public void submitTeacher() {
        List<Teacher> teacherList = teacherDAO.getTeachers();
        String teacherName = mUsername.getText().toString();
        if(teacherName.matches("")){
            Toast.makeText(this, "You need to enter a Username", Toast.LENGTH_SHORT).show();
            return;
        }
        // checks if a student with the same name has already been added to the list
        for (Teacher teacher: teacherList){
            if(teacher.getName().equals(teacherName)){
                Toast.makeText(this, teacherName + " is already in use!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        String password = mPassword.getText().toString();
        if(password.matches("")){
            Toast.makeText(this, "You need to enter a Password", Toast.LENGTH_SHORT).show();
            return;
        } else if(password.matches(mSecretPassword)){
            teacherDAO.insert(new Teacher(teacherName, password, true));
            Toast.makeText(this, teacherName + " has been registered as an Admin!", Toast.LENGTH_SHORT).show();
        } else {
            teacherDAO.insert(new Teacher(teacherName, password, false));
            Toast.makeText(this, teacherName + " has been registered!", Toast.LENGTH_SHORT).show();
        }
    }

    // method to go back to the main activity
    public void finish(View v){
        finish();
    }
}
