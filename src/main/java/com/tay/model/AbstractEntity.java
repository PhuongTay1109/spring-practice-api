package com.tay.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class AbstractEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id")
	private Long id;
	
    @Column(name = "created_at")
    @CreationTimestamp // tự động set current timestamp khi record được tạo
    @Temporal(TemporalType.TIMESTAMP) // chỉ định trường này được coi là timestamp, bao gồm ngày và giờ
    private Date createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp // tự động set current timestamp khi record được update
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
