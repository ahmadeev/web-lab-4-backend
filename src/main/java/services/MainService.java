package services;

import dto.DragonDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import objects.*;

@Named(value = "mainService")
@ApplicationScoped
public class MainService {

    @PersistenceContext
    protected EntityManager em;

    @PostConstruct
    private void init() {
        System.out.println("MainService initialized");
    }

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

    public Dragon createEntityFromDTO(DragonDTO dto) {
        var coordinates = dto.getCoordinates();
        var cave = dto.getCave();
        var killer = dto.getKiller();
        var location = killer.getLocation();
        var head = dto.getHead();

        return new Dragon(
                dto.getName(),
                new Coordinates(
                        coordinates.getX(),
                        coordinates.getY()
                ),
                dto.getCreationDate(),
                new DragonCave(
                        cave.getNumberOfTreasures()
                ),
                new Person(
                        killer.getName(),
                        killer.getEyeColor(),
                        killer.getHairColor(),
                        new Location(
                                location.getX(),
                                location.getY(),
                                location.getZ()
                        ),
                        killer.getBirthday(),
                        killer.getHeight()
                ),
                dto.getAge(),
                dto.getDescription(),
                dto.getWingspan(),
                dto.getCharacter(),
                new DragonHead(
                        head.getEyesCount(),
                        head.getToothCount()
                )
        );
    }
}
