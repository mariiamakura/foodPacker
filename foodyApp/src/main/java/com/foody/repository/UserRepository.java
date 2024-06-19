package com.foody.repository;

import com.foody.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //provides some methods: findById, remove and etc.

    public User findByEmail(String email);

}
