package sg.edu.nus.iss.phoenix.scheduleprogram.entity;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * Created by nivi on 9/6/2017.
 */

public class ScheduleProgram {
    private RadioProgram radioProgram;
    private String dateOfProgram;
    private String startTime;
    private String duration;

    public ScheduleProgram(RadioProgram radioProgram, String dateOfProgram, String startTime, String duration) {
        this.radioProgram = radioProgram;
        this.dateOfProgram = dateOfProgram;
        this.startTime = startTime;
        this.duration = duration;
    }

    public RadioProgram getRadioProgram() {
        return radioProgram;
    }

    public void setRadioProgram(RadioProgram radioProgram) {
        this.radioProgram = radioProgram;
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
