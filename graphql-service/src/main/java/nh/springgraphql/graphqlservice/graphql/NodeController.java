package nh.springgraphql.graphqlservice.graphql;

import nh.springgraphql.graphqlservice.domain.PublisherServiceClient;
import nh.springgraphql.graphqlservice.domain.StoryRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NodeController {

    private final StoryRepository storyRepository;
    private final PublisherServiceClient publisherServiceClient;

    public NodeController(StoryRepository storyRepository, PublisherServiceClient publisherServiceClient) {
        this.storyRepository = storyRepository;
        this.publisherServiceClient = publisherServiceClient;
    }

    @SchemaMapping(typeName = "Node")
    NodeId id(Object o) {
        return NodeId.forNode(o);
    }

    @QueryMapping
    Object node(@Argument NodeId id) {
        return switch (id.typeName()) {
            case "S" -> storyRepository.findStory(id.id());
            case "C" -> storyRepository.findComment(id.id());
            case "P" -> new StoryController.Publisher(publisherServiceClient.fetchPublisher(id.id()).orElseThrow());
            default -> throw new IllegalStateException("Invalid Node Id " + id);
        };

    }

}
