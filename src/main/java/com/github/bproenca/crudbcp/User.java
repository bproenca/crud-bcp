package com.github.bproenca.crudbcp;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String phone;
    
    @Column(nullable = true)
    private String gender;
    @Column(nullable = true)
    private Integer age;
    @Column(nullable = true)
    private Integer height;
    @Column(nullable = true, precision = 8, scale = 2)
    private BigDecimal weight;
}