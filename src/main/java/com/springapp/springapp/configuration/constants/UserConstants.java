package com.springapp.springapp.configuration.constants;

public final class UserConstants {

    private UserConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String USER_NOT_FOUND = "Usuário não encontrado";
    public static final String USER_ALREADY_EXISTS = "Usuário já existe";
    public static final String USER_CREATED = "Usuário criado com sucesso";
    public static final String USER_UPDATED = "Usuário atualizado com sucesso";
    public static final String USER_DELETED = "Usuário deletado com sucesso";
    public static final String USER_PASSWORD_CHANGED = "Senha alterada com sucesso";
    public static final String USER_PASSWORD_FAILED = "A senha atual não confere";

    public static final String API_USER_CREATION = "Cria um novo usuario";
    public static final String API_USER_UPDATE = "Atualiza um usuario";
    public static final String API_USER_DELETION = "Deleta um usuario";
    public static final String API_USER_LIST = "Lista todos os usuarios";
    public static final String API_USER_GET = "Busca um usuario pelo id";
    public static final String API_USER_LOGIN = "Login de um usuario";

    public static final String API_AUTH_FAILED = "Falha ao autenticar";

    public static final String API_AUTH_GUID_PASSWORD = "4470f05b-3956-4f48-9c85-569df8c7d402";

    public static final String USER_ROLE_ADMIN = "0";
    public static final String USER_ROLE_USER = "1";

}
