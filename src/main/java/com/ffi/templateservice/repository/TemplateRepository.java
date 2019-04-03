package com.ffi.templateservice.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ffi.templateservice.domain.TemplateMaster;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateMaster, String> {
	
	@Query(value = "select f from TemplateMaster f where f.templateName like %:search%")
	List<TemplateMaster> getTemplate(@Param("search") String search);
}
