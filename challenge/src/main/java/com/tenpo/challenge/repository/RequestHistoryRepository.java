package com.tenpo.challenge.repository;

import com.tenpo.challenge.entity.RequestHistory;
import com.tenpo.challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestHistoryRepository extends JpaRepository<RequestHistory, Long> {

}
