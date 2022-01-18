package com.poscoict.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class ContextLoadListener
 *
 */
public class ContextLoadListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext sc = sce.getServletContext();
//    	sc.setAttribute(null, sc); 에 저장하면 내려가기 전까지 계속 저장되어 있는 객체가 된다.
    	String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
    	System.out.println("Application(mysite02) Starts..." +contextConfigLocation );
    	
    }
    

    public void contextDestroyed(ServletContextEvent sce)  { 
         
    	
    }

}
