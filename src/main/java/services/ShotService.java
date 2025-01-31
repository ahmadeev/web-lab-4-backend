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
import lombok.Getter;
import objects.*;
import jakarta.persistence.Query;
import utils.AreaCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Named(value = "shotService")
@ApplicationScoped
public class ShotService {

    @PersistenceContext
    protected EntityManager em;

    @Inject
    private AreaCheck areaCheck;

    @Getter
    private final HashMap<Long, Long> userShotsCountStore = new HashMap<>();

    @PostConstruct
    private void init() {
        System.out.println("ShotService initialized");
    }

    @Transactional
    public List<Shot> createUserShot(List<Shot> shots, long userId) {
        List<Shot> result = new ArrayList<>();
        for(Shot shot : shots) {
            long startTime = System.nanoTime();
            shot.setHit(areaCheck.isHit(shot.getX(), shot.getY(), shot.getR()));
            shot.setOwnerId(userId);
            shot.setScriptTime((System.nanoTime() - startTime) / 1000);
            result.add(shot);
            em.persist(shot);
        }
        if (userShotsCountStore.containsKey(userId)) {
            userShotsCountStore.put(userId, userShotsCountStore.get(userId) + result.size());
        } else {
            userShotsCountStore.put(userId, (long) result.size());
        }
        return result;
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

    @Transactional
    public List<Shot> getAllUserShots(long userId) {
        String jpql = "SELECT i FROM Shot i WHERE i.ownerId = :userId";
        TypedQuery<Shot> query = em.createQuery(jpql, Shot.class);
        return query
                .setParameter("userId", userId)
                .getResultList();
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
    public long deleteUserShots(long userId) {
        String jpql = "DELETE FROM Shot o WHERE o.ownerId = :userId";
        Query query = em.createQuery(jpql);
        query.setParameter("userId", userId);

        long count = query.executeUpdate();
        userShotsCountStore.remove(userId);

        return count;
    }

    @Transactional
    public long getShotsCount(long userId) {
        // .executeUpdate() для INSERT, UPDATE, DELETE

        if (userShotsCountStore.containsKey(userId)) {
            System.out.printf("Avoided redundant database query. %d rows stored.\n", userShotsCountStore.get(userId));
            return userShotsCountStore.get(userId);
        }

        long count = em
                .createQuery("SELECT COUNT(i) FROM Shot i WHERE i.ownerId = :userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();

        userShotsCountStore.put(userId, count);
        System.out.printf("Executed database query and saved number of rows (stored %d rows)\n", count);

        return count;
    }

    public List<Shot> createEntityFromDTO(ShotDTO dto) {
        List<Shot> result = new ArrayList<>();
        for(double x : dto.getX()) {
            for(double r : dto.getR()) {
                result.add(new Shot(x, (dto.getY()).doubleValue(), r));
            }
        }
        return result;
    }
}
