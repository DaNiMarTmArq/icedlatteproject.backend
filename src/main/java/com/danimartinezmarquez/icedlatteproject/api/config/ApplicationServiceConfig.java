package com.danimartinezmarquez.icedlatteproject.api.config;

import com.danimartinezmarquez.icedlatteproject.application.CoffeeShopApplicationService;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CoffeeShopRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServiceConfig {

    @Bean
    public CoffeeShopApplicationService coffeeShopApplicationService(CoffeeShopRepository coffeeShopRepository) {
        return new CoffeeShopApplicationService(coffeeShopRepository);
    }
}