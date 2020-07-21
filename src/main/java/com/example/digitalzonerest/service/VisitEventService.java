package com.example.digitalzonerest.service;

import com.example.digitalzonerest.dto.VisitEventDto;
import com.example.digitalzonerest.model.VisitEvent;

import java.util.Date;
import java.util.List;

public interface VisitEventService {

    VisitEventDto create(Long userId, Long pageId);
    List<VisitEvent> findAllEventsBetweenDates(Date dateFrom, Date dateTo);

}
