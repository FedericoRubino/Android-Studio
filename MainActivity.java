package com.example.afterschoolapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

//import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import android.os.Bundle;

import com.example.afterschoolapplication.ClassObjects.Teacher;
import com.example.afterschoolapplication.DB.AppDatabase;
import com.example.afterschoolapplication.DB.TeacherDAO;

public class MainActivity extends AppCompatActivity {

    //Member variables
    private Button newAccountBtn;
    private Button loginBtn;
    private Button studentRegBtn;
    private Button adminBtn;
    private Button mManageStudentsBtn;
    String currentDateAndTime = DateFormat.getDateTimeInstance().format(new Date());
    private TextView dateAndTime;
    private TextView mDisplayLogin;

    TeacherDAO teacherDAO;
    List<Teacher> mTeachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        newAccountBtn = (Button) findViewById(R.id.createAccount);
        loginBtn = (Button) findViewById(R.id.login);
        studentRegBtn = (Button) findViewById(R.id.studentReg);
        adminBtn = (Button) findViewById(R.id.admin);
        mManageStudentsBtn = (Button) findViewById(R.id.manageStudentsBtn);

        teacherDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbTeacherName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getTeacherDAO();

        // Text views
        dateAndTime = (TextView) findViewById(R.id.time);
        dateAndTime.setText(currentDateAndTime);
        mDisplayLogin = (TextView) findViewById(R.id.loginDisplay);


        newAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchNewAccountActivity();
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshMainDisplay();
                launchLoginActivity();
                refreshMainDisplay();
            }
        });


        studentRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLoggedIn()) {
                    launchStudentRegActivity();
                }
            }
        });

        mManageStudentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLoggedIn()) {
                    launchManageStudentActivity();
                }
            }
        });


        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLoggedInAdmin()) {
                    launchAdminActivity();
                }
            }
        });

        refreshMainDisplay();
    }

    // returns true if a teacher is logged in
    public Boolean checkLoggedIn(){
        mTeachers = teacherDAO.getTeachers();
        if(!mTeachers.isEmpty()){
            for(Teacher teacher: mTeachers){
                if(teacher.getLoggedIn()){
                    return true;
                }
            }
        }
        return false;
    }

    // returns true if the teacher logged in is an admin
    public Boolean checkLoggedInAdmin(){
        mTeachers = teacherDAO.getTeachers();
        if(!mTeachers.isEmpty()){
            for(Teacher teacher: mTeachers){
                if(teacher.getLoggedIn() && teacher.getAdmin()){
                    return true;
                }
            }
        }
        return false;
    }


    public void refreshMainDisplay(){
        mTeachers = teacherDAO.getTeachers();
        String loggedInTeacher = "";
        if(!mTeachers.isEmpty()){
            for(Teacher teacher: mTeachers){
                if(teacher.getLoggedIn()){
                    loggedInTeacher = teacher.getName();
                }
            }
        }
        if(!loggedInTeacher.equals("")) {
            mDisplayLogin.setText(loggedInTeacher);
        }
    }

    private void launchNewAccountActivity() {

        Intent intentNewAct = new Intent(this, NewAccount.class);
        startActivity(intentNewAct);
    }

    private void launchLoginActivity() {

        Intent intentLogin = new Intent(this, Login.class);
        startActivity(intentLogin);
        refreshMainDisplay();
    }

    private void launchStudentRegActivity() {

        Intent intentStudentReg = new Intent(this, StudentRegistration.class);
        startActivity(intentStudentReg);
    }
    private void launchManageStudentActivity() {

        Intent intentStudentReg = new Intent(this, ManageStudents.class);
        startActivity(intentStudentReg);
    }

    private void launchAdminActivity() {
        Intent intentAdmin = new Intent(this, Admin.class);
        startActivity(intentAdmin);
    }


} // end of class

