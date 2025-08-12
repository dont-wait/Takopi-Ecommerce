package com.dontwait.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column(name = "order_fullname", length = 100)
    String orderFullName;

    @Column(name = "order_email", length = 100)
    String orderEmail;

    @Column(name = "order_phone_number", length = 11, nullable = false)
    String orderPhoneNumber;

    @Column(name = "order_address", length = 100, nullable = false)
    String address;

    @Column(name = "note", length = 100)
    String note;

    @Column(name = "order_date")
    LocalDateTime orderDate;

    @Column(name = "order_status")
    String orderStatus;

    @Column(name = "total_money")
    BigDecimal totalMoney;

    @Column(name = "shipping_method")
    String shippingMethod;

    @Column(name = "shipping_address")
    String shippingAddress;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method")
    String paymentMethod;

    @Column(name = "active", columnDefinition = "TINYINT(1)")
    Boolean active;//thuộc về admin

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    List<OrderDetail> orderDetails;
}
