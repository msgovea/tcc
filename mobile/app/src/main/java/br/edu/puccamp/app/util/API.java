package br.edu.puccamp.app.util;

/**
 * Created by Mateus on 4/2/2017.
 */

public interface API {

    //API
    String URL        = "http://192.198.90.26:82/musicsocial/";

    String REGISTER              = "usuario/cadastro";
    String BUSCAR_USUARIO        = "usuario/buscar";
    String ATUALIZAR_PERFIL      = "usuario/atualizar";
    String LOGIN                 = "usuario/login";
    String RECOVERY              = "usuario/recuperar";
    String GOSTOS_MUSICAIS       = "usuario/getGostosMusicais";
    String GOSTOS_MUSICAIS_REGISTER = "usuario/gostosmusicais";
    String PUBLICATION           = "publicacoes/get";
    String PUBLICATION_REGISTER  = "publicacoes/cadastrar";

    //SHAREDPREFERENCE

    String USUARIO = "USUARIO";


}
