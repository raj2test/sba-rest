package com.fsd.sba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SbaRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbaRestApplication.class, args);
	} 

}
