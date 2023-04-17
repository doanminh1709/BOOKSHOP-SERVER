package project.spring.quanlysach.application.utils;

import project.spring.quanlysach.application.constants.CommonConstant;

import java.text.ParseException;
import java.util.Date;

public class DateTimeUntil {

    public static String getDateTimeNow() {
        return CommonConstant.FORMAT_DATE.format(new Date());
    }

    public static String getDateTimeNowDetail() {
        return CommonConstant.FORMAT_DATE_DETAIL.format(new Date());
    }

    public static Date getDateByString(String str) throws ParseException {
        return CommonConstant.FORMAT_DATE_DETAIL.parse(str);
    }

    public static String formatDateStandard(String date) {

        String[] formatDate = new String[3];
        StringBuilder s1 = new StringBuilder(),
                s2 = new StringBuilder(),
                s3 = new StringBuilder();
        int mark1 = 0, mark2 = 0;
        //lay ra
        for (int i = 0; i < date.length(); i++) {
            mark1 = i + 1;
            if (date.charAt(i) == '/' || date.charAt(i) == '-') {
                break;
            }
            s1.append(date.charAt(i));
        }
        //lay ra
        for (int i = mark1; i < date.length(); i++) {
            if (date.charAt(i) == '/' || date.charAt(i) == '-') {
                mark2 = i + 1;
                break;
            }
            s2.append(date.charAt(i));
        }
        while (mark2 < date.length()) {
            s3.append(date.charAt(mark2));
            mark2++;
        }
        //format
        if (Integer.parseInt(s2.toString()) > 12) {
            formatDate[0] = s1.toString();
            formatDate[1] = s3.toString();
            formatDate[2] = s2.toString();
        } else {
            formatDate[0] = s1.toString();
            formatDate[1] = s2.toString();
            formatDate[2] = s3.toString();
        }
        return formatDate[0] + "-" + formatDate[1] + "-" + formatDate[2];
    }
}
