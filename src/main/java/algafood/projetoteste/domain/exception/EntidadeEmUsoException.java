package algafood.projetoteste.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntidadeEmUsoException extends RuntimeException {

    public EntidadeEmUsoException(String message) {
        super(message);
    }

}

