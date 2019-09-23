package com.heavenhr.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heavenhr.test.model.Application;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
