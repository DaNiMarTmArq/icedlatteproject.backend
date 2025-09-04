package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.application.CoffeeShopApplicationService;
import com.danimartinezmarquez.icedlatteproject.application.dtos.CoffeeShopResponse;
import com.danimartinezmarquez.icedlatteproject.application.dtos.CreateCoffeeShopRequest;
import com.danimartinezmarquez.icedlatteproject.application.errors.CoffeeShopAlreadyExistException;
import com.danimartinezmarquez.icedlatteproject.application.errors.CoffeeShopNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coffeeshops")
@AllArgsConstructor
public class CoffeeShopController {

    private final CoffeeShopApplicationService coffeeShopService;

    @PostMapping
    public CoffeeShopResponse create(@RequestBody CreateCoffeeShopRequest request) {
        return coffeeShopService.createNew(request);
    }

    @GetMapping("/{id}")
    public CoffeeShopResponse getById(@PathVariable Integer id) {
        return coffeeShopService.getById(id);
    }

    @GetMapping
    public List<CoffeeShopResponse> getAll() {
        return coffeeShopService.getAll();
    }

    @GetMapping("/name")
    public List<CoffeeShopResponse> getByFullName(@RequestParam String name) {
        return coffeeShopService.getByFullName(name);
    }

    @GetMapping("/search")
    public List<CoffeeShopResponse> searchByName(@RequestParam String partialName) {
        return coffeeShopService.searchByName(partialName);
    }

    @GetMapping("/location")
    public List<CoffeeShopResponse> getByLocation(@RequestParam String location) {
        return coffeeShopService.getByLocation(location);
    }

    @GetMapping("/dietary")
    public List<CoffeeShopResponse> getByDietaryOptions(
            @RequestParam(required = false) Boolean glutenFree,
            @RequestParam(required = false) Boolean lactoseFree,
            @RequestParam(required = false) Boolean vegetarianOptions) {
        return coffeeShopService.getByDietaryOptions(glutenFree, lactoseFree, vegetarianOptions);
    }

    @ExceptionHandler(CoffeeShopNotFoundException.class)
    public ResponseEntity<String> handleNotFound(CoffeeShopNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CoffeeShopAlreadyExistException.class)
    public ResponseEntity<String> handleAlreadyExists(CoffeeShopAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
