package com.ct.webDemo.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    public static final Map<Integer, Character> charMap = new HashMap<Integer, Character>();
    public static final Pattern p = Pattern.compile("^(\\d+)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)");

    static {
        charMap.put(1, 'y');
        charMap.put(2, 'M');
        charMap.put(3, 'd');
        charMap.put(4, 'H');
        charMap.put(5, 'm');
        charMap.put(6, 's');
    }

    /**
     * 任意日期字符串转换为Date，不包括无分割的纯数字（13位时间戳除外） ，日期时间为数字，年月日时分秒，但没有毫秒,不包括CST格式
     *
     * @param dateString 日期字符串
     * @return Date
     */
    public static Date stringToDate(String dateString) {
        dateString = dateString.trim().replaceAll("[a-zA-Z]"," ");
        if(Pattern.matches("^[-+]?\\d{13}$",dateString)) {//支持13位时间戳
            return new Date(Long.parseLong(dateString));
        }
        Matcher m = p.matcher(dateString);
        StringBuilder sb = new StringBuilder(dateString);
        if (m.find(0)) {//从被匹配的字符串中，充index = 0的下表开始查找能够匹配pattern的子字符串。m.matches()的意思是尝试将整个区域与模式匹配，不一样。
            int count = m.groupCount();
            for (int i = 1; i <= count; i++) {
                for (Map.Entry<Integer, Character> entry : charMap.entrySet()) {
                    if (entry.getKey() == i) {
                        sb.replace(m.start(i), m.end(i), replaceEachChar(m.group(i), entry.getValue()));
                    }
                }
            }
        } else {
            System.out.println("错误的日期格式");
            return null;
        }
        String format = sb.toString();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.parse(dateString);
        } catch (ParseException e) {
            System.out.println("日期字符串转Date出错");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将指定字符串的所有字符替换成指定字符，跳过空白字符
     *
     * @param s 被替换字符串
     * @param c 字符
     * @return 新字符串
     */
    public static String replaceEachChar(String s, Character c) {
        StringBuilder sb = new StringBuilder("");
        for (Character c1 : s.toCharArray()) {
            if (c1 != ' ') {
                sb.append(String.valueOf(c));
            }
        }
        return sb.toString();
    }
    
    /**
     * 功能：判断字符串是否为日期格式,CST格式除外
     *
     * @param 
     * @return 
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         try {
        	//设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
         } catch (ParseException e) {
        	//如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
         } 
         return convertSuccess;
	}
    
    /**
     * 功能：判断字符串是否为日期格式,CST格式除外
     * 
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String str="2018-12-16 12:12:12";
        System.out.println(isDate(str));
        System.out.println(stringToDate(str));
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
        format1.setLenient(false);
        Date date = (Date) format1.parse(str);
        System.out.println(format1.parse(str));
        System.out.println(format1.format(date));
        
        str = "Mon Mar 26 17:29:21 CST 2018";
        System.out.println(stringToDate(str));
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		date = (Date) sdf.parse(str);
		String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
		System.out.println(formatStr);
		
		//2009-09-16 11:26:23
		String formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println(formatStr2);
    }
}