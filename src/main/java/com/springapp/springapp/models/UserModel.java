package com.springapp.springapp.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USERS")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DSNAME", nullable = false, length = 255)
    private String name;

    @Column(name = "DSEMAIL", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "FGROLE", nullable = false, length = 5)
    private Integer role;

    @Column(name = "DSPASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdat;

    @Column(name = "UPDATED_AT", nullable = true)
    private LocalDateTime updatedat;

}
