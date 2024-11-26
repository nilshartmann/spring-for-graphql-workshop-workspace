package nh.springgraphql.graphqlservice.graphql;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.security.RolesAllowed;
import nh.springgraphql.graphqlservice.domain.Comment;
import nh.springgraphql.graphqlservice.domain.ResourceNotFoundException;
import nh.springgraphql.graphqlservice.domain.StoryRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MutationController {

    private final StoryRepository storyRepository;

    public MutationController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    record CreateCommentInput(String storyId, String text, int rating){}

    @GraphQlExceptionHandler
    GraphQLError handleResourceNotFoundException(
        ResourceNotFoundException ex,
        DataFetchingEnvironment env) {
        return GraphQLError.newError()
            .message("Kommentar anlegen geht heute nicht.")
            .errorType(ErrorType.OperationNotSupported)
            .location(env.getField().getSourceLocation())
            .build();
    }
//    @GraphQlExceptionHandler
//    GraphQLError handleException(RuntimeException ex) {
//
//    }

    @RolesAllowed("USER") // nur "USER" dürfen Kommentare anlegen
    @MutationMapping
    Comment createComment(@Argument CreateCommentInput input) {

        var newComment = storyRepository.addComment(
            input.storyId(),
            input.text(),
            input.rating()
        );

        return newComment;
    }

    record AddCommentInput(String storyId, String text, int rating){}

    interface AddCommentPayload {}
    record AddCommentSuccess(Comment newComment) implements AddCommentPayload {}
    record AddCommentError(String msg) implements AddCommentPayload {}

    @MutationMapping
    AddCommentPayload addComment(@Argument AddCommentInput input) {
        try {
            var newComment = storyRepository.addComment(
                input.storyId(),
                input.text(),
                input.rating()
            );

            return new AddCommentSuccess(newComment);
        }  catch (Exception ex) {
            // im "echten Leben" bitte keine Exception Message zurückliefern
            return new AddCommentError(ex.getMessage());
        }
    }



}
