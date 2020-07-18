package com.example.digitalzonerest.service;

import com.example.digitalzonerest.model.Page;

import java.util.List;

public interface PageService {

    Page create(Page page);

    Page findById(Long id);

    List<Page> getAll();

    void delete(Long id);

}
