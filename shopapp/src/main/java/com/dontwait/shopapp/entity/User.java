package com.dontwait.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long userId;

    @Column(name = "fullname", length = 100)
    String fullName;
    @Column(name = "phone_number", length = 11, nullable = false)
    String phoneNumber;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "user_address", length = 200)
    String userAddress;

    @Column(name = "password", nullable = false, length = 100)
    String password;

    @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    Integer isActive;

    @Column(name = "facebook_account_id", columnDefinition = "INT(0)")
    Integer facebookAccountId;

    @Column(name = "google_account_id", columnDefinition = "INT(0)")
    Integer googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<SocialAccount> socialAccounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Token> tokens;
}
