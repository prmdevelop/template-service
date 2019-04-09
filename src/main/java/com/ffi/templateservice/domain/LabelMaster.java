package com.ffi.templateservice.domain;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "LABEL_MASTER")
public class LabelMaster {

	private UUID id;
	private String labelText;
	private String labelId;
	private String labelType;
	private String labelDesc;
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

	@Column(name = "LABEL_TEXT")
	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}

	@Column(name = "LABEL_ID")
	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	@Column(name = "LABEL_TYPE")
	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	@Column(name = "LABEL_DESC")
	public String getLabelDesc() {
		return labelDesc;
	}

	public void setLabelDesc(String labelDesc) {
		this.labelDesc = labelDesc;
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
