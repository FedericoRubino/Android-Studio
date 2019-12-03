package com.example.afterschoolapplication.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.afterschoolapplication.ClassObjects.Student;
import com.example.afterschoolapplication.ClassObjects.Teacher;
import com.example.afterschoolapplication.DB.typeConverter.DateTypeConverter;

@Database(entities = {Student.class, Teacher.class}, version = 13)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    // Student Database
    public static final String dbName = "db-Student";

    public static final String STUDENT_TABLE = "student";

    public abstract StudentDAO getStudentDAO();


    // Teacher Database
    public static final String dbTeacherName = "db-Teacher";

    public static final String TEACHER_TABLE = "teacher";

    public abstract TeacherDAO getTeacherDAO();

}
