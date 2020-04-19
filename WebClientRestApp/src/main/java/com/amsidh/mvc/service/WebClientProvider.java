package com.amsidh.mvc.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.Collections;

import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;
import reactor.netty.tcp.TcpClient;



public class WebClientProvider {

	public static WebClient.Builder getWebClientBuilder() {
		TcpClient tcpClient= TcpClient.create().secure(getSecureSslProvider());
		HttpClient httpClient= HttpClient.from(tcpClient);
		ClientHttpConnector clientHttpConnector= new ReactorClientHttpConnector(httpClient);
		return WebClient.builder().clientConnector(clientHttpConnector);
	}

	public static SslProvider  getNonSecureSslProvider() {
		SslProvider sslProvider = SslProvider.builder().sslContext(  
				  SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
				)
				.defaultConfiguration(SslProvider.DefaultConfigurationType.NONE).build();
		
		return sslProvider;

	}
	
	public static SslProvider  getSecureSslProvider() {
		
		SslContext sslContext = null; 
		
		try {  
		    KeyStore ks = KeyStore.getInstance("JKS");
		     String path = WebClientProvider.class.getResource("/").getPath();
		    System.out.println(path);
		    try (InputStream is = WebClientProvider.class.getResourceAsStream("/keystore.jks")) {
		        ks.load(is, "Pass@123".toCharArray());
		    }

		    X509Certificate[] trusted = Collections.list(ks.aliases()).stream().map(alias -> {
		        try {
		            return (X509Certificate) ks.getCertificate(alias);
		        } catch (KeyStoreException e) {
		            throw new RuntimeException(e);
		        }
		    }).toArray(X509Certificate[]::new);

		    sslContext = SslContextBuilder.forClient().trustManager(trusted).build();
		} catch (GeneralSecurityException | IOException e) {
		    throw new RuntimeException(e);
		}
				
		return SslProvider.builder().sslContext(sslContext).build();

	}

}
