package com.tpu.itr.smart_budget.authentication.dto;

public record LoginRequest(
        String email,
        String phone_number,
        String password
) {
}
