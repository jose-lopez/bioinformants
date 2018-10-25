/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioinformants.client.model.predictor.experimento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Casa
 */
public class DataOrganizer {

    /**Labels que se encuentran en el Predictor*/
    public static final String[] tags = {"source", "mRNA", "CDS", "gene", "misc_RNA", "exon", "BASE", "ORIGIN",
        "variation", "misc_feature", "repeat_region", "STS"
    };
    private String lastTag = "XX", tag = "";

    /**
    Constructor de la clase
    <br/>
    Organiza la data
    <li>Crea un directorio con el nombre del cromosoma de la descripcion del mismo</li>
    <li>Header.ebi.xml Contiene la cabecera del Cormosoma</li>
    <li>Body.gb Contiene cuerpo del cromosoma</li>
     * @param fileName Nombre del Archivo a decodificar en formato GenBank
     */
    public void crearCabeceraXML(String root_url) {
        
        BufferedReader b = null;
        try {
            b = new BufferedReader(new FileReader(new File(root_url + "/sequence_headers.ebi")));
            String linea = "";
            PrintWriter p = new PrintWriter(new File(root_url+"/Header.ebi.xml"));
            linea = b.readLine();
//            boolean sw = true;
            do {
                    encodeToXML(linea, p);
                
//                if (sw && linea.startsWith("DE")) {
//                    sw = false;
//                    linea = linea.substring(2).trim();
//                    url = new File(path + "/" + linea);
//                    if (url.exists()) {
//                        File f = new File(url, "Header.ebi.xml");
//                        if (f.exists()) {
//                            f.delete();
//                        }
//                    }
//                    url.mkdirs();
//                }
                linea = b.readLine();
            } while (!linea.startsWith("ORIGIN") && !linea.startsWith("SQ"));
            p.close();
//            File file = new File("Header.ebi.xml");
//            System.out.println(file.exists());
//            File outFile = new File(url, file.getName());
//            copyFile(file, outFile);
//
//            p = new PrintWriter(new File(url, "Body.gb"));
//            int i = 1;
//            while ((linea = b.readLine()) != null) {
//                linea = linea.replaceAll("[0-9]", "").trim();
//                p.format("%9d %s\n", i, linea);
//                i += 60;
//            }
//            p.close();
        } catch (Exception ex) {
            Logger.getLogger(DataOrganizer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                b.close();
            } catch (IOException ex) {
                Logger.getLogger(DataOrganizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   
    private void encodeToXML(String linea, PrintWriter p) {
        if (!linea.startsWith(lastTag)) {
            if (!lastTag.equals("XX")) {
                if(!tag.isEmpty()&&tag.compareTo(lastTag)!=0)
                {p.println("\t</" + tag + ">");}
                p.println("</" + lastTag + ">");
            }
            lastTag = linea.substring(0, 2);
            if (!lastTag.equals("XX")) {
                p.println("<" + lastTag + ">");
            }
        }
        if (linea.startsWith("FT")) {
            String ln = linea.substring(2).trim();
            int pos = isTag(ln);
            if (pos != -1) {
                if (!tag.isEmpty()) {
                    p.println("\t</" + tag + ">");
                }
                tag = tags[pos];
                p.println("\t<" + tag + ">");
                linea = linea.replaceFirst(tag, "").trim();
            }
            p.print("\t");
        }
        p.println("\t" + linea.substring(2).trim());


    }

    /**
     * Revisa si la linea es un nuevo tag
     * @param linea String
     */
    private int isTag(String linea) {
        for (int i = 0; i < tags.length; i++) {
            if (linea.startsWith(tags[i] + "  ")) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Copia un solo archivo
     * @param s File origen
     * @param t File destino
     * @throws IOException
     */
    private void copyFile(File s, File t) {
        try {
            FileChannel in = (new FileInputStream(s)).getChannel();
            FileChannel out = (new FileOutputStream(t)).getChannel();
            in.transferTo(0, s.length(), out);
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
