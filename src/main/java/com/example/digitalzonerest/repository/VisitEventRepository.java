package com.example.digitalzonerest.repository;

import com.example.digitalzonerest.model.VisitEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitEventRepository extends JpaRepository<VisitEvent, Long>, VisitEventRepositoryCustom {
}
