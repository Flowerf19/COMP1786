package com.example.unversalyogajavaapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.unversalyogajavaapplication.Entity.ClassInstance;
import com.example.unversalyogajavaapplication.Entity.Course;
import com.example.unversalyogajavaapplication.Entity.Teacher;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Flowerf_Yoga.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_COURSE = "Course";
    private static final String TABLE_CLASS_INSTANCE = "ClassInstance";
    private static final String TABLE_TEACHER = "Teacher";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_COURSE + " (" +
                "CourseID TEXT PRIMARY KEY, " +
                "DayOfWeek TEXT, " +
                "Capacity INTEGER, " +
                "TimeOfCourse TEXT, " +
                "Duration INTEGER, " +
                "Price REAL, " +
                "Type TEXT, " +
                "Location TEXT, " +
                "CreateAt DATE" +
                ");");
        db.execSQL("CREATE TABLE " + TABLE_CLASS_INSTANCE + " (" +
                "ClassID TEXT PRIMARY KEY, " +
                "CourseID TEXT, " +
                "Date DATE, " +
                "TeacherID TEXT, " +
                "Comment TEXT, " +
                "CreateAt DATE, " +
                "FOREIGN KEY (CourseID) REFERENCES " + TABLE_COURSE + "(CourseID), " +
                "FOREIGN KEY (TeacherID) REFERENCES " + TABLE_TEACHER + "(TeacherID)" +
                ");");
        db.execSQL("CREATE TABLE " + TABLE_TEACHER + " (" +
                "TeacherID TEXT PRIMARY KEY, " +
                "Name TEXT, " +
                "Specialty TEXT, " +
                "ContactInfo TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS_INSTANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHER);
        onCreate(db);
    }

    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CourseID", course.getCourseID());
        cv.put("DayOfWeek", course.getDayOfWeek());
        cv.put("Capacity", course.getCapacity());
        cv.put("TimeOfCourse", course.getTimeOfCourse());
        cv.put("Duration", course.getDuration());
        cv.put("Price", course.getPrice());
        cv.put("Type", course.getType());
        cv.put("Location", course.getLocation());
        cv.put("CreateAt", course.getCreateAt());
        long result = db.insert(TABLE_COURSE, null, cv);
        if (result == -1) {
            System.out.println("Failed to insert course");
        } else {
            System.out.println("Course added successfully");
        }
        db.close();
    }

    Cursor readAllCourse() {
        String query = "SELECT * FROM " + TABLE_COURSE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void addTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("TeacherID", teacher.getTeacherID());
        cv.put("Name", teacher.getName());
        cv.put("Specialty", teacher.getSpecialty());
        cv.put("ContactInfo", teacher.getContactInfo());
        long result = db.insert(TABLE_TEACHER, null, cv);
        if (result == -1) {
            System.out.println("Failed to insert teacher");
        } else {
            System.out.println("Teacher added successfully");
        }
        db.close();
    }

    Cursor readAllInstructor() {
        String query = "SELECT * FROM " + TABLE_TEACHER;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void addClassInstance(ClassInstance classInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ClassID", classInstance.getClassID());
        cv.put("CourseID", classInstance.getCourseID());
        cv.put("Date", classInstance.getDate());
        cv.put("TeacherID", classInstance.getTeacherID());
        cv.put("Comment", classInstance.getComment());
        cv.put("CreateAt", classInstance.getCreateAt());
        long result = db.insert(TABLE_CLASS_INSTANCE, null, cv);
        if (result == -1) {
            System.out.println("Failed to insert class instance");
        } else {
            System.out.println("Class instance added successfully");
        }
        db.close();
    }


    public Cursor readAllClass() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ci.ClassID, ci.Date, c.CourseID, c.DayOfWeek, t.Name as TeacherName " +
                "FROM " + TABLE_CLASS_INSTANCE + " ci " +
                "JOIN " + TABLE_COURSE + " c ON ci.CourseID = c.CourseID " +
                "JOIN " + TABLE_TEACHER + " t ON ci.TeacherID = t.TeacherID";
        return db.rawQuery(query, null);
    }

    public Cursor getCourseByID(String courseID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSE + " WHERE CourseID = ?";
        return db.rawQuery(query, new String[]{courseID});
    }

    public Cursor getTeacherByID(String teacherID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TEACHER + " WHERE TeacherID = ?";
        return db.rawQuery(query, new String[]{teacherID});
    }

    public Cursor getClassByID(String classID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLASS_INSTANCE + " WHERE ClassID = ?";
        return db.rawQuery(query, new String[]{classID});
    }

    public void updateClassInstance(ClassInstance classInstance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CourseID", classInstance.getCourseID());
        cv.put("Date", classInstance.getDate());
        cv.put("TeacherID", classInstance.getTeacherID());
        cv.put("Comment", classInstance.getComment());
        cv.put("CreateAt", classInstance.getCreateAt());
        db.update(TABLE_CLASS_INSTANCE, cv, "ClassID = ?", new String[]{classInstance.getClassID()});
        db.close();
    }


    public void updateTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", teacher.getName());
        cv.put("Specialty", teacher.getSpecialty());
        cv.put("ContactInfo", teacher.getContactInfo());
        db.update(TABLE_TEACHER, cv, "TeacherID = ?", new String[]{teacher.getTeacherID()});
        db.close();
    }
    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DayOfWeek", course.getDayOfWeek());
        cv.put("Capacity", course.getCapacity());
        cv.put("TimeOfCourse", course.getTimeOfCourse());
        cv.put("Duration", course.getDuration());
        cv.put("Price", course.getPrice());
        cv.put("Type", course.getType());
        cv.put("Location", course.getLocation());
        cv.put("CreateAt", course.getCreateAt());
        db.update(TABLE_COURSE, cv, "CourseID = ?", new String[]{course.getCourseID()});
        db.close();
    }

    public void deleteCourse(String courseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, "CourseID = ?", new String[]{courseID});
        db.close();
    }

    public void deleteInstructor(String teacherID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEACHER, "TeacherID = ?", new String[]{teacherID});
        db.close();
    }

    public void deleteClass(String classID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLASS_INSTANCE, "ClassID = ?", new String[]{classID});
        db.close();
    }

    public Cursor searchClassesByDay(String dayOfWeek) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ci.ClassID, ci.Date, c.CourseID, c.DayOfWeek, t.Name as TeacherName " +
                "FROM " + TABLE_CLASS_INSTANCE + " ci " +
                "JOIN " + TABLE_COURSE + " c ON ci.CourseID = c.CourseID " +
                "JOIN " + TABLE_TEACHER + " t ON ci.TeacherID = t.TeacherID " +
                "WHERE c.DayOfWeek = ?";
        return db.rawQuery(query, new String[]{dayOfWeek});
    }
    public Cursor searchClassesByTeacherName(String teacherName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ci.ClassID, ci.Date, c.CourseID, c.DayOfWeek, t.Name as TeacherName " +
                "FROM " + TABLE_CLASS_INSTANCE + " ci " +
                "JOIN " + TABLE_COURSE + " c ON ci.CourseID = c.CourseID " +
                "JOIN " + TABLE_TEACHER + " t ON ci.TeacherID = t.TeacherID " +
                "WHERE t.Name LIKE ?";
        return db.rawQuery(query, new String[]{"%" + teacherName + "%"});
    }




}
