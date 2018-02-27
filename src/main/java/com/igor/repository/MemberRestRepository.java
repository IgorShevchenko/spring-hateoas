package com.igor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.igor.model.Member;

/**
 * Will direct Spring MVC to create RESTful endpoints at
 * /members-rest-repository. It creates a collection of Spring MVC
 * controllers, JSON converters, and other beans needed to provide a RESTful
 * front end. These components link up to the Spring Data JPA backend.
 */
@RepositoryRestResource(collectionResourceRel = "members", path = "members-rest-repository")
public interface MemberRestRepository extends JpaRepository<Member, Long> {

    List<Member> findByLastName(@Param("lastname") String lastname);

}
