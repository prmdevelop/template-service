package com.ffi.templateservice.domain;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "TEMPLATE_DETAILS")
public class TemplateDetails {
	
	private UUID id;
	private TemplateSection templateSection;
	private LabelMaster labelMaster;
	private UUID createdBy;
	private UUID approvedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isExpired;
	
	@Id
	@Type(type = "org.hibernate.type.UUIDCharType")
	@Column(name = "ID")
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TEMP_SECTION_ID")
	public TemplateSection getTemplateSection() {
		return templateSection;
	}
	public void setTemplateSection(TemplateSection templateSection) {
		this.templateSection = templateSection;
	}
	
	@ManyToOne
	@JoinColumn(name = "LABEL_MASTER_ID")
	public LabelMaster getLabelMaster() {
		return labelMaster;
	}
	public void setLabelMaster(LabelMaster labelMaster) {
		this.labelMaster = labelMaster;
	}
	
	@Column(name = "CREATED_BY")
	public UUID getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name = "APPROVED_BY")
	public UUID getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(UUID approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "LAST_MODIFIED_DATE")
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	@Column(name = "ISEXPIRED")
	public boolean isExpired() {
		return isExpired;
	}
	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}
}
