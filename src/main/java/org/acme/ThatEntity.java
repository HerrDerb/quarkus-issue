package org.acme;

import jakarta.persistence.*;

@Table
@Entity
public class ThatEntity {

    @Id
    private long id;

    @ManyToOne
    private ThisEntity thisEntity;
}
