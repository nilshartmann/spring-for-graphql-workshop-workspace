package nh.springgraphql.graphqlservice.gqljava;


import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.springgraphql.graphqlservice.domain.Story;
import nh.springgraphql.graphqlservice.domain.StoryRepository;

import java.util.List;

public class QueryStoriesDataFetcher implements DataFetcher<List<Story>>{
  // TODO: Implementiere diesen DataFetcher:
  //  er soll ALLE Stories aus einem StoryRepository zurückliefern

    private final StoryRepository storyRepository = new StoryRepository();

    @Override
    public List<Story> get(DataFetchingEnvironment environment) throws Exception {
        return storyRepository.findAllStories();
    }
}
