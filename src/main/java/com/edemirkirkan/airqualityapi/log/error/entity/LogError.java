package com.edemirkirkan.airqualityapi.log.error.entity;

import com.edemirkirkan.airqualityapi.gen.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOG_ERROR")
@Getter
@Setter
public class LogError extends BaseEntity {
    @Id
    @SequenceGenerator(name = "LogError", sequenceName = "LOG_ERROR_ID_SEQ")
    @GeneratedValue(generator = "LogError")
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
