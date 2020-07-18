package com.example.digitalzonerest.controllers;

import com.example.digitalzonerest.dto.PageDto;
import com.example.digitalzonerest.dto.PageRequestDto;
import com.example.digitalzonerest.model.Page;
import com.example.digitalzonerest.service.PageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/pages/")
@Api(description = "Page operations", tags = {"Page"})
@Slf4j
public class PageRestControllerV1 {

    private final PageService pageService;

    @Autowired
    public PageRestControllerV1(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping("/")
    @ApiOperation(value = "Creates new page",
            response = PageDto.class
    )
    public PageDto createPage(@RequestBody PageRequestDto pageRequestDto) {

        Page page = new Page();

        try {
            page = pageRequestDto.toPage();
        } catch (ParseException e) {
            log.info("ParseException");
        }

        Page createdPage = pageService.create(page);

        PageDto pageDto = PageDto.fromPage(createdPage);

        return pageDto;
    }

    @GetMapping
    @ApiOperation(value = "Finds all pages",
            notes = "All pages of website",
            response = PageDto.class
    )
    public ResponseEntity<List<PageDto>> getAllPages(){

        List<Page> pages = pageService.getAll();

        if(pages == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<PageDto> pageDtos = new ArrayList<PageDto>();

        for(int i = 0; i < pages.size(); i++) {
            PageDto result = PageDto.fromPage(pages.get(i));
            pageDtos.add(result);
        }

        return new ResponseEntity<>(pageDtos, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePage(@PathVariable(name = "id") Long id) {

        Page page = pageService.findById(id);

        pageService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
