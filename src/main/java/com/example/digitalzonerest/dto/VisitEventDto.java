package com.example.digitalzonerest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitEventDto {

    private Integer generalVisitsQuantity;
    private Integer uniqueUsersQuantity;

}
