package com.danimartinezmarquez.icedlatteproject.application.repositories;

import com.danimartinezmarquez.icedlatteproject.domain.CoffeeShop;

import java.util.List;

public interface CoffeeShopRepository {
    CoffeeShop save(CoffeeShop coffeeShop);
    CoffeeShop findById(Integer id);
    void deleteById(Integer id);
    List<CoffeeShop> findAll();
    List<CoffeeShop> findByLocation(String trim);
    List<CoffeeShop> findByDietaryOptions(Boolean glutenFree, Boolean lactoseFree, Boolean vegetarianOptions);
}
