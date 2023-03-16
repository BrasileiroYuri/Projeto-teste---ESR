package algafood.projetoteste.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String userMessage;
    private OffsetDateTime timeStamp;

    private List<Object> objects;

    @Getter
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Object {
        private String name;
        private String userMessag;
        private java.lang.Object flag;

    }

    public static Problem getDefaultProblem(HttpStatusCode statusCode) {
        String reasonPhrase = HttpStatus.valueOf(statusCode.value()).getReasonPhrase();
        return builder().timeStamp(OffsetDateTime.now()).title(reasonPhrase).status(statusCode.value()).build();
    }

}
