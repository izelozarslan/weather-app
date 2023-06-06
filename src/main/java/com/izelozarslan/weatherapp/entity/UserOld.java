//package com.izelozarslan.weatherapp.entity;
//
//import com.izelozarslan.weatherapp.general.BaseEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "USERS")
//public class User extends BaseEntity {
//
//    @Id
//    @GeneratedValue(generator = "User" ,strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "User", sequenceName = "User_ID_SEQ")
//    private Long id;
//
//    @NotBlank
//    @Size(min=2, max=50, message = "Username must be at least 2 characters")
//    @Column(name = "USERNAME", length =50, nullable = false , unique = true )
//    private String username;
//
//    @NotBlank
//    @Size(min=6, max=400, message = "Password must be at least 2 characters")
//    @Column(name = "PASSWORD", length = 400, nullable = false)
//    private String password;
//
//    @OneToMany(fetch = FetchType.LAZY,  mappedBy = "user")
//    private List<City> cities = new ArrayList<>();
//
//}
