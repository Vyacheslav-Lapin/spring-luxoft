package ru.vlapin.trainings.springluxoft;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public interface DirectMovieLister {
  List<Movie> moviesDirectedBy(String director);
}

@Component
@RequiredArgsConstructor
final class DirectMovieListerImpl implements DirectMovieLister {
  MovieFinder finder;

  public List<Movie> moviesDirectedBy(String director) {
    return finder.findAll().stream()
               .filter(movie -> movie.getDirector().equals(director))
               .collect(Collectors.toList());
//               .toList();
  }
}
