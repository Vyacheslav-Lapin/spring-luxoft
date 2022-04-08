package ru.vlapin.trainings.springluxoft.controller.jsonplaceholder;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.vlapin.trainings.springluxoft.model.jsonplaceholder.User;
import ru.vlapin.trainings.springluxoft.service.jsonplaceholder.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  UserService userService;

  @NotNull
  @GetMapping
//  @Contract(pure = true)
  public ResponseEntity<List<User>> all() {
    return ResponseEntity.ok(userService.all());
  }

//  @NotNull
  @GetMapping("{id}")
//  @Contract(pure = true)
  public User byId(@PathVariable @NotNull Long id) {
    return userService.findById(id).getBody();
  }
}
