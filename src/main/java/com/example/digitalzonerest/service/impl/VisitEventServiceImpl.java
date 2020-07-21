package com.example.digitalzonerest.service.impl;

import com.example.digitalzonerest.dto.VisitEventDto;
import com.example.digitalzonerest.model.Page;
import com.example.digitalzonerest.model.Status;
import com.example.digitalzonerest.model.User;
import com.example.digitalzonerest.model.VisitEvent;
import com.example.digitalzonerest.repository.PageRepository;
import com.example.digitalzonerest.repository.UserRepository;
import com.example.digitalzonerest.repository.VisitEventRepository;
import com.example.digitalzonerest.service.VisitEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class VisitEventServiceImpl implements VisitEventService {

    private final VisitEventRepository visitEventRepository;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;

    @Autowired
    public VisitEventServiceImpl(VisitEventRepository visitEventRepository, UserRepository userRepository, PageRepository pageRepository) {
        this.visitEventRepository = visitEventRepository;
        this.userRepository = userRepository;
        this.pageRepository = pageRepository;
    }

    @Override
    public VisitEventDto create(Long userId, Long pageId) {

        VisitEventDto visitEventDto = new VisitEventDto();

        User user = userRepository.findById(userId).orElse(null);
        Page page = pageRepository.findById(pageId).orElse(null);

        VisitEvent visitEvent = new VisitEvent();

        visitEvent.setCreated(new Date());
        visitEvent.setUpdated(new Date());
        visitEvent.setStatus(Status.ACTIVE);
        visitEvent.setUser(user);
        visitEvent.setPage(page);

        page.addVisit(visitEvent);

        Page savedPage = pageRepository.save(page);

        log.info("IN create - visit event:  successfully created");

        List<VisitEvent> visitEvents = new ArrayList<VisitEvent>();

        visitEvents = savedPage.getVisitEvents();

        //Чистим лист от событий которые произошли не сегодня
        for (int i = 0; i < visitEvents.size(); i++) {
            if (!DateUtils.isToday(visitEvents.get(i).getCreated())) {
                visitEvents.remove(i);
                i--;
            }
        }

        visitEventDto.setGeneralVisitsQuantity(visitEvents.size());

        //Чистим лист от событий у которых user_id повторяется
        for (int i = 0; i < visitEvents.size(); i++) {
            for (int j = 0; j < visitEvents.size(); j++) {
                if (j == i) continue;
                if (visitEvents.get(j).getUser().getId() == visitEvents.get(i).getUser().getId()) {
                    visitEvents.remove(j);
                    j--;
                }
            }
        }

        visitEventDto.setUniqueUsersQuantity(visitEvents.size());

        return visitEventDto;
    }

    @Override
    public List<VisitEvent> findAllEventsBetweenDates(Date dateFrom, Date dateTo) {
        List<VisitEvent> visitEvents = visitEventRepository.getVisitEventsBetween(dateFrom, dateTo);
        return  visitEvents;
    }

}
