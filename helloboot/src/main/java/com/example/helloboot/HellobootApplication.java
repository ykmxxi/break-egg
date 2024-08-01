package com.example.helloboot;

import java.io.IOException;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.MediaType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HellobootApplication {

    public static void main(String[] args) {
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext ->
                servletContext.addServlet("hello", new HttpServlet() {
                    @Override
                    protected void service(final HttpServletRequest req, final HttpServletResponse resp)
                            throws ServletException, IOException {
                        // resp.setStatus(HttpStatus.OK.value()); // OK 상태 코드는 생략 가능
                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().println("Hello Servlet");
                    }
                }).addMapping("/hello")
        );
        webServer.start();
    }

}
