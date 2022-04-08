package ru.vlapin.trainings.springluxoft.dao;

import java.util.UUID;

import ru.vlapin.trainings.springluxoft.model.Cat;

import org.springframework.data.jpa.repository.JpaRepository;

//@RepositoryRestResource
public interface CatRepository extends JpaRepository<Cat, UUID> {
}
