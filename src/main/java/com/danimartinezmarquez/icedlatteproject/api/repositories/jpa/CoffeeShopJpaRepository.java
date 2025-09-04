package com.danimartinezmarquez.icedlatteproject.api.repositories.jpa;

import com.danimartinezmarquez.icedlatteproject.api.models.CoffeeShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeShopJpaRepository extends JpaRepository<CoffeeShopModel, Integer> {
    List<CoffeeShopModel> findByLocation(String location);
    List<CoffeeShopModel> findByGlutenFreeAndLactoseFreeAndVegetarianOptions(boolean glutenFree, boolean lactoseFree, boolean vegetarianOptions);
    List<CoffeeShopModel> findByName(String name);
    List<CoffeeShopModel> findByNameContainingIgnoreCase(String partialName);
}