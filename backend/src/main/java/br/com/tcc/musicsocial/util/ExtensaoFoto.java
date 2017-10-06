package br.com.tcc.musicsocial.util;

public enum ExtensaoFoto {
	BMP("bmp"),
	JPG("jpg"),
	PNG("png"),
	WBMP("wbmp"),
	GIF("gif");
	
	String valor;

	private ExtensaoFoto(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
}
