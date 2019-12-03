package com.example.afterschoolapplication.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.afterschoolapplication.ClassObjects.Student;

import java.util.List;

@Dao
public interface StudentDAO {

    @Insert
    public void insert(Student... students);

    @Update
    public void update(Student... students);

    @Delete
    public void delete(Student... students);

    @Query("SELECT * FROM " + AppDatabase.STUDENT_TABLE)
    public List<Student> getStudents();

    // this Query is self written that clocks out all students
//    @Query("UPDATE " + AppDatabase.STUDENT_TABLE + " SET student_clocked=" + false)
//    public void clockOutAllStudents();

    @Query("SELECT * FROM " + AppDatabase.STUDENT_TABLE + " ORDER BY grade DESC")
    public List<Student> getAllStudentsOrdered();

    @Query("SELECT * FROM " + AppDatabase.STUDENT_TABLE + " WHERE name = :studentName")
    public Student getStudentWithName(String studentName);

    // This cleans out the entire database
    @Query("DELETE FROM " + AppDatabase.STUDENT_TABLE)
    public void nukeTable();
}
