package com.tpu.itr.smart_budget.authentication.dto;

public record LoginRequest(
        String phone_number,
        String password
) {
}
