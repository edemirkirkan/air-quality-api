package com.edemirkirkan.airqualityapi.pol.entity;

import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "POL_POLLUTION")
@Getter
@Setter
public class PolPollution extends BaseEntity {
    @Id
    @SequenceGenerator(name = "PolPollution", sequenceName = "POL_POLLUTION_ID_SEQ")
    @GeneratedValue(generator = "PolPollution")
    private Long id;

    @Column(name="DATE", nullable = false)
    private String date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CTY_CITY")
    private CtyCity ctyCity;

    @Column(name = "CO", precision = 25, scale = 20, nullable = false)
    private BigDecimal co;

    @Column(name = "O3", precision = 25, scale = 20, nullable = false)
    private BigDecimal o3;

    @Column(name = "SO2", precision = 25, scale = 20, nullable = false)
    private BigDecimal so2;


}
