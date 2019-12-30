package com.cskaoyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.cskaoyan.mapper")
@EnableTransactionManagement
public class SkyblueApplication {
	public static void main(String[] args) {
		SpringApplication.run(SkyblueApplication.class, args);
	}

}
