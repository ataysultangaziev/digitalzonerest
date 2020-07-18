package com.example.digitalzonerest.controllers;

import com.example.digitalzonerest.dto.VisitEventDto;
import com.example.digitalzonerest.dto.VisitEventRequestDto;
import com.example.digitalzonerest.model.VisitEvent;
import com.example.digitalzonerest.service.VisitEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/visit_events/")
@Api(description = "Visit event operations", tags = {"Visit event"})
@Slf4j
public class VisitEventRestControllerV1 {

    private final VisitEventService visitEventService;

    @Autowired
    public VisitEventRestControllerV1(VisitEventService visitEventService) {
        this.visitEventService = visitEventService;
    }

    @PostMapping("/")
    @ApiOperation(value = "Creates new visit event",
            response = VisitEventDto.class
    )
    public VisitEventDto createVisitEvent(@RequestBody VisitEventRequestDto visitEventRequestDto) {

        VisitEvent visitEvent = new VisitEvent();

        VisitEventDto createdVisitEventDto = visitEventService.create(visitEventRequestDto.getUserId(), visitEventRequestDto.getPageId());

        return createdVisitEventDto;
    }

}
