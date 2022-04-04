package ru.vlapin.trainings.springluxoft;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

public sealed interface DirectMovieLister {
  List<Movie> moviesDirectedBy(String director);
}

@RequiredArgsConstructor
final class DirectMovieListerImpl implements DirectMovieLister {
  MovieFinder finder;

  public List<Movie> moviesDirectedBy(String director) {
    return finder.findAll().stream()
               .filter(movie -> movie.getDirector().equals(director))
               .collect(Collectors.toList());
  }
}
