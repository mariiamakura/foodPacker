package com.foody.repository;

import com.foody.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderIteamRepository extends JpaRepository<OrderItem, Long> {
}
