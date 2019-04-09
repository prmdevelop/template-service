package com.ffi.templateservice.domain;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "TEMPLATE_SECTION")
public class TemplateSection {

	private UUID id;
	private String sectionName;
	private TemplateMaster templateMaster;
	private Set<TemplateDetails> templateDetails;
	
	public TemplateSection() {
		super();
	}
	
	@Id
	@Type(type = "org.hibernate.type.UUIDCharType")
	@Column(name = "ID")
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	@Column(name = "SECTION_NAME")
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TEMP_MASTER_ID")
	public TemplateMaster getTemplateMaster() {
		return templateMaster;
	}
	public void setTemplateMaster(TemplateMaster templateMaster) {
		this.templateMaster = templateMaster;
	}
	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "templateSection", cascade = CascadeType.ALL)
	public Set<TemplateDetails> getTemplateDetails() {
		return templateDetails;
	}
	public void setTemplateDetails(Set<TemplateDetails> templateDetails) {
		this.templateDetails = templateDetails;
	}
	
}
