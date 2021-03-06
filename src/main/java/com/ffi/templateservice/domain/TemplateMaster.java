package com.ffi.templateservice.domain;

import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	private UUID createdBy;
	private UUID approvedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private boolean isExpired;
	private Set<TemplateSection> templateSection;
	
	public TemplateMaster() {
		super();
	}

	@Id
	@Type(type = "org.hibernate.type.UUIDCharType")
	@Column(name="ID")
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Column(name = "TEMP_NAME")
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Column(name = "TEMP_VERSION")
	public String getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}

	@Column(name = "TEMP_TYPE")
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	@Column(name = "DEF_NO_YEARS")
	public int getDefaultNoOfYear() {
		return defaultNoOfYear;
	}

	public void setDefaultNoOfYear(int defaultNoOfYear) {
		this.defaultNoOfYear = defaultNoOfYear;
	}

	@Column(name = "MAX_NO_YEARS")
	public int getMaxNoOfYear() {
		return maxNoOfYear;
	}

	public void setMaxNoOfYear(int maxNoOfYear) {
		this.maxNoOfYear = maxNoOfYear;
	}

	@Column(name = "MIN_NO_YEARS")
	public int getMinNoOfYear() {
		return minNoOfYear;
	}

	public void setMinNoOfYear(int minNoOfYear) {
		this.minNoOfYear = minNoOfYear;
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
	
	@JsonManagedReference
	@OneToMany(mappedBy = "templateMaster", cascade = CascadeType.ALL)
	public Set<TemplateSection> getTemplateSection() {
		return templateSection;
	}

	public void setTemplateSection(Set<TemplateSection> templateSection) {
		this.templateSection = templateSection;
	}

	@Override
	public String toString() {
		return "TemplateMaster [id=" + id + ", templateName=" + templateName + ", templateVersion=" + templateVersion
				+ ", templateType=" + templateType + ", defaultNoOfYear=" + defaultNoOfYear + ", maxNoOfYear="
				+ maxNoOfYear + ", minNoOfYear=" + minNoOfYear + ", createdBy=" + createdBy + ", approvedBy="
				+ approvedBy + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
				+ ", isExpired=" + isExpired + ", templateSection=" + templateSection + "]";
	}
}
