/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public class Combinar {

    HashMap<String, PrintStream> archivos = new HashMap<String, PrintStream>();
    public String donde;

    public Combinar(String donde) {
        this.donde = donde;
    }

    PrintStream getArchivos(String key) throws FileNotFoundException {
        if (archivos.get(key) == null) {
            File s = new File(donde + key.substring(0, key.lastIndexOf("/")));
            s.mkdirs();
            archivos.put(key, new PrintStream(new File(donde + key)));
        }
        return archivos.get(key);
    }

    void cerrarTodos() {
        for (PrintStream printWriter : archivos.values()) {
            printWriter.close();
        }
        archivos.clear();

    }

//    public static void main(String[] args) {
//        Combinar s = new Combinar("/home/adrian/Tesis5.6/OutPut/Watson/");
//        s.data("/home/adrian/Tesis5.6/Data/");
//        s.data("/home/adrian/Tesis5.6/prueba_temp/");
//    }

    public void data(String path) {
        File f = new File(path);
        File[] e = f.listFiles();
        if (e != null) {
            for (int i = 0; i < e.length; i++) {
                File file = e[i];
                if (file.isDirectory()&&  file.getName().matches("^[0-9|X|Y].*")) {
                    File[] fol = file.listFiles();
                    if (fol != null) {
                        for (int j = 0; j < fol.length; j++) {
                            File car = fol[j];
                            if (car.isDirectory()
                                    && (car.getName().compareTo("Watson") == 0
                                    || car.getName().compareTo("Crick") == 0)) {
                                File[] todo = car.listFiles();
                                for (int k = 0; k < todo.length; k++) {
                                    File dato = todo[k];
                                    llenarNuevos(dato, car.getName() + "/" + dato.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void llenarNuevos(File dato, String name) {

        if (dato.isDirectory()) {
            File inner[] = dato.listFiles();
            for (int k = 0; k < inner.length; k++) {
                File data = inner[k];
                llenarNuevos(data, name + "/" + data.getName());
            }
        } else if (name.endsWith("txt")
                || (name.endsWith("pl") && name.contains("duplex"))) {
            try {
                System.out.println(dato.toString());
                BufferedReader b = new BufferedReader(new FileReader(dato));
                PrintStream imp = getArchivos(name);
                String lin = "";
                while ((lin = b.readLine()) != null) {
                    imp.println(lin);
                }

            } catch (IOException ex) {
                Logger.getLogger(Combinar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}
