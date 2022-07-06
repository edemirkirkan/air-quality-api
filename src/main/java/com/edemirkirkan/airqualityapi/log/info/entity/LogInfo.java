package com.edemirkirkan.airqualityapi.log.info.entity;

import com.edemirkirkan.airqualityapi.cty.entity.CtyCity;
import com.edemirkirkan.airqualityapi.gen.base.BaseEntity;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoActionType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoDataSourceType;
import com.edemirkirkan.airqualityapi.log.info.enums.EnumLogInfoEntityType;
import com.edemirkirkan.airqualityapi.pol.entity.PolPollution;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOG_INFO")
@Getter
@Setter
public class LogInfo extends BaseEntity {
    @Id
    @SequenceGenerator(name = "LogInfo", sequenceName = "LOG_INFO_ID_SEQ")
    @GeneratedValue(generator = "LogInfo")
    private Long id;

    @Column(name = "ID_USR_USER")
    private Long usrUserId;

    @Enumerated(EnumType.STRING)
    @Column(name="ENTITY", length = 20)
    private EnumLogInfoEntityType entity;

    @Enumerated(EnumType.STRING)
    @Column(name="TYPE", length = 20)
    private EnumLogInfoActionType action;

    @Enumerated(EnumType.STRING)
    @Column(name="SOURCE", length = 20)
    private EnumLogInfoDataSourceType source;

    @Column(name="CTY_CITY_NAME", length = 30)
    private String ctyCityName;

    @Column(name="POL_POLLUTION_DATE", length = 20)
    private String polPollutionDate;
}
