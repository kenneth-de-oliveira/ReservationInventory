package io.bookwise.framework.errors;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.bookwise.framework.errors.GenericErrorsEnum.ERROR_GENERIC;

@Slf4j
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable throwable, @NonNull DataFetchingEnvironment env) {
        String message = throwable.getMessage() != null ? throwable.getMessage() : "Internal server error";
        Map<String, Object> extensions = Map.of(
                "code", ERROR_GENERIC.getCode(),
                "reason", ERROR_GENERIC.getReason(),
                "info", message,
                "exception", throwable.getClass().getSimpleName()
        );

        log.error(extensions.toString(), throwable.getCause());

        return GraphqlErrorBuilder.newError(env)
                .message(message)
                .errorType(graphql.ErrorType.DataFetchingException)
                .extensions(extensions)
                .build();
    }

}