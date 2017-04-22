package br.edu.puccamp.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mateus on 4/22/2017.
 */

public abstract class Convert {

    public static String dateEnglish (String dateOld) {

        SimpleDateFormat formatterOld = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatterNew = new SimpleDateFormat("yyyy-MM-dd");
        String dataFinal;

        try {
            Date date = formatterOld.parse(dateOld);
            dataFinal = formatterNew.format(date);

        } catch (Exception e) {
            dataFinal = "1900-01-01";
        }

        return dataFinal;
    }
}
