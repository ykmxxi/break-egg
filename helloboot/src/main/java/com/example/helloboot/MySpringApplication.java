package com.example.helloboot;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {

    public static void run(final Class<?> applicationClass, final String... args) {
        // 웹 컨테이너로 변경
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                // 서블릿 컨테이너를 초기화하고 띄우는 작업을 스프링 컨테이너 초기화 과정에서 진행
                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
//                dispatcherServlet.setApplicationContext(this); // 이 코드가 없어도 동작

                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*");
                });
                webServer.start();
            }
        };
        applicationContext.register(applicationClass);
        applicationContext.refresh();
    }

}
