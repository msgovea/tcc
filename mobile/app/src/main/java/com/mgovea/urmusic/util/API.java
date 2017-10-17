package com.mgovea.urmusic.util;

/**
 * Created by Mateus on 4/2/2017.
 */

public interface API {

    //API
    String URL                  = "http://192.198.90.26/";

    String URL_IMGS             = "http://www.urmusic.me:82/";
    String IMG_PERFIL           = "perfil/";
    String IMG_PUBLICACAO       = "publicacao/";

    String REGISTER              = "usuario/cadastro";
    String BUSCAR_USUARIO        = "usuario/buscar";
    String BUSCAR_USUARIO_NOME   = "usuario/buscar?nome=";
    String ATUALIZAR_PERFIL      = "usuario/atualizar";
    String UPLOAD_IMAGEM         = "usuario/perfil/foto";
    String LOGIN                 = "usuario/login";
    String RECOVERY              = "usuario/recuperar";
    String GOSTOS_MUSICAIS       = "usuario/getGostosMusicais";
    String GOSTOS_MUSICAIS_REGISTER = "usuario/gostosmusicais";
    String PUBLICATION           = "publicacoes/get";
    String PUBLICATION_FRIEND    = "publicacoes/get/amigos";
    String PUBLICATION_HIGH      = "publicacoes/get/alta";
    String PUBLICATION_REGISTER  = "publicacoes/cadastrar";
    String PUBLICATION_IMPULS    = "publicacoes/impulsionar/";
    String COMMENTS_REGISTER     = "publicacoes/comentar";
    String COMMENTS              = "publicacoes/comentarios/listar?codigoPublicacao=";
    String REMOVE_COMMENTS       = "publicacoes/comentario/remover/";
    String REMOVE_PUBLICATION    = "publicacoes/remover?codigo=";
    String FOLLOW_USER           = "usuario/seguir";
    String LIKE_PUBLICATION      = "publicacoes/curtir";


    //SHAREDPREFERENCE

    String USUARIO = "USUARIO";
    String PUBLICACAO = "PUBLICAÇÃO";
    String IMPULSIONAMENTO = "IMPULSIONAMENTO";
}
