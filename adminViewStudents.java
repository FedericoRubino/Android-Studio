package com.example.afterschoolapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.afterschoolapplication.ClassObjects.Student;
import com.example.afterschoolapplication.DB.AppDatabase;
import com.example.afterschoolapplication.DB.StudentDAO;

import java.util.List;

public class adminViewStudents extends AppCompatActivity {

    TextView mDisplayStudents;
    StudentDAO mStudentDao;
    List<Student> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_students);

        mDisplayStudents = (TextView) findViewById(R.id.studentDisplay);
        mDisplayStudents.setMovementMethod(new ScrollingMovementMethod());


        mStudentDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getStudentDAO();

        refreshDisplay();
    }

    // method to update the display
    private void refreshDisplay(){
        mStudents = mStudentDao.getStudents();
        if(! mStudents.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            mDisplayStudents.setText("");
            for(Student student: mStudents){
                stringBuilder.append(student.toString());
            }
            mDisplayStudents.append(stringBuilder.toString());
        } else {
            mDisplayStudents.setText("No students registered yet");
        }
    }


    public void finish(View view){
        finish();
    }
}
