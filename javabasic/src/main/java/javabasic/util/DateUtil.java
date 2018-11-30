package javabasic.util;

import org.apache.logging.log4j.core.util.datetime.FastDateFormat;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * @author wenchao.meng
 *         <p>
 *         Nov 29, 2018
 */
public class DateUtil {

    public static String format = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String currentTimeAsString() {
        return FastDateFormat.getInstance(format).format(new Date());
    }

    public static String timeAsString(Date date) {
        return FastDateFormat.getInstance(format).format(date);
    }

    public static Date stringAsDate(String datetimeString) throws ParseException {

        int indexOfDot = datetimeString.lastIndexOf(".");
        int milliLength = datetimeString.length() - indexOfDot - 1;
        if (milliLength > 3) {
            datetimeString = datetimeString.substring(0, datetimeString.length() - (milliLength - 3));
        }
        return FastDateFormat.getInstance(format).parse(datetimeString);


    }


    //2018-11-28 21:31:06.777556
    @Test
    public void fromString() throws ParseException {

//        Date date = stringAsDate("2018-11-28 21:31:06.777556");
//        Date date2 = stringAsDate("2018-11-28 21:31:06.778556");

        Date date = stringAsDate("2018-11-28 21:31:06.777123");
        Date date2 = stringAsDate("2018-11-28 21:31:06.778123");

        System.out.println(date2.getTime() - date.getTime());
        System.out.println(timeAsString(date));
        System.out.println(timeAsString(date2));

    }
}
