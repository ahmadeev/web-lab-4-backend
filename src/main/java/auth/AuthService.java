package auth;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

@Named(value = "authService")
@ApplicationScoped
public class AuthService {
    @PersistenceContext
    protected EntityManager em;

    @PostConstruct
    private void init() {
        System.out.println("AuthService initialized");
    }

    @Transactional
    public void createUser(User user) {
        em.persist(user);
    }

    @Transactional
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Transactional
    public User getUserByName(String name) {
        String jpql = "SELECT o FROM User o WHERE o.name = :name";
        Query query = em.createQuery(jpql, User.class);
        query.setParameter("name", name);
        List<User> users = query.getResultList();
        if (users.isEmpty()) return null;
        return users.get(0);
    }

    public User createEntityFromDTO(UserDTO userDTO) {
        return new User(
            userDTO.getName(),
            userDTO.getPassword()
        );
    }
}
