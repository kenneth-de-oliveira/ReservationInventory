package io.bookwise.framework.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GenericErrorsEnum {
     
    BAD_REQUEST             (400, "400", "Bad Request", "Incorrect request"),
    UNAUTHORIZED            (401, "401", "Unauthorized", "User authentication is required"),
    FORBIDDEN               (403, "403", "Forbidden", "Client doesn't have sufficient permissions to access the resource"),
    NOT_FOUND               (404, "404", "Not Found", "URI not mapped to resource"),
    METHOD_NOT_ALLOWED      (405, "405", "Method Not Allowed", "HTTP method is not supported"),
    ERROR_GENERIC           (500, "500", "Internal Server Error", "Unexpected Error"),
    METHOD_NOT_IMPLEMENTED  (501, "501", "Not Implemented", "Method is not implemented");
 
    private final int statusCode;
    private final String code;
    private final String reason;
    private final String info;
     
    public static GenericErrorsEnum get(int statusCode) {
        return Stream.of(values()).filter(p -> p.getStatusCode() == statusCode).findFirst().orElse(ERROR_GENERIC);
    }

}