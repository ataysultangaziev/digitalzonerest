package com.example.digitalzonerest.dto;

import com.example.digitalzonerest.model.Page;
import lombok.Data;

import java.text.ParseException;
import java.util.Date;

@Data
public class PageRequestDto {

    private String name;

    public Page toPage() throws ParseException {

        Page page = new Page();
        page.setName(name);

        page.setCreated(new Date());
        page.setUpdated(new Date());

        return page;
    }

}
