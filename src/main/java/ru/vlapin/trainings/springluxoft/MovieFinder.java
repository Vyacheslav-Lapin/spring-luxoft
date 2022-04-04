package ru.vlapin.trainings.springluxoft;

import java.util.Collection;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public interface MovieFinder {
  Collection<Movie> findAll();
}

@Component
@RequiredArgsConstructor
final class MovieFinderImpl implements MovieFinder {

  Movie movie;

  @Override
  public Collection<Movie> findAll() {
    return Collections.singleton(movie);
  }
}
