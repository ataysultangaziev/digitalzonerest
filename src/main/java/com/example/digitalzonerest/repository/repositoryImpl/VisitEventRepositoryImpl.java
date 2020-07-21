package com.example.digitalzonerest.repository.repositoryImpl;

import com.example.digitalzonerest.model.VisitEvent;
import com.example.digitalzonerest.repository.VisitEventRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VisitEventRepositoryImpl implements VisitEventRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<VisitEvent> getVisitEventsBetween(Date dateFrom, Date dateTo) {
        Query query = entityManager.createNativeQuery("SELECT * FROM visit_events WHERE created BETWEEN CAST(:dateFrom as timestamp) AND CAST(:dateTo as timestamp)", VisitEvent.class);
        DateFormat dateDF = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeDF = new SimpleDateFormat("HH:mm:ss");
        query.setParameter("dateFrom", dateDF.format(dateFrom) + " " + timeDF.format(dateFrom))
                .setParameter("dateTo", dateDF.format(dateTo) + " " + timeDF.format(dateTo))
                .getResultList();
        return query.getResultList();
    }

}
