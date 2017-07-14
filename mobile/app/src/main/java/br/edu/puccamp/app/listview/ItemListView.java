package br.edu.puccamp.app.listview;

/**
 * Created by mgovea on 11/06/2017.
 */

public class ItemListView
{
    private int codigo;
    private String texto;
    private int iconeRid;

    public ItemListView()
    {
    }

    public ItemListView(String texto, int iconeRid, int numero)
    {
        this.texto = texto;
        this.iconeRid = iconeRid;
        this.codigo = numero;
    }

    public int getIconeRid()
    {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid)
    {
        this.iconeRid = iconeRid;
    }

    public String getTexto()
    {
        return texto;
    }

    public void setTexto(String texto)
    {
        this.texto = texto;
    }
}