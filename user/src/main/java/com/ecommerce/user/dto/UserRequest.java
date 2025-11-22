package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    public String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private AddressDTO address;
}
