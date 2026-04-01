package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 1);
        log.info(entity.getBody());

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class,1);
        log.info(object);


        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));


        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                });
        log.info(exchange.getBody());


        Anime kingdom = Anime.builder().name("kingdom").build();
        ResponseEntity<Anime> kingdomSaved = new RestTemplate().postForEntity("http://localhost:8080/animes", kingdom, Anime.class);
        log.info("saved anime {}", kingdomSaved);


        Anime naruto = Anime.builder().name("naruto").build();
        ResponseEntity<Anime> narutoSaved = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<>(naruto, createJsonHeader()),
                Anime.class);
        log.info("saved anime {}", narutoSaved);


        Anime animeToBeUpdated = narutoSaved.getBody();
        animeToBeUpdated.setName("Boruto");
        ResponseEntity<Void> narutoUpdated = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);
        log.info(narutoUpdated);



        ResponseEntity<Void> narutoDeleted = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());
        log.info(narutoDeleted);

    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
