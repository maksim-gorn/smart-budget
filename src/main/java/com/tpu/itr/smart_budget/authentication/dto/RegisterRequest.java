package com.tpu.itr.smart_budget.authentication.dto;

public record RegisterRequest(
        String email,
        String phone_number,
        String password
) {
}
