package com.danimartinezmarquez.icedlatteproject.api.config;

import com.danimartinezmarquez.icedlatteproject.application.CoffeeShopApplicationService;
import com.danimartinezmarquez.icedlatteproject.application.CommentApplicationService;
import com.danimartinezmarquez.icedlatteproject.application.VisitApplicationService;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CoffeeShopRepository;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CommentRepository;
import com.danimartinezmarquez.icedlatteproject.application.repositories.VisitRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServiceConfig {

    @Bean
    public CoffeeShopApplicationService coffeeShopApplicationService(CoffeeShopRepository coffeeShopRepository) {
        return new CoffeeShopApplicationService(coffeeShopRepository);
    }

    @Bean
    public VisitApplicationService visitApplicationService(VisitRepository visitRepository, CoffeeShopRepository coffeeShopRepository) {
        return new VisitApplicationService(visitRepository, coffeeShopRepository);
    }

    @Bean
    public CommentApplicationService commentApplicationService(CommentRepository commentRepository, VisitRepository visitRepository) {
        return new CommentApplicationService(commentRepository, visitRepository);
    }
}