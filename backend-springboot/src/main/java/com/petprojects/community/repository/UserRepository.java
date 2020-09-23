package com.petprojects.community.repository;

import com.petprojects.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByToken(String token);

    User findUserByEmailAddressAndPassword(String emailAddress, String password);

    User findUserByEmailAddress(String emailAddress);

}
