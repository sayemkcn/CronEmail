package net.toracode.app.repository;

import net.toracode.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sayemkcn on 2/16/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
