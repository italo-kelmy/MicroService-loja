package com.hgithub.com.italo_kelmy.loja_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LojaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaServiceApplication.class, args);
	}

}
