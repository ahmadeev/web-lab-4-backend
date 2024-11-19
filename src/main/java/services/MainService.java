package services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import objects.Dragon;

@Named(value = "mainService")
@ApplicationScoped
public class MainService {
    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public void createDragon(Dragon dragon) {
        em.persist(dragon);
    }

    @Transactional
    public Dragon getDragonById(long id) {
        return em.find(Dragon.class, id);
    }
}
