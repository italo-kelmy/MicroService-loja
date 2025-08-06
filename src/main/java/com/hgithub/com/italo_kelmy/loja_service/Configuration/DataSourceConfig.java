package com.hgithub.com.italo_kelmy.loja_service.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class DataSourceConfig {




    @Bean
    public DataSource dataSource(){
       return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/produtoseletronicos?useSSL=false&serverTimezone=UTC")
                .username("italo")
                .password("2610!!")
               .build();
    }
}
