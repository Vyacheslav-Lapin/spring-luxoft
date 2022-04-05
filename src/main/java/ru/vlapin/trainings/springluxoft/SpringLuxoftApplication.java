package ru.vlapin.trainings.springluxoft;

import java.util.stream.Stream;
import lombok.val;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.vlapin.trainings.springluxoft.dao.CatRepository;
import ru.vlapin.trainings.springluxoft.model.Cat;
import ru.vlapin.trainings.springluxoft.model.JavaConfigBasedSetterPropertiesPlaceholderExample;
import ru.vlapin.trainings.springluxoft.model.JavaConfigBasedSetterPropertiesPlaceholderExampleImpl;

@SpringBootApplication
//@RequiredArgsConstructor
public class SpringLuxoftApplication {

  //  Movie movie;

  public static void main(String[] args) {
    //    @Cleanup
    val context = SpringApplication.run(SpringLuxoftApplication.class, args);

    //    val movieFinder = context.getBean(MovieFinder.class);
    //    movieFinder.findAll().stream()
    //        .map(Movie::getDirector)
    //        .forEach(System.out::println);

    //    ServiceLoader.load(java.sql.Driver.class)
    //        .findFirst()
    //        .ifPresent(driver -> {
    //
    //        });
  }

  @Bean
  @ConfigurationProperties("my-properties2")
  JavaConfigBasedSetterPropertiesPlaceholderExample mySetterProperties2(Movie movie) {
    System.out.println(movie.getDirector());
    return new JavaConfigBasedSetterPropertiesPlaceholderExampleImpl();
  }

  @Bean
  ApplicationRunner dbRunner(CatRepository catRepository) {
    return __ -> Stream.of("Мурзик, Барсик, Матроскин".split(", "))
                     .map(Cat::new)
                     .forEach(catRepository::save);
  }
}
