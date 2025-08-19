package com.dontwait.shopapp.repository;

import com.dontwait.shopapp.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAll(Specification<Order> spec, Pageable pageable);
    Optional<Order> findByOrderId(Long orderId);
    void deleteByOrderId(Long orderId);
    Boolean existsByOrderId(Long orderId);
}
