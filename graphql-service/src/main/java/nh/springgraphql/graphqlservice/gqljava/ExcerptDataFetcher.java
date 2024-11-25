package nh.springgraphql.graphqlservice.gqljava;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import nh.springgraphql.graphqlservice.domain.Story;

public class ExcerptDataFetcher implements DataFetcher<String>  {
  // TODO: Implementiere diesen Datafetcher
  //   Der DataFetcher soll die ersten drei Zeichen des 'body'-Feldes einer Story zurückliefern


    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        Story story = environment.getSource();
        return story.body().substring(0, 3);
    }

    /*

      query { story(storyId: "1")
                { id excerpt }
            }

     */
}
