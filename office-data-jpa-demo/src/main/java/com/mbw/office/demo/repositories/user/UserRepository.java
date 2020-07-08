package com.mbw.office.demo.repositories.user;

import com.mbw.office.demo.entity.user.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mabowen
 * @date 2020-07-08 15:09
 */
@Repository
public interface UserRepository extends JpaRepository<UserPO, Long> {
}
