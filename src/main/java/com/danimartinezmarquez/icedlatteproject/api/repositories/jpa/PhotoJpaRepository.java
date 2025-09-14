package com.danimartinezmarquez.icedlatteproject.api.repositories.jpa;

import com.danimartinezmarquez.icedlatteproject.api.models.CoffeeShopModel;
import com.danimartinezmarquez.icedlatteproject.api.models.PhotoModel;
import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoJpaRepository extends JpaRepository<PhotoModel, Integer> {

    List<PhotoModel> findByUserId(Integer userId);

    List<PhotoModel> findByCommentId(Integer commentId);

    List<PhotoModel> findByCoffeeShopId(Integer coffeeShopId);

    List<PhotoModel> findByBucketName(String bucketName);

    boolean existsByBucketNameAndPhotoPath(String bucketName, String photoPath);
}
