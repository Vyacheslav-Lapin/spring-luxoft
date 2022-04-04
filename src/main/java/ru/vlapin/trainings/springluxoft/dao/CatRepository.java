package ru.vlapin.trainings.springluxoft.dao;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vlapin.trainings.springluxoft.model.Cat;

public interface CatRepository extends JpaRepository<Cat, UUID> {
}
