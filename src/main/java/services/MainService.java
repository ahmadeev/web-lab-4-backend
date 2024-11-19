package services;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import objects.Dragon;

@Named(value = "mainService")
@ApplicationScoped
public class MainService {

    @PostConstruct
    private void init() {
        System.out.println("MainService initialized");
    }

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

    @Transactional
    public boolean deleteDragonById(long id) {
        Dragon dragon = em.find(Dragon.class, id);
        if (dragon != null) {
            em.remove(dragon);
            return true;
        }
        return false; // Если дракон с таким id не найден
    }
}
