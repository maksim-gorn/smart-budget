package com.tpu.itr.smart_budget.authentication.dto;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password_hash", nullable = false)
    private String password_hash;


    public UserEntity(Long id, String email, String phone_number, String password_hash) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phone_number;
        this.password_hash = password_hash;
    }

    public UserEntity() {

    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}
