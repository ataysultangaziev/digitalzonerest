package com.example.digitalzonerest.repository;

import com.example.digitalzonerest.model.VisitEvent;

import java.util.Date;
import java.util.List;

public interface VisitEventRepositoryCustom {
    List<VisitEvent> getVisitEventsBetween(Date dateFrom, Date dateTo);
}
