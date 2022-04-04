package ru.vlapin.trainings.springluxoft.model.jsonplaceholder;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class Post {

  Long userId;

  Long id;

  String title;

  String body;
}
