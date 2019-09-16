package com.yjm.passengerapp.util;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Constants {
    public static final String MAPVIEW_BUNDLE_KEY = "bbb";
    public static final String BASE_URL = "http://23.239.215.199:2345/";
    public static Location currentLocation;
    public static LatLng bus_location;
    public  static  String ACCESS_TOKEN;
    public static ArrayList<String[]> ARRAY_SCHEDULE;
    public static ArrayList<String[]> ARRAY_SCHEDULE_DAY;
    public static String[] ITEM_TIME;
}
