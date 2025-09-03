package com.dontwait.shopapp.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    INVALID_ID_KEY(1001, "Invalid Message key, you should check your key", HttpStatus.BAD_REQUEST), //Sai sot trong dat viec dat Message tai DTO
    CATEGORY_NAME_CANT_NULL(1002, "Category name can't null", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_MUST_BE_GREATER_THAN_3(1002, "Category name must be greater than 3", HttpStatus.CONFLICT),
    PRODUCT_PRICE_MUST_BE_GREATER_THAN_0(1002, "Product price must be greater or equal than 0", HttpStatus.CONFLICT),
    PRODUCT_NUMBER_MUST_BE_GREATER_THAN_1(1002, "Product number must be greater than 1", HttpStatus.CONFLICT),
    PRODUCT_TOTAL_MONEY_MUST_BE_GREATER_THAN_0(1002, "Product total money must be greater than 0", HttpStatus.CONFLICT),
    PRODUCT_NAME_MUST_BE_GREATER_THAN_3(1002, "Product name must be greater than 3", HttpStatus.CONFLICT),
    USER_ID_MUST_BE_GREATER_THAN_1(1002, "User id must be greater than 1", HttpStatus.CONFLICT),
    ORDER_ID_MUST_BE_GREATER_THAN_1(1002, "Order id must be greater than 1", HttpStatus.CONFLICT),
    PRODUCT_ID_MUST_BE_GREATER_THAN_1(1002, "Product id must be greater than 1", HttpStatus.CONFLICT),
    ROLE_MUST_BE_GREATER_THAN_1(1002, "Role must be greater than 1", HttpStatus.CONFLICT),
    PHONE_NUMBER_EXISTED(1002, "Phone number existed", HttpStatus.CONFLICT),
    SIZE_OF_PRODUCT_IMAGES_CANNOT_GREATER_THAN_5(1002, "Size of product images cannot greater than 5", HttpStatus.CONFLICT),
    FULLNAME_CANT_BLANK(1002, "Full name can't blank", HttpStatus.BAD_REQUEST),
    USERNAME_CANT_BLANK(1002, "Username can't blank", HttpStatus.BAD_REQUEST),

    ADDRESS_CANT_BLANK(1002, "Address can't blank", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_CANT_BLANK(1002, "Phone number can't blank", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_MUST_BE_10_DIGIT(1002, "Phone number must be 10 digit", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1002, "Email is invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_CANT_BLANK(1002, "Password Cant blank", HttpStatus.BAD_REQUEST),
    RE_PASSWORD_CANT_BLANK(1002, "RePassword Cant blank", HttpStatus.BAD_REQUEST),
    ROLE_CANT_NULL(1002, "Role can't null", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1002, "Password must be include 0-9 characters, number and specific character(s)", HttpStatus.BAD_REQUEST),
    RE_PASSWORD_NOT_MATCH(1002, "Password and re-password must be same", HttpStatus.BAD_REQUEST),

    FILE_TOO_LARGE(1003, "File too large! Maximum size is 10MB", HttpStatus.PAYLOAD_TOO_LARGE),
    FILE_TYPE_NOT_SUPPORTED(1003, "File must be an image", HttpStatus.UNSUPPORTED_MEDIA_TYPE),

    CATEGORY_NAME_EXISTED(1003, "Category name existed", HttpStatus.CONFLICT),

    ORDER_DETAIL_ID_NOT_FOUND(1004, "Order detail id not found", HttpStatus.NOT_FOUND),
    PRODUCT_ID_NOT_FOUND(1004, "Product id not found", HttpStatus.NOT_FOUND),
    ORDER_ID_NOT_FOUND(1004, "Order id not found", HttpStatus.NOT_FOUND),
    FULLNAME_NOT_FOUND(1004, "Full name not found", HttpStatus.NOT_FOUND),
    CATEGORY_ID_NOT_FOUND(1004, "Category id not found", HttpStatus.NOT_FOUND),
    ROLE_ID_NOT_FOUND(1004, "Role id not found", HttpStatus.NOT_FOUND),
    USER_ID_NOT_FOUND(1004, "User id not found", HttpStatus.NOT_FOUND),
    UNCATEGORIZED(6789, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED);
    int code;
    String message;
    HttpStatus httpStatus;
}
