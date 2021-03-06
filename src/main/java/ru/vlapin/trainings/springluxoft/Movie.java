package ru.vlapin.trainings.springluxoft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

public interface Movie {
  String getDirector();
}

//@Component
@RequiredArgsConstructor
final class MovieImpl implements Movie {

  @Getter
  @Value("${movie.director}")
  String director;
}
