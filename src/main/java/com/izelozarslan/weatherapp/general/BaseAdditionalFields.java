package com.izelozarslan.weatherapp.general;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class BaseAdditionalFields {

    @Column(name = "ID_USER_CREATED_BY")
    private  String createdBy;

    @Column(name = "ID_USER_UPDATED_BY")
    private  String updatedBy;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
