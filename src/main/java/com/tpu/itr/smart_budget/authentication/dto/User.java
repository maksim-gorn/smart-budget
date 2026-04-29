package com.tpu.itr.smart_budget.authentication.dto;

public record User (
       Long id,
       String email,
       String phone_number,
       String password
) {}
