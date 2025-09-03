package com.dontwait.shopapp.repository;

import com.dontwait.shopapp.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderOrderId(Long orderId); //order.orderId
    Optional<OrderDetail> findByOrderDetailId(long orderDetailId);
}
