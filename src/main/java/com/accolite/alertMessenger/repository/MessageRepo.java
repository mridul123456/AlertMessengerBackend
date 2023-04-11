package com.accolite.alertMessenger.repository;

import com.accolite.alertMessenger.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {

    @Query(value = "select * from message where is_published = 1", nativeQuery = true)
    public List<Message> getMessagesForUser();

}