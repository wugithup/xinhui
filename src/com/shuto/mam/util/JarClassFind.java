/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.util;


import java.util.*;

import java.io.*;
import java.util.jar.JarFile;


/**
 * 检测class文件jar包冲突工具类
 * @author SumMer
 */
public class JarClassFind {

    public static int count = 0;


    static public void main(String[] args) {
        // 要查询的class,要带包名的类名
        String className = "org.apache.http.conn.ssl.SSLSocketFactory";
        // 所要查找的JAR包的目录
        String libPath = "D:\\lib";
        String absoluteclassname = className.replace('.', '/') + ".class";
        System.out.println("Find class [" + className + "] in Path [" + libPath + "] Results:");
        findclassinlocalsystem(libPath, absoluteclassname);
        if (JarClassFind.count == 0) {
            System.out.println("Error:Can't Find Such Jar File!");
        }
        System.out.println("Find Process Ended! Total Results:" + JarClassFind.count);
    }

    private static void findclassinlocalsystem(String path, String classname) {

        if (path.charAt(path.length() - 1) != '\\') {
            path += '\\';
        }
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Error: Path not Existed! Please Check it out!");
            return;
        }

        String[] filelist = file.list();
        assert filelist != null;
        for (String aFilelist : filelist) {
            File temp = new File(path + aFilelist);
            if ((temp.isDirectory() && !temp.isHidden() && temp.exists())) {
                findclassinlocalsystem(path + aFilelist, classname);
            } else {
                if (aFilelist.endsWith("jar")) {
                    try {
                        JarFile jarfile = new JarFile(path + aFilelist);
                        for (Enumeration e = jarfile.entries(); e.hasMoreElements(); ) {
                            String name = e.nextElement().toString();
                            if (name.equals(classname) || name.contains(classname)) {
                                System.out.println("No." + ++JarClassFind.count);
                                System.out.println("Jar Package:" + path + aFilelist);
                                System.out.println(name);
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }
}
