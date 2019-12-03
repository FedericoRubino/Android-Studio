package com.example.afterschoolapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afterschoolapplication.ClassObjects.Teacher;
import com.example.afterschoolapplication.DB.AppDatabase;
import com.example.afterschoolapplication.DB.TeacherDAO;

import java.util.List;

public class Login extends AppCompatActivity {

    // member variables
    private Button finishBtn;
    private Button loginBtn;
    private EditText usernameField;
    private EditText passwordField;
    private TextView displayUsername;

    // teacher DAO
    TeacherDAO teacherDAO;

    // list of teachers
    List<Teacher> teachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // wire up the fields and buttons
        finishBtn = (Button) findViewById(R.id.finish);
        usernameField = (EditText) findViewById(R.id.usernameEntry);
        passwordField = (EditText) findViewById(R.id.passwordEntry);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        displayUsername = (TextView) findViewById(R.id.displayUsername);

        teacherDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbTeacherName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getTeacherDAO();


        refreshDisplay();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsername();
                refreshDisplay();
            }
        });

    }

    // get the username and display it
    public void updateUsername(){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        List<Teacher> teacherList = teacherDAO.getTeachers();
        Teacher loggedInTeacher = null;
        Boolean teacherFound = false;

        if (username.matches("")){
            Toast.makeText(this,"Please include a username!", Toast.LENGTH_SHORT);
            return;
        }
        if (password.matches("")){
            Toast.makeText(this,"Please include a password!", Toast.LENGTH_SHORT);
            return;
        }
        for (Teacher teacher: teacherList){
            if(teacher.getName().equals(username) && teacher.getPassword().equals(password)){
                loggedInTeacher = teacher;
                teacherFound = true;
            }
        }
        if(!teacherFound){
            Toast.makeText(this,"Username or password not in database", Toast.LENGTH_SHORT).show();
            return;
        }
        for(Teacher teacher: teacherList){
            teacher.setLoggedIn(false);
            if(teacher.getName().equals(username)){
                teacher.setLoggedIn(true);
            }
            teacherDAO.update(teacher);
        }
    }


    public void refreshDisplay(){
        teachers = teacherDAO.getTeachers();
        String loggedInTeacher = "No one is logged in.";
        if(!teachers.isEmpty()){
            for(Teacher teacher: teachers){
                if(teacher.getLoggedIn()){
                    loggedInTeacher = teacher.getName();
                }
            }
        }
        displayUsername.setText("Logged in user: " + loggedInTeacher);
    }

    // method to go back to the main activity
    public void finish(View v){
        finish();
    }

}
