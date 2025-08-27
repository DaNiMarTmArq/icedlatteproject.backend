package com.danimartinezmarquez.icedlatteproject.application;

import com.danimartinezmarquez.icedlatteproject.application.dtos.CoffeeShopResponse;
import com.danimartinezmarquez.icedlatteproject.application.dtos.CreateCoffeeShopRequest;
import com.danimartinezmarquez.icedlatteproject.application.errors.CoffeeShopNotFoundException;
import com.danimartinezmarquez.icedlatteproject.application.repositories.CoffeeShopRepository;
import com.danimartinezmarquez.icedlatteproject.domain.CoffeeShop;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CoffeeShopApplicationService {

    private final CoffeeShopRepository coffeeShopRepository;

    public CoffeeShopResponse createNew(CreateCoffeeShopRequest newShopRequest) {
        validateRequest(newShopRequest);

        CoffeeShop coffeeShop = mapRequestToDomain(newShopRequest);
        CoffeeShop savedCoffeeShop = coffeeShopRepository.save(coffeeShop);

        return mapDomainToResponse(savedCoffeeShop);
    }

    public CoffeeShopResponse getById(Integer coffeeShopId) {
        validateId(coffeeShopId);

        CoffeeShop coffeeShop = coffeeShopRepository.findById(coffeeShopId);
        if (coffeeShop == null) {
            throw new CoffeeShopNotFoundException("Coffee shop not found with id: " + coffeeShopId);
        }

        return mapDomainToResponse(coffeeShop);
    }

    public List<CoffeeShopResponse> getAll() {
        List<CoffeeShop> coffeeShops = coffeeShopRepository.findAll();
        return coffeeShops.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<CoffeeShopResponse> getByLocation(String location) {
        validateLocation(location);

        List<CoffeeShop> coffeeShops = coffeeShopRepository.findByLocation(location.trim());
        return coffeeShops.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    public List<CoffeeShopResponse> getByDietaryOptions(Boolean glutenFree, Boolean lactoseFree, Boolean vegetarianOptions) {
        List<CoffeeShop> coffeeShops = coffeeShopRepository.findByDietaryOptions(glutenFree, lactoseFree, vegetarianOptions);
        return coffeeShops.stream()
                .map(this::mapDomainToResponse)
                .collect(Collectors.toList());
    }

    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Coffee shop ID must be a positive integer");
        }
    }

    private void validateLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
    }

    private void validateCoordinates(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Latitude and longitude are required");
        }
        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0 || latitude.compareTo(BigDecimal.valueOf(90)) > 0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0 || longitude.compareTo(BigDecimal.valueOf(180)) > 0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }

    private void validateRadius(BigDecimal radiusKm) {
        if (radiusKm == null || radiusKm.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Radius must be a positive number");
        }
    }

    private CoffeeShop mapRequestToDomain(CreateCoffeeShopRequest request) {
        return CoffeeShop.builder()
                .name(request.getName().trim())
                .location(request.getLocation().trim())
                .locationLatitude(request.getLocationLatitude())
                .locationLongitude(request.getLocationLongitude())
                .glutenFree(request.getGlutenFree() != null ? request.getGlutenFree() : false)
                .lactoseFree(request.getLactoseFree() != null ? request.getLactoseFree() : false)
                .vegetarianOptions(request.getVegetarianOptions() != null ? request.getVegetarianOptions() : false)
                .coverPhotoUrl(request.getCoverPhotoUrl())
                .build();
    }

    private CoffeeShopResponse mapDomainToResponse(CoffeeShop coffeeShop) {
        return CoffeeShopResponse.builder()
                .coffeeShopId(coffeeShop.getCoffeeShopId())
                .name(coffeeShop.getName())
                .location(coffeeShop.getLocation())
                .locationLatitude(coffeeShop.getLocationLatitude())
                .locationLongitude(coffeeShop.getLocationLongitude())
                .glutenFree(coffeeShop.getGlutenFree())
                .lactoseFree(coffeeShop.getLactoseFree())
                .vegetarianOptions(coffeeShop.getVegetarianOptions())
                .coverPhotoUrl(coffeeShop.getCoverPhotoUrl())
                .build();
    }

    private void validateRequest(CreateCoffeeShopRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Coffee shop request cannot be null");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Coffee shop name is required");
        }
        if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Coffee shop location is required");
        }
    }
}
