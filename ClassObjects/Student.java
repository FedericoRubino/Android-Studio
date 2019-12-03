package com.example.afterschoolapplication.ClassObjects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.afterschoolapplication.DB.AppDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = AppDatabase.STUDENT_TABLE)
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int studentID;

    private String name;
    private int grade;
    private String paymentType;

    @ColumnInfo(name = "student_clocked")
    private boolean clockedIn;
    private String guardians;
//    @NonNull
    private Date time;
    private int totalTime;
    private String teacher;

    public Student(String name, int grade, String paymentType, String guardians){
        this.name = name;
        this.grade = grade;
        this.paymentType = paymentType;
        this.guardians = guardians;
        clockedIn = false;
        time = new Date();
        teacher = null;
    }

    // setters and getters:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isClockedIn() {
        return clockedIn;
    }

    public void setClockedIn(boolean clockedIn) {
        this.clockedIn = clockedIn;
    }

    public String getGuardians() {
        return guardians;
    }

    public void setGuardians(String guardians) {
        this.guardians = guardians;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void addTime(int time) {
        this.totalTime = this.totalTime + time;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    // Students toString
    @Override
    public String toString() {
        String output  = "| Student: " + name + "\n| Grade: " + grade + " | In: " + clockedIn + "\n" +
                "| Guardians: " + guardians + "\n" +
                "| Payment: " + paymentType + "\n" +
                "| Time in: " + totalTime + "\n";
        if(this.teacher != null){
            output += "| Teacher in charge: " + teacher + "\n";
        }
        if(this.clockedIn){
            output += "| clocked in since:\n| " + time.toString() + "\n" +
                    "------------------------------------------\n";
        } else {
            output += "------------------------------------------\n";
        }
        return output;
    }

}
