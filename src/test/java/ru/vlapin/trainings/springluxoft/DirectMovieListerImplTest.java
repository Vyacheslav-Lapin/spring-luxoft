package ru.vlapin.trainings.springluxoft;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DirectMovieListerImplTest {

  DirectMovieLister directMovieLister;
  Movie movie1;

  @Test
  @SneakyThrows
  @DisplayName("DirectMovieFinder works correctly")
  void directMovieFinderWorksCorrectlyTest() {
    assertThat(directMovieLister.moviesDirectedBy("Lorem ipsum dolor sit amet")).isNotNull()
        .hasSize(1)
        .contains(movie1);
  }
}
