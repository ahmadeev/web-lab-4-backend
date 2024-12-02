package services;

import auth.AdminRequest;
import auth.Roles;
import auth.User;
import auth.UserDTO;
import dto.DragonDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import objects.*;

import java.util.List;

@Named(value = "adminService")
@ApplicationScoped
public class AdminService {

    @PersistenceContext
    protected EntityManager em;

    @PostConstruct
    private void init() {
        System.out.println("AdminService initialized");
    }

    @Transactional
    public void createUser(long userId) {
        AdminRequest adminRequest = em.find(AdminRequest.class, userId);
        if (adminRequest == null) return;
        try {
            em.persist(new User(
                    adminRequest.getName(),
                    adminRequest.getPassword(),
                    adminRequest.getRole()
            ));
            deleteUserById(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public AdminRequest getUserById(long id) {
        return em.find(AdminRequest.class, id);
    }

    @Transactional
    public List<AdminRequest> getUserList(int page, int size) {
        return em.createQuery("SELECT i FROM AdminRequest i", AdminRequest.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Transactional
    public boolean deleteUserById(long id) {
        AdminRequest adminRequest = em.find(AdminRequest.class, id);

        if (adminRequest == null) return false;

        em.remove(adminRequest);
        return true;
    }

    public AdminRequest createEntityFromDTO(UserDTO userDTO) {
        return new AdminRequest(
                userDTO.getName(),
                userDTO.getPassword(),
                userDTO.getIsAdmin().equals("true") ? Roles.ADMIN : Roles.USER
        );
    }
}

