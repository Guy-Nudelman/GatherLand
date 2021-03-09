package com.gather.land.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    private static SimpleDateFormat simpleDateFormat = null;

    public static String getTimeAsStringFormat(long timeStampCreated) {
        if (simpleDateFormat == null)
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String timeFormat = simpleDateFormat.format(new Date(timeStampCreated));
        return timeFormat;
    }
}
