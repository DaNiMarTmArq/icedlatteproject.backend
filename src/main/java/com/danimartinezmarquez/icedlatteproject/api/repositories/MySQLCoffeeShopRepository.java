package com.danimartinezmarquez.icedlatteproject.api.repositories;

import com.danimartinezmarquez.icedlatteproject.api.models.CoffeeShopModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.jpa.CoffeeShopJpaRepository;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CoffeeShopRepository;
import com.danimartinezmarquez.icedlatteproject.domain.CoffeeShop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MySQLCoffeeShopRepository implements CoffeeShopRepository {

    private final CoffeeShopJpaRepository jpaRepository;

    private CoffeeShop toDomain(CoffeeShopModel model) {
        if (model == null) return null;
        return CoffeeShop.builder()
                .coffeeShopId(model.getCoffeeShopId())
                .name(model.getName())
                .location(model.getLocation())
                .locationLatitude(model.getLocationLatitude())
                .locationLongitude(model.getLocationLongitude())
                .glutenFree(model.isGlutenFree())
                .lactoseFree(model.isLactoseFree())
                .vegetarianOptions(model.isVegetarianOptions())
                .coverPhotoUrl(model.getCoverPhotoUrl())
                .build();
    }

    private CoffeeShopModel toEntity(CoffeeShop coffeeShop) {
        if (coffeeShop == null) return null;
        return CoffeeShopModel.builder()
                .coffeeShopId(coffeeShop.getCoffeeShopId())
                .name(coffeeShop.getName())
                .location(coffeeShop.getLocation())
                .locationLatitude(coffeeShop.getLocationLatitude())
                .locationLongitude(coffeeShop.getLocationLongitude())
                .glutenFree(Boolean.TRUE.equals(coffeeShop.getGlutenFree()))
                .lactoseFree(Boolean.TRUE.equals(coffeeShop.getLactoseFree()))
                .vegetarianOptions(Boolean.TRUE.equals(coffeeShop.getVegetarianOptions()))
                .coverPhotoUrl(coffeeShop.getCoverPhotoUrl())
                .build();
    }

    @Override
    public CoffeeShop save(CoffeeShop coffeeShop) {
        CoffeeShopModel saved = jpaRepository.save(toEntity(coffeeShop));
        return toDomain(saved);
    }

    @Override
    public CoffeeShop findById(Integer id) {
        Optional<CoffeeShopModel> model = jpaRepository.findById(id);
        return model.map(this::toDomain).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<CoffeeShop> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoffeeShop> findByLocation(String location) {
        return jpaRepository.findByLocation(location).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoffeeShop> findByDietaryOptions(Boolean glutenFree, Boolean lactoseFree, Boolean vegetarianOptions) {
        return jpaRepository.findByGlutenFreeAndLactoseFreeAndVegetarianOptions(
                        Boolean.TRUE.equals(glutenFree),
                        Boolean.TRUE.equals(lactoseFree),
                        Boolean.TRUE.equals(vegetarianOptions))
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoffeeShop> findByName(String name) {
        return jpaRepository.findByName(name).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoffeeShop> searchByName(String partialName) {
        return jpaRepository.findByNameContainingIgnoreCase(partialName).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}