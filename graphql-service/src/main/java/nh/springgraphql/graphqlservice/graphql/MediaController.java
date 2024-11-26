package nh.springgraphql.graphqlservice.graphql;

import graphql.schema.DataFetchingFieldSelectionSet;
import nh.graphqlexample.graphqlservice.clients.media.client.MediaGraphQLQuery;
import nh.graphqlexample.graphqlservice.clients.media.client.MediaProjectionRoot;
import nh.springgraphql.graphqlservice.domain.Story;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.DgsGraphQlClient;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Controller
public class MediaController {

    private final GraphQlClient graphQlClient;
    private final DgsGraphQlClient dgsGraphQlClient;

    MediaController(@Value("${publisher.service.base-url}") String baseUrl) {
        RestClient restClient = RestClient.create(baseUrl + "graphql");
        this.graphQlClient = HttpSyncGraphQlClient.create(restClient);
        this.dgsGraphQlClient = DgsGraphQlClient.create(this.graphQlClient);
    }


    @SchemaMapping
    List<?> media(Story story, DataFetchingFieldSelectionSet selection) {

        if (selection.contains("mediadata/**")) {
            //
        } else {
            // ...
        }

        return fetchWithDgs(story);
    }

    private List<?> fetchWithDgs(Story story) {
       return this.dgsGraphQlClient
           .request(
               new MediaGraphQLQuery.Builder()
                   .storyId(story.id())
                   .build()
           ).projection(
               new MediaProjectionRoot<>()
                   .metadata()
                   .codec().dimensions().duration().parent()
                   .id().url().type().parent()

           )
           .retrieveSync("media")
           .toEntityList(Map.class);
    }

    private List<?> fetchWithGraphQlClient(Story story) {
        var result = this.graphQlClient
            .document(
                """
                    query ($storyId: ID!) {
                        media(storyId:$storyId) {
                            id type url
                            metadata { dimensions duration codec }
                          }
                      }
                    """)
            .variable("storyId", story.id())
            .retrieveSync("media")
            .toEntityList(Map.class);

        return result;
    }


}
