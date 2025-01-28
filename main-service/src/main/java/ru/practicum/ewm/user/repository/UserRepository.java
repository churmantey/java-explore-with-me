package ru.practicum.ewm.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            select *
            from users as u
            where u.id in :ids order by u.id offset :from limit :size
            """, nativeQuery = true)
    List<User> findAllByIds(@Param("ids") Collection<Long> ids,
                                      @Param("from") int from,
                                      @Param("size") int size);

    @Query(value = """
            select *
            from users as u
            order by u.id offset :from limit :size
            """, nativeQuery = true)
    List<User> findUsers(@Param("from") int from,
                         @Param("size") int size);

    boolean existsByEmailIgnoreCase(String email);
}