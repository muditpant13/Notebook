package com.example.mudit.notebook.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mudit on 3/8/16.
 */
public class AppUtils {

    public static String getCurrentDateTime() {

        return new SimpleDateFormat(AppConstants.DATE_FORMAT).format(System.currentTimeMillis() - 5.5 * 60 * 60 * 1000);
    }

    /**
     * Get a random UUID with the given prefix
     **/
    public static String getDocumentId(final String prefix) {
        String id;
        if (prefix == null) {
            return null;
        }
        UUID randOmUuid = UUID.randomUUID();
        String uuid = "";
        if(randOmUuid != null){
            uuid = randOmUuid.toString().toUpperCase();
            id = prefix + uuid;
            return id.trim();
        }
        return null;
    }

    //use this logic to get table occupancy time
    public static String getCreationTimeDifference(String creationTime) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
        String currentTime = new SimpleDateFormat(AppConstants.DATE_FORMAT).format(System.currentTimeMillis() - 5.5 * 60 * 60 * 1000);
        String creationTimeDifference = "";

        try {

            Date date1 = simpleDateFormat.parse(creationTime);
            Date date2 = simpleDateFormat.parse(currentTime);

            long different = date2.getTime() - date1.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;


            if (elapsedDays > 0) {
                creationTimeDifference += String.valueOf(elapsedDays) + "d";
            } else if (elapsedDays == 0 && elapsedHours > 0) {
                creationTimeDifference += String.valueOf(elapsedHours) + "h";
            } else if (elapsedDays == 0 && elapsedHours == 0 && elapsedMinutes > 0) {
                creationTimeDifference += String.valueOf(elapsedMinutes) + "m";
            } else {
                creationTimeDifference += "0m";
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return creationTimeDifference;
    }

   public static long getTimeDifference(String previousTime , String currentTime) {
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
       long difference = 0;
       try{

           Date date1 = simpleDateFormat.parse(previousTime);
           Date date2 = simpleDateFormat.parse(currentTime);

           difference = date2.getTime() - date1.getTime();

       }catch (ParseException e){
           e.printStackTrace();
       }
      return difference;
   }
}
