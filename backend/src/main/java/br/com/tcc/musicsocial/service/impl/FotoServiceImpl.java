package br.com.tcc.musicsocial.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import br.com.tcc.musicsocial.service.FotoService;
import br.com.tcc.musicsocial.util.ExtensaoFoto;

@Service
public class FotoServiceImpl implements FotoService {
	
	private final String CAMINHO = "D:\\imagens\\";
	
	private final String PERFIL = "perfil\\";
	
	@Override
	public boolean gravarImagemPerfilUsuario(byte[] foto, Long idUsuario) {
		File arquivo = new File(CAMINHO.concat(PERFIL).concat(idUsuario.toString()).concat(".jpg"));
		return gravarFoto(foto, arquivo, ExtensaoFoto.JPG);
		
	}
	
	private boolean gravarFoto(byte[] foto, File arquivo, ExtensaoFoto extensao) {
		try {
			if(!arquivo.exists()) {
				arquivo.createNewFile();
			}
			InputStream is = new ByteArrayInputStream(foto);
			File log = new File(CAMINHO.concat("log.txt"));
			PrintWriter pw = new PrintWriter(log);
			pw.println(foto);
			pw.close();
			BufferedImage image = ImageIO.read(is);
			return ImageIO.write(image, extensao.getValor(), arquivo);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}