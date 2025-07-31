package com.danimartinezmarquez.icedlatteproject.api.repositories;

import com.danimartinezmarquez.icedlatteproject.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByEmail(String mail);
}
