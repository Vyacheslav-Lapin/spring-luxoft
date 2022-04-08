package ru.vlapin.trainings.springluxoft.config;

import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import ru.vlapin.trainings.springluxoft.common.Loggable;
import ru.vlapin.trainings.springluxoft.dao.CatRepository;
import ru.vlapin.trainings.springluxoft.model.Cat;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static ru.vlapin.trainings.springluxoft.common.Loggable.LogLevel.*;

@Component
@Loggable(INFO)
@RequiredArgsConstructor
public class DbInit implements ApplicationRunner {

  CatRepository catRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Stream.of("Мурзик, Барсик, Матроскин".split(", "))
        .map(Cat::new)
        .forEach(catRepository::save);
  }
}
