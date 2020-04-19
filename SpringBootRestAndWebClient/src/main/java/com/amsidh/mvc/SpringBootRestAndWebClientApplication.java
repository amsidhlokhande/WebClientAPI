package com.amsidh.mvc;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.amsidh.mvc.repositories" })
public class SpringBootRestAndWebClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestAndWebClientApplication.class, args);
	}
	/*
	 * @Value("${http.port}") public Integer httpServerPort;
	 * 
	 * @Value("${server.port}") public Integer httpsServerPort;
	 * 
	 * @Bean public ServletWebServerFactory getTomcatServletWebServerFactory() {
	 * final TomcatServletWebServerFactory tomcatServletWebServerFactory = new
	 * TomcatServletWebServerFactory();
	 * tomcatServletWebServerFactory.addAdditionalTomcatConnectors(getConnector());
	 * return tomcatServletWebServerFactory; }
	 * 
	 * private Connector getConnector() { final Connector connector = new
	 * Connector("org.apache.coyote.http11.Http11NioProtocol");
	 * connector.setScheme("http"); connector.setPort(httpServerPort);
	 * connector.setSecure(true); connector.setRedirectPort(httpsServerPort); return
	 * connector; }
	 */
}
