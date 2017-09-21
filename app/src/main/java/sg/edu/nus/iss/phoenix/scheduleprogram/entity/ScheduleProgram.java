package sg.edu.nus.iss.phoenix.scheduleprogram.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * Created by nivi on 9/6/2017.
 */

public class ScheduleProgram {
    private String name;
    private String dateOfProgram;
    private String startTime;
    private String duration;

    public ScheduleProgram(String name, String dateOfProgram, String startTime, String duration) {
        this.name = name;
        this.dateOfProgram = dateOfProgram;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfProgram() {
        return dateOfProgram;
    }

    public void setDateOfProgram(String dateOfProgram) {
        this.dateOfProgram = dateOfProgram;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
