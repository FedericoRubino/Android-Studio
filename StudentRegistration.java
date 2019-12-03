package com.example.afterschoolapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afterschoolapplication.ClassObjects.Student;
import com.example.afterschoolapplication.DB.AppDatabase;
import com.example.afterschoolapplication.DB.StudentDAO;

import java.util.ArrayList;
import java.util.List;

public class StudentRegistration extends AppCompatActivity {

    // member variables
    private TextView mStudentDisplay;
    private EditText mStudentName;
    private EditText mStudentGrade;
    private EditText mStudentGuardian;
    private EditText mStudentPaymentMethod;

    private Button saveBtn;

    // Room member variable
    StudentDAO mStudentDAO;

    // list to store all of our students!!
    List<Student> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        saveBtn = (Button) findViewById(R.id.registerStudentButton);
        mStudentName = (EditText) findViewById(R.id.studentNameField);
        mStudentGrade = (EditText) findViewById(R.id.studentIdEntryField);
        mStudentPaymentMethod = (EditText) findViewById(R.id.paymentMethodEntry);
        mStudentGuardian = (EditText) findViewById(R.id.guardianTextEntry);
        mStudentDisplay = (TextView) findViewById(R.id.studentDisplay);

        mStudentDisplay.setMovementMethod(new ScrollingMovementMethod());

        mStudentDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.dbName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .getStudentDAO();

//        mStudentDao.nukeTable();


        refreshDisplay();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitStudent();
                refreshDisplay();
            }
        });

        saveBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mStudentDAO.nukeTable();
                return false;
            }
        });
    }

    // method to update the display
    private void refreshDisplay(){
        mStudents = mStudentDAO.getStudents();
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

    // method that inserts a new student into the list
    private void submitStudent(){
        List<Student> studentList = mStudentDAO.getStudents();
        String studentName = mStudentName.getText().toString();
        if(studentName.matches("")){
            Toast.makeText(this, "You need to enter a Student Name", Toast.LENGTH_SHORT).show();
            return;
        }
        // checks if a student with the same name has already been added to the list
        for (Student student: studentList){
            if(student.getName().equals(studentName)){
                Toast.makeText(this, "Student with that name has already been registered!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        String temp = mStudentGrade.getText().toString();
        if(temp.matches("")){
            Toast.makeText(this, "You need to enter the student's grade", Toast.LENGTH_SHORT).show();
            return;
        }
        int studentGrade = Integer.parseInt(temp);
        String paymentMethod = mStudentPaymentMethod.getText().toString();
        if(paymentMethod.matches("")){
            Toast.makeText(this, "You need to enter a Student's payment method", Toast.LENGTH_SHORT).show();
            return;
        }
        String studentGuardians = mStudentGuardian.getText().toString();
        if(studentGuardians.matches("")){
            studentGuardians = "Mom and Pa";
        }
        mStudentDAO.insert(new Student(studentName,studentGrade,paymentMethod,studentGuardians));
    }


    // method to go back to the main activity
    public void finish(View v){
        finish();
    }

}
