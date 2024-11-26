package nh.springgraphql.graphqlservice.graphql;

import nh.springgraphql.graphqlservice.domain.Story;
import nh.springgraphql.graphqlservice.domain.StoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@GraphQlTest(QueryController.class)
@Import(StoryRepository.class)
public class QueryControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void stories_liefert_stories_zurueck() {
        graphQlTester.
            document(
                // language=GraphQL
                """
                    query {
                    stories {
                       id title comments {id } } }                   
                    """)
            .execute()
//            .path("stories[0].id").entity(String.class).isEqualTo("1")
//            .path("stories[0].title").entity(String.class).isEqualTo("Tech Innovations in 2024")
            .path("stories[0]", story -> {
                story.path("id").entity(String.class).isEqualTo("1");
                story.path("title").entity(String.class).isEqualTo("Tech Innovations in 2024");
            })
            .path("stories[0]").entity(Story.class).satisfies(s -> {
                assertThat(s.id()).isEqualTo("1");
            })
            .path("stories[*]").entityList(Object.class).hasSize(8);
//            .path("...").entity(new ParameterizedTypeReference<Map<String, Object>>() {
//            });
    }

    @Test
    void story_liefert_story_zurueck() {
        graphQlTester.
            document(
                // language=GraphQL
                """
                    query ($storyId: ID!){
                    story(storyId: $storyId) {
                       id title comments {id } } }                   
                    """)
            .variable("storyId", "1")
            .execute()
            .path("story.id").entity(String.class).isEqualTo("1");
    }


}
