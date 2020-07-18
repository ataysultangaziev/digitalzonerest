package com.example.digitalzonerest.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pages")
@Data
public class Page extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VisitEvent> visitEvents;

    public void addVisit(VisitEvent visitEvent) {
        visitEvents.add(visitEvent);
    }

}
