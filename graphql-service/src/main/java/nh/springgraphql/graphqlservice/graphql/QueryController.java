package nh.springgraphql.graphqlservice.graphql;

import nh.springgraphql.graphqlservice.domain.Story;
import nh.springgraphql.graphqlservice.domain.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Controller
public class QueryController {
    private static final Logger log = LoggerFactory.getLogger(QueryController.class );
    private final StoryRepository storyRepository;

    public QueryController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @QueryMapping
    // Nur ADMINs duerfen Stories sehen, die im Draft-Zustand sind
    // Ausprobieren: query {  stories { id state } }
    //   Story 6 und 7 sind in draft und werden nicht zurückgeliefert,
    //   solange der aktuelle User kein ADMIN ist
    //   (Bearer-Token aus dem Log-File von Spring zum Testen verwenden)
    //  -> wenn man die Daten aus einer DB o.ä. bezieht, kann man natürlich auch
    //     den DB-Query so bauen, dass der nur die "erlaubten" Stories zurückliefert
    @PostFilter("!filterObject.state().isDraft() || hasRole('ROLE_ADMIN')")
    public List<Story> stories(){
        return storyRepository.findAllStories();
    }

//    @SchemaMapping(typeName = "Query", field = "story")
    @QueryMapping
    // Nur ADMINs duerfen Stories sehen, die im Draft-Zustand sind
    // Ausprobieren: query {  story(storyId: "S:6") { id state } }
    //   Story 6 und 7 sind in draft und werden nicht zurückgeliefert,
    //   solange der aktuelle User kein ADMIN ist
    //   Im Gegensatz zu @PostFiler wird hier ein Fehler ausgelöst ("Unauthorized")
    //    eine (bessere) Option an dieser Stelle wäre womöglich, lieber Optional.empty()
    //    im dem Fall zurückzuliefern, um Angreifern keine Hinweise zu geben.
    //   (Bearer-Token aus dem Log-File von Spring zum Testen verwenden)
    //  -> wenn man die Daten aus einer DB o.ä. bezieht, kann man natürlich auch
    //     den DB-Query so bauen, dass der nur die "erlaubten" Stories zurückliefert
    @PostAuthorize("returnObject.isEmpty() || !returnObject.get().state().isDraft() || hasRole('ROLE_ADMIN')")
    Optional<Story> story(@Argument NodeId storyId) {
        return storyRepository.findStory(storyId.id());
    }

    @SchemaMapping
    String excerpt(Story story, @Argument int maxLength) {

        log.info("Excerpt for story {} {}", story.id(), maxLength);
        return storyRepository.generateExcerpt(story, maxLength);
//        var actualLength = Math.min(story.body().length(), maxLength);
//        return story.body().substring(0, actualLength);
    }


}
