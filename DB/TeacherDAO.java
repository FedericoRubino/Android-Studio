package com.example.afterschoolapplication.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.afterschoolapplication.ClassObjects.Teacher;

import java.util.List;

@Dao
public interface TeacherDAO {

    @Insert
    public void insert(Teacher... teachers);

    @Update
    public void update(Teacher... teachers);

    @Delete
    public void delete(Teacher... teachers);

    @Query("SELECT * FROM " + AppDatabase.TEACHER_TABLE)
    public List<Teacher> getTeachers();

    @Query("SELECT * FROM " + AppDatabase.TEACHER_TABLE + " WHERE name = :teacherName")
    public Teacher getTeacherWithName(String teacherName);

    @Query("DELETE FROM " + AppDatabase.TEACHER_TABLE)
    public void nukeTeachers();

}
