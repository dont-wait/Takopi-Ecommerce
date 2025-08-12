package com.dontwait.shopapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String fullName;
    String phoneNumber;
    String userAddress;
    Date dateOfBirth;
    Integer facebookAccountId;
    Integer googleAccountId;
    String userRole;
    Integer isActive;
}
