package br.com.tcc.musicsocial.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import br.com.tcc.musicsocial.service.FotoService;
import br.com.tcc.musicsocial.util.ExtensaoFoto;

@Service
public class FotoServiceImpl implements FotoService {
	
	private final String CAMINHO = "D:\\imagens\\";
	
	private final String PERFIL = "perfil\\";
	
	private final String PUBLI = "publicacao\\";
	
	@Override
	public boolean gravarImagemPerfilUsuario(byte[] foto, Long idUsuario) {
		File arquivo = new File(CAMINHO.concat(PERFIL).concat(idUsuario.toString()).concat(".jpg"));
		return gravarFoto(foto, arquivo, ExtensaoFoto.JPG);
		
	}
	
	@Override
	public boolean gravarImagemPublicacao(byte[] foto, Long idPublicacao) {
		File arquivo = new File(CAMINHO.concat(PUBLI).concat(idPublicacao.toString()).concat(".jpg"));
		return gravarFoto(foto, arquivo, ExtensaoFoto.JPG);
		
	}
	
	private boolean gravarFoto(byte[] foto, File arquivo, ExtensaoFoto extensao) {
		try {
			if(!arquivo.exists()) {
				arquivo.createNewFile();
			}
			InputStream is = new ByteArrayInputStream(foto);
			BufferedImage image = ImageIO.read(is);
			return ImageIO.write(image, extensao.getValor(), arquivo);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}