package com.example.digitalzonerest.dto;

import com.example.digitalzonerest.model.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDto {

    private Long id;
    private String name;

    public static PageDto fromPage(Page page) {

        PageDto pageDto = new PageDto();
        pageDto.setId(page.getId());
        pageDto.setName(page.getName());

        return pageDto;
    }

}
