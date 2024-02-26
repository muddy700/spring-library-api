package com.kalambo.libraryapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalambo.libraryapi.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
