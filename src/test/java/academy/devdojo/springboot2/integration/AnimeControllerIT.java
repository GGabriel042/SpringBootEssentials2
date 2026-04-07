package academy.devdojo.springboot2.integration;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();


        ResponseEntity<List<Anime>> response = testRestTemplate.exchange(
                "/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {}
        );

        List<Anime> animes = response.getBody();

        Assertions.assertThat(animes).isNotNull();
        Assertions.assertThat(animes)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("List returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();


        List<Anime> animes = testRestTemplate.exchange(
                "/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Anime>>() {}
        ).getBody();


        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }


//    @Test
//    @DisplayName("FindById returns anime when successful")
//    void findById_ReturnsAnime_WhenSuccessful(){
//        Long expectedId = AnimeCreator.createValidAnime().getId();
//        Anime anime = animeController.findById(1L).getBody();
//
//        Assertions.assertThat(anime).isNotNull();
//
//        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
//    }
//
//
//
//    @Test
//    @DisplayName("FindByName returns anime when successful")
//    void findByName_ReturnsListOfAnimes_WhenSuccessful(){
//        String expectedName = AnimeCreator.createValidAnime().getName();
//        List<Anime> animes = animeController.findByName("anime").getBody();
//
//        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
//
//        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
//    }
//
//
//    @Test
//    @DisplayName("FindByName returns and empty list of anime when anime is not found")
//    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound(){
//        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
//                .thenReturn(Collections.emptyList());
//
//
//        List<Anime> animes = animeController.findByName("anime").getBody();
//
//        Assertions.assertThat(animes).isNotNull();
//    }
//
//
//    @Test
//    @DisplayName("save returns anime when successful")
//    void save_ReturnsAnime_WhenSuccessful(){
//
//        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
//
//        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
//    }
//
//
//    @Test
//    @DisplayName("replace update anime when successful")
//    void replace_UpdateAnime_WhenSuccessful(){
//
//        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
//                .doesNotThrowAnyException();
//
//
//        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());
//
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//    }
//
//
//
//    @Test
//    @DisplayName("delete removes when successful")
//    void delete_RemovesAnime_WhenSuccessful(){
//
//        Assertions.assertThatCode(() -> animeController.delete(1))
//                .doesNotThrowAnyException();
//
//
//        ResponseEntity<Void> entity = animeController.delete(1);
//
//        Assertions.assertThat(entity).isNotNull();
//        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//    }
}