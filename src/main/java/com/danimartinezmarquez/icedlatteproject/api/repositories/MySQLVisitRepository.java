package com.danimartinezmarquez.icedlatteproject.api.repositories;

import com.danimartinezmarquez.icedlatteproject.api.models.CommentModel;
import com.danimartinezmarquez.icedlatteproject.api.models.VisitModel;
import com.danimartinezmarquez.icedlatteproject.api.repositories.jpa.VisitJpaRepository;
import com.danimartinezmarquez.icedlatteproject.application.repositories.VisitRepository;
import com.danimartinezmarquez.icedlatteproject.domain.CoffeeShop;
import com.danimartinezmarquez.icedlatteproject.domain.Comment;
import com.danimartinezmarquez.icedlatteproject.domain.Visit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MySQLVisitRepository implements VisitRepository {

    private final VisitJpaRepository jpa;

    @Override
    public Visit save(Visit visit) {
        VisitModel toSave = toEntity(visit);
        VisitModel saved = jpa.save(toSave);
        return toDomain(saved);
    }

    @Override
    public Visit findById(Integer id) {
        return jpa.findById(id).map(this::toDomain).orElse(null);
    }

    @Override
    public List<Visit> findAll() {
        return jpa.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Visit> findByUserId(Integer userId) {
        return jpa.findByCreatedByUserId(userId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Visit> findByCoffeeShopId(Integer coffeeShopId) {
        return jpa.findByCoffeeShopId(coffeeShopId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Visit> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jpa.findByDateBetween(startDate, endDate).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        jpa.deleteById(id);
    }

    private Visit toDomain(VisitModel m) {
        if (m == null) return null;
        return Visit.builder()
                .visitId(m.getVisitId())
                .title(m.getTitle())
                .date(m.getDate())
                .userId(m.getCreatedByUserId())
                .coffeeShopId(m.getCoffeeShopId())
                .coffeeShop(toCoffeeShopDomain(m))
                .comments(
                        m.getComments().stream()
                                .map(c -> Comment.builder()
                                        .commentId(c.getCommentId())
                                        .body(c.getBody())
                                        .rating(c.getRating())
                                        .userId(c.getUserId())
                                        .visitId(c.getVisitId())
                                        .date(c.getDate())
                                        .build())
                .collect(Collectors.toList())
                )
                .build();
    }

    private CoffeeShop toCoffeeShopDomain(VisitModel visitModel) {
        if (visitModel == null || visitModel.getCoffeeShop() == null) {
            return null;
        }

        return CoffeeShop.builder()
                .coffeeShopId(visitModel.getCoffeeShop().getCoffeeShopId())
                .name(visitModel.getCoffeeShop().getName())
                .location(visitModel.getCoffeeShop().getLocation())
                .locationLatitude(visitModel.getCoffeeShop().getLocationLatitude())
                .locationLongitude(visitModel.getCoffeeShop().getLocationLongitude())
                .glutenFree(visitModel.getCoffeeShop().isGlutenFree())
                .lactoseFree(visitModel.getCoffeeShop().isLactoseFree())
                .vegetarianOptions(visitModel.getCoffeeShop().isVegetarianOptions())
                .coverPhotoUrl(visitModel.getCoffeeShop().getCoverPhotoUrl())
                .build();
    }

    private VisitModel toEntity(Visit v) {
        if (v == null) return null;
        return VisitModel.builder()
                .visitId(v.getVisitId())
                .title(v.getTitle())
                .date(v.getDate())
                .createdByUserId(v.getUserId())
                .coffeeShopId(v.getCoffeeShopId())
                .comments(
                        v.getComments().stream()
                                .map(c -> CommentModel.builder()
                                        .commentId(c.getCommentId())
                                        .body(c.getBody())
                                        .rating(c.getRating())
                                        .userId(c.getUserId())
                                        .visitId(v.getVisitId())
                                        .coffeeShopId(v.getCoffeeShopId())
                                        .date(c.getDate())
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }
}
