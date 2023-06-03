package com.izelozarslan.weatherapp.general;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable, BaseEntityModel {

    @Embedded
    private BaseAdditionalFields baseAdditionalFields;
}
