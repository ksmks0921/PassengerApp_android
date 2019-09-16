package com.yjm.passengerapp.models;

public class Schedule {

    String ShiftDate;
    String time;
    boolean IsFrom;

    public String getShiftDate() {
        return ShiftDate;
    }

    public void setShiftDate(String shiftDate) {
        ShiftDate = ShiftDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFrom() {
        return IsFrom;
    }

    public void setFrom(boolean from) {
        IsFrom = from;
    }
}
