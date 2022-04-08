package ru.vlapin.trainings.springluxoft.service.jsonplaceholder;

import java.util.List;

import ru.vlapin.trainings.springluxoft.model.jsonplaceholder.User;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "UserJsonPlaceHolder",
    url = "https://jsonplaceholder.typicode.com",
    path = "users")
public interface UserService {

  @GetMapping
  List<User> all();

  @GetMapping("{id}")
  ResponseEntity<User> findById(@PathVariable Long id);
}
