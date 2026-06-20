package com.example.repository;

import com.example.dto.UserRankingDto;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("""
        select new com.example.dto.UserRankingDto(
            u.name,
            s.points
        )
        from User u
        join u.score s
        order by s.points desc
    """)
    List<UserRankingDto> findRanking();

    Optional<User> findByEmail(String email);

}
