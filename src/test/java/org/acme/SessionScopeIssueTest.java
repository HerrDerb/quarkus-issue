package org.acme;

import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SessionScopeIssueTest {
    @Inject
    ThatRepository thatRepository;
    @Inject
    ThisRepository thisRepository;

    @BeforeEach
    public void setup() {
        QuarkusTransaction.begin();
        thatRepository.deleteAll();
        thisRepository.deleteAll();
        QuarkusTransaction.commit();
    }

    @Test
    public void changeIdOfDetachedAndUpdateForeignKey() {
        QuarkusTransaction.requiringNew().run(() -> {
            ThisEntity thisEntity = new ThisEntity();
            thisEntity.name = "This";
            thisRepository.persist(thisEntity);
            ThatEntity thatEntity = new ThatEntity();
            thatRepository.persist(thatEntity);
        });
        QuarkusTransaction.requiringNew().run(() -> {
            var thisEntity = thisRepository.findById("This");
            thisRepository.getEntityManager().detach(thisEntity);
            thisEntity.name = "That";
            thisRepository.persist(thisEntity);

            thatRepository.updateThisEntity(thisEntity);
        });
    }

    @Test
    public void changeIdOfDetachedFlushingAndUpdateForeignKey() {
        QuarkusTransaction.requiringNew().run(() -> {
            ThisEntity thisEntity = new ThisEntity();
            thisEntity.name = "This";
            thisRepository.persist(thisEntity);
            ThatEntity thatEntity = new ThatEntity();
            thatRepository.persist(thatEntity);
        });
        QuarkusTransaction.requiringNew().run(() -> {
            var thisEntity = thisRepository.findById("This");
            thisRepository.getEntityManager().detach(thisEntity);
            thisEntity.name = "That";
            thisRepository.persistAndFlush(thisEntity);

            thatRepository.updateThisEntity(thisEntity);
        });
    }

    @Test
    public void changeIdAndUpdateForeignKey() {
        QuarkusTransaction.requiringNew().run(() -> {
            ThisEntity thisEntity = new ThisEntity();
            thisEntity.name = "This";
            thisRepository.persist(thisEntity);
            ThatEntity thatEntity = new ThatEntity();
            thatRepository.persist(thatEntity);
        });
        QuarkusTransaction.requiringNew().run(() -> {
            var thisEntity = thisRepository.findById("This");
            thisEntity.name = "That";

            thatRepository.updateThisEntity(thisEntity);
        });
    }

}
