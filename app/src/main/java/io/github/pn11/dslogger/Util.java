package io.github.pn11.dslogger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for utility functions.
 */

public class Util {

    public static String getCurrentDate() { //http://qiita.com/zuccyi/items/d9c185588a5628837137
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    public static String getCurrentDateHuman() { //http://qiita.com/zuccyi/items/d9c185588a5628837137
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }
}
