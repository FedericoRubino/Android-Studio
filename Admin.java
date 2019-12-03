package com.example.afterschoolapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.afterschoolapplication.ClassObjects.Student;
import com.example.afterschoolapplication.DB.AppDatabase;
import com.example.afterschoolapplication.DB.StudentDAO;

import java.util.List;

public class Admin extends AppCompatActivity {

    private Button displayAllStudents;
//    private Button clockOutBtn;

    StudentDAO mStudentDAO;
    List<Student> mStudents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        displayAllStudents = (Button) findViewById(R.id.viewStudentsBtn);
//        clockOutBtn = (Button) findViewById(R.id.clockOutAllBtn);

        mStudentDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getStudentDAO();

    }

    public void launchAdminViewStudent(View v){
        Intent intent = new Intent(this, adminViewStudents.class);
        startActivity(intent);
    }


    public void launchViewReport(View v){
        Intent intent = new Intent(this, AdminViewReport.class);
        startActivity(intent);
    }

    public void clockOutAll(View v){
//        mStudentDAO.clockOutAllStudents();
        mStudents = mStudentDAO.getStudents();
        for(Student student: mStudents){
            student.setClockedIn(false);
            student.setTeacher(null);
            mStudentDAO.update(student);
        }
        Toast.makeText(this,"All Students have been clocked out!", Toast.LENGTH_SHORT).show();
    }

    public void finish(View v){
        finish();
    }

}
