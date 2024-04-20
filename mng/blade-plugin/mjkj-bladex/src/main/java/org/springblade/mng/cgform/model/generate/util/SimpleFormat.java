package org.springblade.mng.cgform.model.generate.util;

import java.text.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class SimpleFormat
{
    public static String underlineToHump(String para) {
        final StringBuilder sb = new StringBuilder();
        for (String s : para.split("_")) {
            if (!para.contains("_")) {
                sb.append(s);
            }
            else if (sb.length() == 0) {
                sb.append(s.toLowerCase());
            }
            else {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int n = 0;
        if (!para.contains("_")) {
            for (int i = 0; i < para.length(); ++i) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + n, "_");
                    ++n;
                }
            }
        }
        if (sb.toString().toLowerCase().startsWith("_")) {
            return sb.toString().toLowerCase().substring(1);
        }
        return sb.toString().toLowerCase();
    }

    public static String humpToShortbar(String para) {
        StringBuilder sb = new StringBuilder(para);
        int n = 0;
        if (!para.contains("-")) {
            for (int i = 0; i < para.length(); ++i) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + n, "-");
                    ++n;
                }
            }
        }
        if (sb.toString().toLowerCase().startsWith("-")) {
            return sb.toString().toLowerCase().substring(1);
        }
        return sb.toString().toLowerCase();
    }

    public String number(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "NaN";
        }
        return new DecimalFormat("0.00").format(Double.parseDouble(obj.toString()));
    }

    public String number(Object obj, String pattern) {
        obj = ((obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "NaN";
        }
        return new DecimalFormat(pattern).format(Double.parseDouble(obj.toString()));
    }

    public String round(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "NaN";
        }
        return new DecimalFormat("0").format(Double.parseDouble(obj.toString()));
    }

    public String currency(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj);
        return NumberFormat.getCurrencyInstance(Locale.CHINA).format(obj);
    }

    public String timestampToString(Object obj, String pattern) {
        if (obj == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM\u6708 -yy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern);
        Date parse;
        try {
            parse = simpleDateFormat.parse(obj.toString());
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            return "error";
        }
        return simpleDateFormat2.format(parse);
    }

    public String percent(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? Integer.valueOf(0) : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "";
        }
        return NumberFormat.getPercentInstance(Locale.CHINA).format(obj);
    }

    public String date(Object obj, String pattern) {
        if (obj == null) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(obj);
    }

    public String date(Object obj) {
        if (obj == null) {
            return "";
        }
        return DateFormat.getDateInstance(1, Locale.CHINA).format(obj);
    }

    public String time(Object obj) {
        if (obj == null) {
            return "";
        }
        return DateFormat.getTimeInstance(3, Locale.CHINA).format(obj);
    }

    public String datetime(Object obj) {
        if (obj == null) {
            return "";
        }
        return DateFormat.getDateTimeInstance(1, 3, Locale.CHINA).format(obj);
    }

    public String getInStrs(List<String> params) {
        StringBuffer sb = new StringBuffer();
        Iterator<String> iterator = params.iterator();
        while (iterator.hasNext()) {
            sb.append("'" + iterator.next() + "',");
        }
        String string = sb.toString();
        if (!"".equals(string) || string.endsWith(",")) {
            return string.substring(0, string.length() - 1);
        }
        return null;
    }
}
