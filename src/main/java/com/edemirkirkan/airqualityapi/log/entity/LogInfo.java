package com.edemirkirkan.airqualityapi.log.entity;

import com.edemirkirkan.airqualityapi.gen.base.BaseEntity;
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
    @Column(length = 50)
    private HttpStatus httpStatus;

    @Column(name = "BODY", length = 4000)
    private String body;

    @Column(name = "HEADERS", length = 4000)
    private String headers;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERROR_DATE")
    private Date errorDate;
}
