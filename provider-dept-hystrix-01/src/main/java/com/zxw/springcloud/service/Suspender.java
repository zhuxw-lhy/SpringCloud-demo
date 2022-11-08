package com.cludove.dirigible.gear.file;

import com.cludove.dirigible.gear.file.ClassFactory;
import com.cludove.dirigible.gear.file.Suspender;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.cludove.dirigible.gear.file.properties.Environment;

/**
 * 动态加载装置
 *
 * @author leon
 *
 */
public class Suspender extends ClassLoader {

    /** 装置起始位置 */
    public static final String LIB_PATH = "lib";

    /** 当前位置*/
    private String currentPath;

    /**
     * 获取一个URLClassLoader类加载器
     */
    private URLClassLoader suspenderLoader = new URLClassLoader(new URL[] {}, this);


    public Suspender() {
        super(Suspender.class.getClassLoader());
    }

    /**
     * 设置当前位置
     *
     */
    public void setCurrentPath(String path) {
        currentPath = path;
    }

    /**
     * 设置类加载器
     * @throws Exception
     */
    public void setSuspenderLoader() throws Exception {
        if (currentPath == null)
            currentPath = LIB_PATH;
        else
            currentPath = LIB_PATH + File.separator + currentPath; //拼接加载的地址
        /**创建Environment中指定的应用名位置的文件的对象*/
        File applicationHome = new File(Environment.getApplicationHome());
        /**获取目录下当前文件以及文件对象*/
        File[] homeFiles = applicationHome.listFiles();
        List<URL> libURLList = new ArrayList<URL>();
        /***/
        for (File homeFile : homeFiles) {
            if (LIB_PATH.equals(homeFile.getName())) {
                URL[] libURLs = ClassFactory.getCurrentList(homeFile);
                for (URL libURL : libURLs)
                    if (!libURLList.contains(libURL))
                        libURLList.add(libURL);
            }

            File file = new File(homeFile, LIB_PATH);
            if (file.exists()) {
                URL[] libURLs = ClassFactory.getCurrentList(file);
                for (URL libURL : libURLs)
                    if (!libURLList.contains(libURL))
                        libURLList.add(libURL);
            }
        }

        URL[] libURLs = new URL[libURLList.size()];
        for (int i = 0; i < libURLList.size(); i++)
            libURLs[i] = libURLList.get(i);

        synchronized (suspenderLoader) {
            suspenderLoader = null;
            suspenderLoader = new URLClassLoader(libURLs, this);
        }
    }



    /**
     * 根据名字加载类
     * @param className
     * @return类名
     * @throws Exception
     */
    public Class<?> getClass(String className) throws Exception {
        synchronized (suspenderLoader) {
            if (suspenderLoader == null)
                System.out.println("suspenderLoader is null.");
            if (className == null)
                System.out.println("className is null.");
            Class<?> suspenderClass = suspenderLoader.loadClass(className);

            if (suspenderClass == null)
                suspenderClass = Class.forName(className);

            return suspenderClass;
        }
    }

    public boolean isInitialize() throws Exception {
        if (suspenderLoader != null)
            return true;
        else
            return false;
    }

}