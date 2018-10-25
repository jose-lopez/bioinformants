/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public class Pruebas {
//"/home/adrian/Escritorio/salida/Experimentos"

//    public static void main(String[] args) {
//        new Pruebas().tester("/home/jose/Escritorio/OutPut_500000_noCOMPLETOS/Experimentos", 70, 30);
//    }
/**
 * 
 * @param ruta
 * @param entrenamiento
 * @param pruebas 
 */
    public void tester(String ruta, int entrenamiento, int pruebas) {
        File fs = new File(ruta + "_prueba");
        fs.mkdir();
        File[] x = new File(ruta).listFiles();
        for (int i = 0; i < x.length; i++) {
            BufferedReader b = null;
            try {
                File f = x[i];
                int max = 100000;
                if (f.getName().indexOf("duplex") != -1) {
                    if (f.getName().indexOf("test") != -1) {
                        max = pruebas;
                    } else {
                        max = entrenamiento;
                    }
                }
                b = new BufferedReader(new FileReader(f));
                PrintStream p = new PrintStream(new File(fs, f.getName()));
                String lin = "";
                while ((lin = b.readLine()) != null && max > 0) {
                    if (max > 70 || funciona(lin)) {
                        p.println(lin);
                        max--;
                    }
                }
                p.close();
            } catch (IOException ex) {
                Logger.getLogger(Pruebas.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    b.close();
                } catch (IOException ex) {
                    Logger.getLogger(Pruebas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean funciona(String lin) {

        if (lin.trim().isEmpty()) {
            return false;
        }
        String li = "";
        try {
            li = lin.split("]")[1];
            li = li.replaceAll(",", "");
            li = li.replaceAll("\\)\\.", "");
            li = li.replaceAll("izq", "");
            li = li.replaceAll("der", "");
        } catch (Exception e) {
            return false;
        }
        li = li.replaceAll("", ",");
        if (li.length() == 9) {
            if (!li.contains("p,t,a,a")
                    && !li.contains("p,t,g,a")
                    && !li.contains("p,t,a,g")
                    && !li.contains("p,a,t,g")) {
                return false;
            }
        }
        if (li.length() == 7) {
            if (!li.contains("p,a,g")
                    && !li.contains("p,g,t")) {
                return false;
            }
        }
        return lin.contains(li);
    }
}
