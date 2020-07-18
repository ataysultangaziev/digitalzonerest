package com.example.digitalzonerest.service.impl;

import com.example.digitalzonerest.model.Page;
import com.example.digitalzonerest.model.Status;
import com.example.digitalzonerest.repository.PageRepository;
import com.example.digitalzonerest.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Autowired
    public PageServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    public Page create(Page page) {

        page.setStatus(Status.ACTIVE);

        Page createdPage = pageRepository.save(page);

        log.info("IN create - page: {} successfully registered", page.getName());

        return createdPage;
    }

    @Override
    public List<Page> getAll() {
        List<Page> result = pageRepository.findAll();
        log.info("IN getAll - {} pages found", result.size());
        return result;
    }

    @Override
    public Page findById(Long id) {
        Page result = pageRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no page found by id: {}", id);
            return null;
        }

        log.info("IN findById - page: {} found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        pageRepository.deleteById(id);
        log.info("IN delete - page with id: {} successfully deleted");
    }

}
