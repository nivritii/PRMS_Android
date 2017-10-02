package sg.edu.nus.iss.phoenix.scheduleprogram.entity;

/**
 * Created by nivi on 9/27/2017.
 */

public class WeeklySchedule {

    private String startDate;

    public WeeklySchedule(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
