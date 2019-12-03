package com.example.afterschoolapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afterschoolapplication.ClassObjects.Student;
import com.example.afterschoolapplication.ClassObjects.Teacher;
import com.example.afterschoolapplication.DB.AppDatabase;
import com.example.afterschoolapplication.DB.StudentDAO;
import com.example.afterschoolapplication.DB.TeacherDAO;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageStudents extends AppCompatActivity {

    // member variables
    private TextView mStudentDisplay;
    private EditText mStudentName;

    private Button mClockBtn;

    // Room member variable
    StudentDAO mStudentDao;
    TeacherDAO mTeacherDao;

    // list to store all of our students!!
    List<Student> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        mClockBtn = (Button) findViewById(R.id.clockInOutBtn);
        mStudentDisplay = (TextView) findViewById(R.id.studentDisplay);
        mStudentName = (EditText) findViewById(R.id.studentNameEntry);

        mStudentDisplay.setMovementMethod(new ScrollingMovementMethod());

        mStudentDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getStudentDAO();

        mTeacherDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbTeacherName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getTeacherDAO();


        // testing here
        refreshDisplay();
        // end testing


//        refreshDisplay();

        mClockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockInOut();
                refreshDisplay();
            }
        });
    }

    // method to update the display
    private void refreshDisplay(){
        mStudents = mStudentDao.getStudents();
        if(! mStudents.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            mStudentDisplay.setText("");
            for(Student student: mStudents){
                stringBuilder.append(student.toString());
            }
            mStudentDisplay.append(stringBuilder.toString());
        } else {
            mStudentDisplay.setText("No students registered yet");
        }
    }


    // method to clock a student in and out
    private void clockInOut() {

        boolean studentFound = false;
        List<Student> studentList = mStudentDao.getStudents();
        List<Teacher> teacherList = mTeacherDao.getTeachers();
        String studentName = mStudentName.getText().toString();
        Teacher teacherLoggedIn = null;
        for(Teacher teacher: teacherList){
            if(teacher.getLoggedIn()){
                teacherLoggedIn = teacher;
            }
        }
        if(studentName.matches("")){
            Toast.makeText(this, "You need to enter a Student Name", Toast.LENGTH_SHORT).show();
            return;
        }
        // checks if a student with the same name has already been added to the list
        for (Student student: studentList){
            if(student.getName().equals(studentName)){
                studentFound = true;

                // was clocked in
                if(student.isClockedIn()) {
                    student.setClockedIn(false);
                    student.setTeacher(null);
                    student.addTime((int) getDateDiff(student.getTime(),new Date(),TimeUnit.MINUTES));
                    mStudentDao.update(student);

                } else {
                    student.setClockedIn(true);
                    student.setTime(new Date());
                    student.setTeacher(teacherLoggedIn.getName());
                    mStudentDao.update(student);
                }
            }
        }
        if(!studentFound){
            Toast.makeText(this, "Student wasn't found.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // method to go back to the main activity
    public void finish(View v){
        finish();
    }

}
