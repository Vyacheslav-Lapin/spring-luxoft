package ru.vlapin.trainings.springluxoft;

import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.stereotype.Component;

public interface Movie {
  String getDirector();
}

@Component
final class MovieImpl implements Movie {

  @Override
  public String getDirector() {
    return "Lorem ipsum dolor sit amet";
  }
}
