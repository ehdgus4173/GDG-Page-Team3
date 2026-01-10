package com.example.backend.member.repository;

import com.example.backend.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    Page<User> findByGeneration(Integer generation, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    @Query("select u from User u " +
            "where (:generation is null or u.generation = :generation) " +
            "and (:partsEmpty = true or lower(u.part) in :parts)")
    Page<User> searchMembers(@Param("generation") Integer generation,
                             @Param("parts") java.util.List<String> parts,
                             @Param("partsEmpty") boolean partsEmpty,
                             Pageable pageable);
}
