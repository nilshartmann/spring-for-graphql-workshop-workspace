package nh.springgraphql.graphqlservice.graphql;

import nh.springgraphql.graphqlservice.domain.Story;
import nh.springgraphql.graphqlservice.domain.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
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
    public List<Story> stories(){
        return storyRepository.findAllStories();
    }

//    @SchemaMapping(typeName = "Query", field = "story")
    @QueryMapping
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
