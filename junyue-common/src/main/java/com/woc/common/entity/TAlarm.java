package com.woc.common.entity;

import java.io.Serializable;

/**
 * t_alarm
 * @author 
 */
public class TAlarm implements Serializable {
    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 课程提醒
     */
    private Integer courseAlarm;

    /**
     * 提醒时间
     */
    private String courseAlarmTime;

    private static final long serialVersionUID = 1L;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCourseAlarm() {
        return courseAlarm;
    }

    public void setCourseAlarm(Integer courseAlarm) {
        this.courseAlarm = courseAlarm;
    }

    public String getCourseAlarmTime() {
        return courseAlarmTime;
    }

    public void setCourseAlarmTime(String courseAlarmTime) {
        this.courseAlarmTime = courseAlarmTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TAlarm other = (TAlarm) that;
        return (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getCourseAlarm() == null ? other.getCourseAlarm() == null : this.getCourseAlarm().equals(other.getCourseAlarm()))
            && (this.getCourseAlarmTime() == null ? other.getCourseAlarmTime() == null : this.getCourseAlarmTime().equals(other.getCourseAlarmTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getCourseAlarm() == null) ? 0 : getCourseAlarm().hashCode());
        result = prime * result + ((getCourseAlarmTime() == null) ? 0 : getCourseAlarmTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentId=").append(studentId);
        sb.append(", courseAlarm=").append(courseAlarm);
        sb.append(", courseAlarmTime=").append(courseAlarmTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}