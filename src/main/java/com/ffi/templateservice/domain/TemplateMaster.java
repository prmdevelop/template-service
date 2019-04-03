package com.ffi.templateservice.domain;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "TEMPLATE_MASTER")
public class TemplateMaster {
	private UUID id;
	private String templateName;
	private String templateVersion;
	private String templateType;
	private int defaultNoOfYear;
	private int maxNoOfYear;
	private int minNoOfYear;
	private String createdBy;
	private String approvedBy;
	private Date createdDate;
	private Date lastModDate;
	private boolean isExpired;

	@Id
	@Type(type = "org.hibernate.type.UUIDCharType")
	@Column(name="ID")
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Column(name = "TEMPLATE_NAME")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "TEMPLATE_VERSION")
	public String getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}

	@Column(name = "TEMPLATE_TYPE")
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	@Column(name = "DEFAULT_NO_OF_YEAR")
	public int getDefaultNoOfYear() {
		return defaultNoOfYear;
	}

	public void setDefaultNoOfYear(int defaultNoOfYear) {
		this.defaultNoOfYear = defaultNoOfYear;
	}

	@Column(name = "MAX_NO_OF_YEAR")
	public int getMaxNoOfYear() {
		return maxNoOfYear;
	}

	public void setMaxNoOfYear(int maxNoOfYear) {
		this.maxNoOfYear = maxNoOfYear;
	}

	@Column(name = "MIN_NO_OF_YEAR")
	public int getMinNoOfYear() {
		return minNoOfYear;
	}

	public void setMinNoOfYear(int minNoOfYear) {
		this.minNoOfYear = minNoOfYear;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
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
	public Date getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}

	@Column(name = "IS_EXPIRED")
	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	@Override
	public String toString() {
		return "TemplateMaster [id=" + id + ", templateName=" + templateName + ", templateVersion=" + templateVersion
				+ ", templateType=" + templateType + ", defaultNoOfYear=" + defaultNoOfYear + ", maxNoOfYear="
				+ maxNoOfYear + ", minNoOfYear=" + minNoOfYear + ", createdBy=" + createdBy + ", approvedBy="
				+ approvedBy + ", createdDate=" + createdDate + ", lastModDate=" + lastModDate + ", isExpired="
				+ isExpired + "]";
	}

}
