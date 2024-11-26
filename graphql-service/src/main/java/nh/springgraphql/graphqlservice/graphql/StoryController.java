package nh.springgraphql.graphqlservice.graphql;

import nh.springgraphql.graphqlservice.domain.PublisherServiceClient;
import nh.springgraphql.graphqlservice.domain.Story;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class StoryController {

    private static final Logger log = LoggerFactory.getLogger( StoryController.class );

    public StoryController(PublisherServiceClient publisherServiceClient, BatchLoaderRegistry registry) {
        this.publisherServiceClient = publisherServiceClient;

//        registry.forTypePair(String.class, Publisher.class)
//            .registerMappedBatchLoader((publisherIds, env) -> {
//                log.info("Loading Stories for {}", publisherIds);
//                var request = publisherIds.stream()
//                    .map(id -> CompletableFuture.supplyAsync(() -> publisherServiceClient.fetchPublisher(id)))
//                    .toList();
//                var publishers = request.stream().map(CompletableFuture::join)
//                    .filter(Optional::isPresent)
//                    .map(Optional::get)
//                    .map(Publisher::new)
//                    .collect(Collectors.toMap(Publisher::getId, Function.identity()));
//
//                return Mono.just(publishers);
//            });
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

    //  DATA LOADER #1 (mit Registry im Constructor!)
//    @SchemaMapping
//    CompletableFuture<Publisher> publisher(Story story, DataLoader<String, Publisher> publisherDataLoader) {
//        return publisherDataLoader.load(story.publisherId());
//    }

    @BatchMapping
    Map<Story, Publisher> publisher(List<Story> stories) {
        log.info("BatchMapping for Stories {}", stories.stream().map(Story::id).toList());

        var publisherIds = stories.
            stream().map(Story::publisherId)
            .distinct()
            .toList();

        var request = publisherIds.stream()
//            .map(id -> CompletableFuture.supplyAsync(() -> publisherServiceClient.fetchPublisher(id)))
            .map(publisherServiceClient::fetchPublisherAsync)
            .toList();

        var publishersById = request.stream()
            .map(CompletableFuture::join)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Publisher::new)
            .collect(Collectors.toMap(Publisher::getId, Function.identity()));

        var publishers = stories.stream().collect(Collectors.toMap(
            Function.identity(), s -> publishersById.get(s.publisherId())
        ));

        return publishers;

    }



//    @SchemaMapping
//    Publisher publisher(Story story) {
//        var publisherId = story.publisherId();
//        // Optional<Map<String, String>>
//        var result =  publisherServiceClient.fetchPublisher(publisherId);
//        return new Publisher(result.orElseThrow());
//    }


//    @SchemaMapping
//    Callable<Publisher> publisher(Story story) {
//        return () -> {
//            var publisherId = story.publisherId();
//            // Optional<Map<String, String>>
//            var result = publisherServiceClient.fetchPublisher(publisherId);
//            return new Publisher(result.orElseThrow());
//        };
//    }

}
