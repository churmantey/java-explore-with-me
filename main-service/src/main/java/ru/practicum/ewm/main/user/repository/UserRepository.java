package ru.practicum.ewm.main.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main.user.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //@Query("select u from User u where u.id in :ids order by u.id offset :from")
    @Query(value = """
            select u.id, u.name, u.email 
            from users as u 
            where u.id in :ids order by u.id offset :from limit :size
            """, nativeQuery = true)
    List<User> findAllByIds(@Param("ids") Collection<Long> ids,
                                      @Param("from") int from,
                                      @Param("size") int size);

}