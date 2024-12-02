package auth;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@Named(value = "authService")
@ApplicationScoped
public class AuthService {
    private static final String SALT = "salt";

    @PersistenceContext
    protected EntityManager em;

    @PostConstruct
    private void init() {
        System.out.println("AuthService initialized");
    }

    @Transactional
    public String createUser(User user) {
        // если нет роли админа, то создаем пользователя
        if (!user.getRole().equals(Roles.ADMIN)) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            em.persist(user);
            return "User successfully signed up";
        }

        // если роль админа есть и админы в системе существуют, создаем заявку
        if (this.getAdminsList() != null && !this.getAdminsList().isEmpty()) {
            em.persist(new AdminRequest(
                    user.getName(),
                    BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()),
                    user.getRole()
            ));
            return "Successfully requested admin rights";
        }

        // если роль админа есть и админов в системе нет, создаем админа
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        em.persist(user);
        return "Admin successfully signed up";
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

    @Transactional
    public List<User> getAdminsList() {
        String jpql = "SELECT o FROM User o WHERE o.role = :role";
        Query query = em.createQuery(jpql, User.class);
        query.setParameter("role", Roles.ADMIN);
        List<User> admins = query.getResultList();
        if (admins.isEmpty()) return null;
        return admins;
    }

    public User createEntityFromDTO(UserDTO userDTO) {
        return new User(
            userDTO.getName(),
            userDTO.getPassword(),
            userDTO.getIsAdmin().equals("true") ? Roles.ADMIN : Roles.USER
        );
    }
}
