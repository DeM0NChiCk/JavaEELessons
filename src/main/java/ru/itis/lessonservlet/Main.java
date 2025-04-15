package ru.itis.lessonservlet;

import org.apache.catalina.startup.Tomcat;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        String contextPath = "";
        String webappDir = new File("src/main/webapp").getAbsolutePath();

        tomcat.addWebapp(contextPath, webappDir);
        tomcat.start();
        tomcat.getServer().await();
    }
}