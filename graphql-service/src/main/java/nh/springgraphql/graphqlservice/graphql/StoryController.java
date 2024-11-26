package nh.springgraphql.graphqlservice.graphql;

import nh.springgraphql.graphqlservice.domain.PublisherServiceClient;
import nh.springgraphql.graphqlservice.domain.Story;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class StoryController {

    public StoryController(PublisherServiceClient publisherServiceClient) {
        this.publisherServiceClient = publisherServiceClient;
    }

    private final PublisherServiceClient publisherServiceClient;

    record Publisher(Map<String, String> internal) {
        String getId() {
            return internal.get("id");
        }

        String getName() {
            return internal.get("name");
        }

        // keine Prüfung, ob das Enum korrekt ist hier!
        Map<String, String> getContact() {
            return Map.of(
                "type", internal.get("contact_type"),
                "value", internal.get("contact_value")
            );
        }

    }

//    record Publisher(Map<String, String> internal) {
//        String getId() {
//            return internal.get("id");
//        }
//
//        String getName() {
//            return internal.get("name");
//        }
//    }
//
//    @SchemaMapping
//    Map<String, String> contact(Publisher publisher) {
//        return Map.of(
//            "type", publisher.internal.get("contact_key")
//        );
//    }

//    @SchemaMapping(typeName = "Publisher")
//    Map<String, String> contact(Map<String, String> publisher) {
//      return null;
//    }

    @SchemaMapping
    Publisher publisher(Story story) {
        var publisherId = story.publisherId();
        // Optional<Map<String, String>>
        var result =  publisherServiceClient.fetchPublisher(publisherId);
        return new Publisher(result.orElseThrow());
    }

}
