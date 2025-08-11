package com.example.technicaltask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactsDto {
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+375\\s?(17|25|29|33|44)\\s?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$", message = "Неверный формат номера")
    private String phone;
    @Email
    private String email;
}
