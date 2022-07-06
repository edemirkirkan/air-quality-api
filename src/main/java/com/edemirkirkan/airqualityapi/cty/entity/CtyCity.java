package com.edemirkirkan.airqualityapi.cty.entity;

import com.edemirkirkan.airqualityapi.gen.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CTY_CITY")
@Getter
@Setter
public class CtyCity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "CtyCity", sequenceName = "CTY_CITY_ID_SEQ")
    @GeneratedValue(generator = "CtyCity")
    private Long id;

    @Column(name = "NAME", length = 30, nullable = false, unique = true)
    private String name;

    @Column(name = "LATITUDE", precision = 7, scale = 4, nullable = false)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 7, scale = 4, nullable = false)
    private BigDecimal longitude;

    @Column(name = "COUNTRY_CODE", nullable = false)
    private String countryCode;
}
