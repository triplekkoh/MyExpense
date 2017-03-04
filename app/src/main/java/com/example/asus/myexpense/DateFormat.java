package com.example.asus.myexpense;

import java.text.DecimalFormat;

/**
 * Created by ASUS on 2017/3/1.
 */

public class DateFormat {

    public static String formatValue (Double num){
        DecimalFormat format = new DecimalFormat("#,###,###");
        String after = format.format(num);
        return after;
    }


}
