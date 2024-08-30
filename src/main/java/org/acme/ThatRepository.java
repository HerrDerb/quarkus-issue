package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ThatRepository implements PanacheRepository<ThatEntity> {

    public void updateThisEntity(ThisEntity thisEntity) {
        update("thisEntity = ?1", thisEntity);
    }
}
