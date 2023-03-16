package algafood.projetoteste.api.exceptionhandler;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.exception.NegocioException;
import algafood.projetoteste.domain.exception.ValidacaoException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static algafood.projetoteste.api.exceptionhandler.ProblemType.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRO_INTERNO = "Ocorreu um erro interno no sistema."
    + " Tente novamente e se o erro persistir, entre em contato com o administrador do sistema.";

    private final MessageSource messageSource;

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, RECURSO_NAO_ENCONTRADO, detail).userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, ENTIDADE_EM_USO, detail).userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(status, ERRO_NEGOCIO, detail).userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        ex.printStackTrace();
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        Problem problem = createProblemBuilder(status, ERRO_DE_SISTEMA, ERRO_INTERNO).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (rootCause instanceof SQLIntegrityConstraintViolationException) {
            return handleSQLIntegrityConstraintViolation(
                    ((SQLIntegrityConstraintViolationException) rootCause), status, request);
        }
        Problem problem =
                createProblemBuilder(HttpStatus.BAD_REQUEST, VIOLACAO_DE_INTEGRIDADE_DE_DADOS, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> handleSQLIntegrityConstraintViolation(
            SQLIntegrityConstraintViolationException ex, HttpStatus status, WebRequest request) {
        Problem problem = createProblemBuilder(status, VIOLACAO_DE_INTEGRIDADE_DE_DADOS, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return getProblemObjects(ex, ex.getBindingResult(), status, headers, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacao(ValidacaoException ex, WebRequest request) {
        return getProblemObjects(ex, ex.getBindingResult(), HttpStatus.BAD_REQUEST, new HttpHeaders(), request);
    }

    private ResponseEntity<Object> getProblemObjects(
            Exception ex, BindingResult bindingResult, HttpStatusCode status, HttpHeaders headers, WebRequest request) {
        List<Problem.Object> objects = bindingResult.getAllErrors().stream().map(objectError -> {
            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            String name = objectError.getObjectName();
            Object flag = null;
            if (objectError instanceof FieldError) {
                name = ((FieldError) objectError).getField();
                flag = ((FieldError) objectError).getRejectedValue();
            }
            return Problem.Object.builder()
                    .name(name)
                    .userMessag(message)
                    .flag(flag)
                    .build();
        }).toList();
        Problem problem = createProblemBuilder(status, DADOS_INVALIDOS, ERRO_INTERNO)
                .objects(objects).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = String.format("%s", ex.getMessage());
        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String detail =
                String.format("O recurso %s que você tentou acessar é inexistente. Método: %s.",
                        ex.getRequestURL(), ex.getHttpMethod());
        Problem problem = createProblemBuilder(status, RECURSO_NAO_ENCONTRADO, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Object value = ex.getValue();
        String detail =
                String.format("O parâmetro de URL %s recebeu o valor '%s' que é do tipo %s. " +
                                "Informe um valor compatível com o tipo %s",
                        ex.getName(), value, value.getClass().getSimpleName(), ex.getRequiredType().getSimpleName());
        Problem problem = createProblemBuilder(status, PARAMETRO_INVALIDO, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        System.out.println(rootCause.getClass().getSimpleName());
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe";
        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(
            PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String format = String.format("Erro em propriedade '%s'. Inválida ou desconhecida", path);
        Problem problem =
                createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, format).userMessage(ERRO_INTERNO).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(
            InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Object value = ex.getValue();
        String property = joinPath(ex.getPath());
        String detail = String.format("Erro na propriedade '%s': tipo esperado %s, valor atribuído: '%s', do tipo %s. ",
                property, ex.getTargetType().getSimpleName(), value, value.getClass().getSimpleName());
        Problem problem =
                createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail).userMessage(ERRO_INTERNO).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        /* body = (body instanceof Problem) ? body : getDefaultProblem(statusCode); */
        String reasonPhrase = HttpStatus.valueOf(statusCode.value()).getReasonPhrase();
        if (body == null) {
            body = Problem.builder().
                    timeStamp(OffsetDateTime.now())
                    .title(reasonPhrase).status(statusCode.value()).userMessage(ERRO_INTERNO).build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timeStamp(OffsetDateTime.now()).
                    title((String) body).status(statusCode.value()).userMessage(ERRO_INTERNO).build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
        return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
                .detail(detail).timeStamp(OffsetDateTime.now());
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.joining("."));
    }

}