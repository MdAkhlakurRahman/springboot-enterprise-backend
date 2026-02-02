package com.example.demo.specification;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    private UserSpecification() {
    }

    public static Specification<User> hasName(String name) {
        if (name == null || name.trim().isEmpty())
            return null;
        else
            return (root, query, cb)
                    -> cb.like(cb.lower(root.get("name")),
                        "%"+name.trim().toLowerCase()+"%");
    }
    //LOWER(name) LIKE '%value%'
    public static Specification<User> hasEmail(String email){
        if(email==null || email.trim().isEmpty())
            return null;
        else
            return ((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("email")), "%"+email.trim().toLowerCase()+"%"));
    }
    //WHERE LOWER(email) LIKE '%gmail%'
    public static Specification<User> isActive(Boolean active){
        if(active==null)
            return null;
        else
            return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("active"),active));
    }
    //WHERE active = true;
}

