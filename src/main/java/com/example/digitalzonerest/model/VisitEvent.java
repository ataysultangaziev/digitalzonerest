package com.example.digitalzonerest.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "visit_events")
@Data
public class VisitEvent extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    private Page page;

}
