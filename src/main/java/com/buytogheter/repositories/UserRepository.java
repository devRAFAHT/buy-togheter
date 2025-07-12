package com.buytogheter.repositories;

import com.buytogheter.models.User;
import com.buytogheter.repositories.projections.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u.role from User u where u.username like :username")
    User.Role findRoleByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String name);
    Page<UserProjection> findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(String namePart, String usernamePart, Pageable pageable);

}
