package com.test.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    @CreatedBy
    @Column(name = "createdby", updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "createdat", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
 
    @LastModifiedBy
    @Column(name = "modifiedby")
    private String lastModifiedBy;
 
    @LastModifiedDate
    @Column(name = "modifiedat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
}
