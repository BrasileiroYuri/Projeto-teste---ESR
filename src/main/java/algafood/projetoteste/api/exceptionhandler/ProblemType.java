package algafood.projetoteste.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível."),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado."),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso."),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido."),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema."),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos."),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio."),
    VIOLACAO_DE_INTEGRIDADE_DE_DADOS("/violação-de-integridade-de-dados", "Vioalação de integridade de dados.");

    private static final String HOST = "https://algafood.com.br";
    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = HOST + path;
        this.title = title;
    }
}
