package com.danimartinezmarquez.icedlatteproject.api.controllers;

import com.danimartinezmarquez.icedlatteproject.application.VisitApplicationService;
import com.danimartinezmarquez.icedlatteproject.application.dtos.CreateVisitRequest;
import com.danimartinezmarquez.icedlatteproject.application.dtos.UpdateVisitRequest;
import com.danimartinezmarquez.icedlatteproject.application.dtos.VisitResponse;
import com.danimartinezmarquez.icedlatteproject.application.errors.CoffeeShopNotFoundException;
import com.danimartinezmarquez.icedlatteproject.application.errors.VisitNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
@AllArgsConstructor
public class VisitController {

    private final VisitApplicationService visitService;

    // Create
    @PostMapping
    public ResponseEntity<VisitResponse> create(@RequestBody CreateVisitRequest request) {
        VisitResponse created = visitService.createNew(request);
        URI location = URI.create("/api/visits/" + created.getVisitId());
        return ResponseEntity
                .created(location)
                .header(HttpHeaders.LOCATION, location.toString())
                .body(created);
    }

    // Read: by id
    @GetMapping("/{id}")
    public ResponseEntity<VisitResponse> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(visitService.getById(id));
    }

    // Read: all
    @GetMapping
    public ResponseEntity<List<VisitResponse>> getAll() {
        return ResponseEntity.ok(visitService.getAll());
    }

    // Read: by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VisitResponse>> getByUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(visitService.getByUserId(userId));
    }

    // Read: by coffee shop
    @GetMapping("/coffee-shop/{coffeeShopId}")
    public ResponseEntity<List<VisitResponse>> getByCoffeeShop(@PathVariable("coffeeShopId") Integer coffeeShopId) {
        return ResponseEntity.ok(visitService.getByCoffeeShopId(coffeeShopId));
    }

    // Read: by date range
    @GetMapping("/range")
    public ResponseEntity<List<VisitResponse>> getByDateRange(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(visitService.getByDateRange(start, end));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<VisitResponse> update(
            @PathVariable("id") Integer id,
            @RequestBody UpdateVisitRequest request
    ) {
        return ResponseEntity.ok(visitService.update(id, request));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        visitService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Exception mapping (simple, controller-scoped) ----

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<String> handleVisitNotFound(VisitNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CoffeeShopNotFoundException.class)
    public ResponseEntity<String> handleCoffeeShopNotFound(CoffeeShopNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
