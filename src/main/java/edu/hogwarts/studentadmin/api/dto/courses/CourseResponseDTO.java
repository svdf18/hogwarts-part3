package edu.hogwarts.studentadmin.api.dto.courses;

import java.util.List;

public class CourseResponseDTO {
    private int id;
    private String subject;
    private int schoolYear;
    private boolean current;
    private int teacherId;
    private List<Integer> studentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public List<Integer> getStudentId() {
        return studentId;
    }

    public void setStudentId(List<Integer> studentId) {
        this.studentId = studentId;
    }
}
