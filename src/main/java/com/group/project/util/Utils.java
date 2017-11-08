package com.group.project.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    /**
     * 将double类型数据保留指定位小数
     * @param value 输入的原始值
     * @param scale 保留的位数
     * @return
     */
    public static double formatDoule(double value, int scale) {
        BigDecimal bd = new BigDecimal(value);
        return bd.setScale(scale, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    /**
     * 格式化输入的百分比字符串
     * 若百分比>1%,则忽略小数部分,否则显示原值
     * @param value
     * @return
     */
    public static String formatPercent(String value) {
        if (value.contains(".")) {
            String temp = value.substring(0, value.indexOf("."));
            if (Integer.parseInt(temp) != 0) {
                value = temp + "%";
            }
        }
        return value;
    }

    /**
     * 获取用户真实IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 若用户经过多层代理则request.getHeader("x-forwarded-for")返回的为多个IP
        // 用逗号分隔，获取其中不为unknown的第一个IP作为用户的IP
        if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int i = 0; i < ips.length; i++) {
                if (!ips[i].equals("unknown")) {
                    ip = ips[i];
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 根据用户输入的密码和salt加密生成密码
     *
     * @param password
     * @param salt
     * @return
     */
    public static String createUserPassword(String password, String salt) {
        return MD5.getMD5(MD5.getMD5(password) + salt);
    }

    /**
     * 将用户手机号码4到7位数字变为*（）仅限中国大陆电话号码
     *
     * @param phone
     * @return
     */
    public static String encryptPhone(String phone) {
        if (phone != null && !"".equals(phone)) {
            return phone.substring(0, 5) + "****" + phone.substring(9);
        } else {
            return "";
        }
    }

    /**
     * 获取时间差字符串
     *
     * @param timestamp
     * @return
     */
    public static String getTimeGap(int timestamp) {
        int nowTime = new Long(System.currentTimeMillis() / 1000).intValue();
        int timeGap = Math.abs(nowTime - timestamp);
        String result = "";

        if (timeGap / (60 * 60 * 24 * 365) >= 1) {
            result = timeGap / (60 * 60 * 24 * 365) + "年";
        } else if (timeGap / (60 * 60 * 24 * 30) >= 1) {
            result = timeGap / (60 * 60 * 24 * 30) + "月";
        } else if (timeGap / (60 * 60 * 24 * 7) >= 1) {
            result = timeGap / (60 * 60 * 24 * 7) + "周";
        } else if (timeGap / (60 * 60 * 24) >= 1) {
            result = timeGap / (60 * 60 * 24) + "天";
        } else if (timeGap / (60 * 60) >= 1) {
            result = timeGap / (60 * 60) + "小时";
        } else if (timeGap / 60 >= 1) {
            result = timeGap / 60 + "分钟";
        } else {
            result = timeGap + "秒钟";
        }

        if (nowTime >= timestamp) {
            result += "前";
        } else {
            result += "后";
        }
        return result;
    }

    /**
     * 获取当前时间的格式化文本
     *
     * @return
     */
    public static String getFormatDate() {
        return new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss,SSS]").format(new Date());
    }

    public static int getNowUnixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static int getUnixTime(Date date) {
        return (int) (date.getTime() / 1000);
    }

    public static int getSecondsToNextDay() {
        // 通过日历类获取第二天凌晨00点00分00秒
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long expTime = calendar.getTimeInMillis();
        // 获取系统当前毫秒值
        long now = System.currentTimeMillis();

        int seconds = (int) ((expTime - now) / 1000);
        return seconds;
    }


    /**
     * 校验是否为空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || "".equals(string.trim()))
            return true;
        return false;
    }
    /**
     * 校验是否为空
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        if (string == null || "".equals(string.trim()))
            return false;
        return true;
    }

    /**
     * 去掉url中域名部分
     *
     * @param url
     * @return
     */
    public static String splitUrlDomain(String url) {
        if (url.contains("http")) {
            return url.substring(url.indexOf("/", 10) + 1);
        }
        return url;
    }
}
