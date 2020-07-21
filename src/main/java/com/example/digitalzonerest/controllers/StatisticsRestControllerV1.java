package com.example.digitalzonerest.controllers;


import com.example.digitalzonerest.dto.StatisticsDto;
import com.example.digitalzonerest.model.Page;
import com.example.digitalzonerest.model.VisitEvent;
import com.example.digitalzonerest.service.PageService;
import com.example.digitalzonerest.service.VisitEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/statistics/")
@Api(description = "Statistics operations", tags = {"Statistics"})
@Slf4j
public class StatisticsRestControllerV1 {

    private final PageService pageService;
    private final VisitEventService visitEventService;

    @Autowired
    public StatisticsRestControllerV1(PageService pageService, VisitEventService visitEventService) {
        this.pageService = pageService;
        this.visitEventService = visitEventService;
    }

    @GetMapping
    @ApiOperation(value = "Gets statistics by dateFrom and dateTo",
            notes = "Provide dates in HH:mm:ss-MM/dd/yyyy form",
            response = StatisticsDto.class
    )
    public StatisticsDto getStatistics(@RequestParam(value="dateFrom") String dateFrom, @RequestParam(value="dateTo") String dateTo) throws ParseException {

        StatisticsDto statisticsDto = new StatisticsDto();
        DateFormat df = new SimpleDateFormat("HH:mm:ss-MM/dd/yyyy");
        Date convertedDateFrom = df.parse(dateFrom);
        Date convertedDateTo = df.parse(dateTo);

        //Все визиты которые есть для первого и второго задания
        List<VisitEvent> visitEvents = new ArrayList<>();
        //Все визиты которые есть для третьего задания
        List<VisitEvent> visitEventsTemp = new ArrayList<>();

        //Собираем все визиты со всех страниц
        visitEvents = visitEventService.findAllEventsBetweenDates(convertedDateFrom, convertedDateTo);
        visitEventsTemp = visitEventService.findAllEventsBetweenDates(convertedDateFrom, convertedDateTo);

        //Удаляем все визиты которые были не сегодня
        for (int i = 0; i < visitEvents.size(); i++) {
            if (!(visitEvents.get(i).getCreated().after(convertedDateFrom) && visitEvents.get(i).getCreated().before(convertedDateTo))) {
                visitEvents.remove(i);
                visitEventsTemp.remove(i);
                i--;
            }
        }

        statisticsDto.setGeneralVisitsQuantity(visitEvents.size());

        //Удаляем все визиты у которых юзеры повторяются
        for (int i = 0; i < visitEvents.size(); i++) {
            for (int j = 0; j < visitEvents.size(); j++) {
                if (j == i) continue;
                if (visitEvents.get(j).getUser().getId() == visitEvents.get(i).getUser().getId()) {
                    visitEvents.remove(j);
                    j--;
                }
            }
        }

        statisticsDto.setUniqueUsersQuantity(visitEvents.size());

        int counter = 0;
        for (int i = 0; i < visitEventsTemp.size(); i++) {
            //Временный массив для сохранения визитов от одного пользователя
            List<VisitEvent> temp = new ArrayList<>();

            //Добавляем во временный массив визиоты от одного пользователя
            for (int j = 0; j < visitEventsTemp.size(); j++) {
                if (i == j) continue;
                if (visitEventsTemp.get(i).getUser().getId() == visitEventsTemp.get(j).getUser().getId()) {
                    temp.add(visitEventsTemp.get(j));
                    visitEventsTemp.remove(j);
                    j--;
                }
            }
            temp.add(visitEventsTemp.get(i));
            visitEventsTemp.remove(i);
            i--;

            //Удаляем повторяющиеся страницы
            for (int k = 0; k < temp.size(); k++) {
                for (int f = 0; f < temp.size(); f++) {
                    if (k == f) continue;
                    if (temp.get(k).getPage().getId() == temp.get(f).getPage().getId()) {
                        temp.remove(f);
                        f--;
                    }
                }
            }

            //Если больше 10-ти страниц то ++
            if (temp.size() >= 10) {
                counter++;
            }

        }

        statisticsDto.setConstantUsersQuantity(counter);

        return statisticsDto;
    }

}
