package com.yjm.passengerapp.directionHelper;


public interface TaskLoadedCallback {
    //void onTaskDone(Object... values);

    void onTaskDone(long distanceVal, long durationVal, Object... values);
}