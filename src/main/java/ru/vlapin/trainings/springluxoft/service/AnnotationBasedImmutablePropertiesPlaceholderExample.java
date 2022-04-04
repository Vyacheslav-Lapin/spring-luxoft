package ru.vlapin.trainings.springluxoft.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

public sealed interface AnnotationBasedImmutablePropertiesPlaceholderExample {
    String getAuthMethod();
    String getUsername();
    String getPassword();
}

/**
 * Limitations:
 * - no defaults (!!!)
 */
@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("mail.credentials")
final class AnnotationBasedImmutablePropertiesPlaceholderExampleImpl
  implements AnnotationBasedImmutablePropertiesPlaceholderExample {

  /**
   * Auth method
   */
  String authMethod;

  /**
   * login
   */
  String username;

  /**
   * pwd
   */
  String password;

}
