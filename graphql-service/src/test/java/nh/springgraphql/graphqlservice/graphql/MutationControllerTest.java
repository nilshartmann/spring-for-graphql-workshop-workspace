package nh.springgraphql.graphqlservice.graphql;

import nh.springgraphql.graphqlservice.domain.MutationController;
import nh.springgraphql.graphqlservice.domain.StoryRepository;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.ExecutionGraphQlServiceTester;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@GraphQlTest(MutationController.class)
//@Import(StoryRepository.class)
@AutoConfigureGraphQlTester
public class MutationControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;
    // mit Http-spezifischen Erweiterungen
//    private HttpGraphQlTester graphQlTester;

    @Test
    @DirtiesContext
    void create_comment_funktioniert() {
        graphQlTester
//            .mutate()
//                .header("Authorization", "Bearer 123455")
//                    .build()
            .document(
                // language=GraphQL
                """
              mutation($newComment: CreateCommentInput!) {
            createComment(input: $newComment) {
              id
            } }
            """)


            .variable("newComment", Map.of(
                "storyId", "1",
                "text", "Hallo Welt",
                "rating", 23
            ))
            .execute()
            .path("createComment.id").hasValue();
    }

}
