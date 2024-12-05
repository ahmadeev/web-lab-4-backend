package services;

import dto.ShotDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import objects.*;
import jakarta.persistence.Query;
import utils.AreaCheck;

import java.util.List;

@Named(value = "shotService")
@ApplicationScoped
public class ShotService {

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private AreaCheck areaCheck;

    @PostConstruct
    private void init() {
        System.out.println("ShotService initialized");
    }

    @Transactional
    public void createUserShot(Shot shot, long userId) {
        long startTime = System.nanoTime();
        shot.setHit(areaCheck.isHit(shot.getX(), shot.getY(), shot.getR()));
        shot.setOwnerId(userId);
        shot.setScriptTime((System.nanoTime() - startTime) / 1000);
        em.persist(shot);
    }

    @Transactional
    public Shot getShotById(long id) {
        return em.find(Shot.class, id);
    }

    @Transactional
    public List<Shot> getShots(int page, int size) {
        return em.createQuery("SELECT i FROM Shot i", Shot.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public List<Shot> getUserShots(int page, int size, long userId) {
        String jpql = "SELECT i FROM Shot i WHERE i.ownerId = :userId";
        TypedQuery<Shot> query = em.createQuery(jpql, Shot.class);
        return query
                .setParameter("userId", userId)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

    }

    //  пока не готово
    @Transactional
    public boolean updateUserShotById(long id, long userId, ShotDTO shotDTO) {
        Shot shot = em.find(Shot.class, id);

        if (shot == null) return false;
        if (shot.getOwnerId() != userId) return false;

        // Обновление полей
//        shot.setName(shotDTO.getName());
//        shot.setAge(shotDTO.getAge());
//        shot.setWingspan(shotDTO.getWingspan());
//        shot.setDescription(shotDTO.getDescription());

        return true;
    }

    @Transactional
    public boolean deleteUserShotById(long id, long userId) {
        Shot shot = em.find(Shot.class, id);

        if (shot == null) return false;
        if (shot.getOwnerId() != userId) return false;

        em.remove(shot);
        return true;
    }

    @Transactional
    public int deleteUserShots(long userId) {
        String jpql = "DELETE FROM Shot o WHERE o.ownerId = :userId";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", userId);
        return query.executeUpdate();
    }

    public Shot createEntityFromDTO(ShotDTO dto) {
        return new Shot(
            dto.getX(),
            dto.getY(),
            dto.getR()
        );
    }
}
