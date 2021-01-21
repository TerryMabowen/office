package com.mbw.office.cloud.common.lang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mabowen
 * @date 2020-07-02 15:05
 */
public interface CommonRepository<T, ID> extends JpaRepository<T, ID> {
}
