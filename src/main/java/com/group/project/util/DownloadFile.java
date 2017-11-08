package com.group.project.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFile {
    public static void main(String[] args) throws Exception {
        String url = "http://download.java.net/jdk/jdk-api-localizations/jdk-api-zh-cn/publish/1.6.0/chm/JDK_API_1_6_zh_CN.CHM";
        getDownload("1", url, "D://jdkapi1.6.CHM");
    }

    //下载文件方法
    public static boolean getDownload(String name, String url, String pathName) {
        BufferedInputStream bi = null;
        BufferedOutputStream bo = null;
        try {
            URL ul = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) ul.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            bi = new BufferedInputStream(conn.getInputStream());
            bo = new BufferedOutputStream(new FileOutputStream(pathName));
//        System.out.println("["+name+"]文件大约："+(conn.getContentLength()/1024/1024)+"M");
//        Date date=new Date();
//        System.out.println("["+name+"]开始下载："+new SimpleDateFormat("MM/dd HH:mm:ss ").format(date));
            byte[] by = new byte[1024];
            int len = 0;
            while ((len = bi.read(by)) != -1) {
                bo.write(by, 0, len);
            }
            return true;
//        Date endDate=new Date();
//        System.out.println("["+name+"]下载完毕："+new SimpleDateFormat("MM/dd HH:mm:ss ").format(endDate));
//        System.out.println("["+name+"]共计用时："+(endDate.getTime()-date.getTime())/1000+"s");
        } catch (Exception e) {
//            System.out.println("下载文件["+name+"]失败");
            return false;
        } finally {
            try {
                if (bo != null)
                    bo.close();
                if (bi != null)
                    bi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
