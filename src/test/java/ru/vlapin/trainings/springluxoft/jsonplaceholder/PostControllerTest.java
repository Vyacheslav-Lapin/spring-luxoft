package ru.vlapin.trainings.springluxoft.jsonplaceholder;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vlapin.trainings.springluxoft.service.jsonplaceholder.PostService;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PostControllerTest {

  long id = 57L;
  PostService postService;

  @Test
  @SneakyThrows
  @DisplayName("Get method works correctly")
  void get() {
    assertThat(postService.all()).isNotNull()
        .isNotEmpty()
        .hasSize(100);
  }

  @Test
  @SneakyThrows
  @DisplayName("Get one post method works correctly")
  void getOnePostMethodWorksCorrectlyTest() {
    assertThat(postService.findById(id)).isNotNull()
        .matches(post -> post.getId() == id, "id is equals")
        .matches(post -> post.getBody().equals(
            "at pariatur consequuntur earum quidem\n" +
            "quo est laudantium soluta voluptatem\n" +
            "qui ullam et est\n" +
            "et cum voluptas voluptatum repellat est"), "body is equals");
  }
}
