package ru.vlapin.trainings.springluxoft.service.jsonplaceholder;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vlapin.trainings.springluxoft.model.jsonplaceholder.Address;
import ru.vlapin.trainings.springluxoft.model.jsonplaceholder.Geo;
import ru.vlapin.trainings.springluxoft.model.jsonplaceholder.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceTests {

  UserService userService;

  @Test
  @SneakyThrows
  @DisplayName("user service works correctly")
  void userServiceWorksCorrectlyTest() {
    assertThat(userService.findById(1L)).isNotNull()
        .matches(entity -> entity.getStatusCode().is2xxSuccessful())
        .extracting(ResponseEntity::getBody)
        .extracting(User::getAddress)
        .extracting(Address::getGeo)
        .extracting(Geo::getLatitude, Geo::getLongitude)
        .contains(-37.3159, 81.1496);
  }
}
