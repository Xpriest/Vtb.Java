package com.flamexander.spring.security.cookbook.dao.repositories;

import com.flamexander.spring.security.cookbook.dao.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends CrudRepository<Role, Long> {
}
