package com.izelozarslan.weatherapp.entity;

import com.izelozarslan.weatherapp.general.BaseEntity;
import com.izelozarslan.weatherapp.security.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "CITY")
public class City extends BaseEntity {

    @Id
    @GeneratedValue(generator = "City", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "City", sequenceName = "CITY_ID_SEQ")
    private Long id;

    @NotBlank
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
