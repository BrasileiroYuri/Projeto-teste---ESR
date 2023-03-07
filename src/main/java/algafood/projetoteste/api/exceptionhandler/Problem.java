package algafood.projetoteste.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
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
    private LocalDateTime timeStamp;

    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private String name;
        private String userMessag;
        private Object flag;

    }

    public static Problem getDefaultProblem(HttpStatusCode statusCode) {
        String reasonPhrase = HttpStatus.valueOf(statusCode.value()).getReasonPhrase();
        return builder().timeStamp(LocalDateTime.now()).title(reasonPhrase).status(statusCode.value()).build();
    }

}
