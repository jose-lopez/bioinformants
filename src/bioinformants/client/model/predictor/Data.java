package bioinformants.client.model.predictor;

import bioinformants.client.model.predictor.experimento.GeneGeter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.Combinar;

public class Data {

    static final String FILEPATH = System.getenv("WORKSPACE");//new File("").getAbsolutePath();

    private static void FromFiles(String longZI, String modo) {
        File[] v = new File(FILEPATH + "/Data").listFiles();
        if (v != null) {
            for (int i = 0; i < v.length; i++) {
                if (v[i].isDirectory()) {
                //if (v[i].isDirectory() && v[i].getName().matches("^[0-9|X|Y].*")) {
                    try {
                        final String x = v[i].getName();
                        new Thread(new Runnable() {

                            public void run() {
                                Data d = new Data(); //"1877429";//51511750
                                d.init(x,longZI,modo);
                            }
                        }).start();
                    } catch (Exception e) {
                        Logger.getLogger(Data.class.getName()).log(Level.SEVERE, "Data " + v[i].getName(), e);
                    }
                }
            }
        }
    }

    void init(String GI,String longZI, String modo) {
        
        //   new SequencesCreator(GI);
        
        String root_url = FILEPATH + "/";
        int cant_bases = Integer.parseInt(longZI);
        GeneGeter geneGeter = new GeneGeter();
        geneGeter.obtenerGenes(root_url + "Data/" + GI, cant_bases, GI);
        UnionCom unionData = new UnionCom();//TODO poner parametros en la interface
        String locustag = unionData.union(GI, root_url, false, true, 10000, modo);
        System.out.println(GI + "   " + locustag);
        StrandsExtractor extractor = new StrandsExtractor();
        extractor.extractor(GI, root_url);
        System.out.println("StrandsExtractor");
        StrandsExamples zonasLongi = new StrandsExamples();
        zonasLongi.ejemplos(GI, cant_bases, root_url);
        System.out.println("StrandsExamples");
        StrandsContraExamples contraejemplos = new StrandsContraExamples();
        contraejemplos.contraejemplos(GI, cant_bases, root_url);
        System.out.println("StrandsContraExamples");
        /*         
        
        String[] requestParam = ("Inicio;" + GI + ";true;" +cant_bases+";true;true;true;true;true;true;" + GI + ";true;true;true;true;true;true;true;true;true;false;true;true;true;true;true;true;true;true;true;true;true;true").split(";");
//      String[] requestParam = ("Inicio;" + GI + ";true;100;true;true;true;true;true;true;" + GI + ";true;true;true;true;true;true;true;true;true;false;true;true;true;true;true;true;true;true;true;true;true;true").split(";");
        datos_entrada_motor data = new datos_entrada_motor();
        data.datos_entrada(GI, cant_bases, root_url, "prueba", requestParam);
        /**
         * *****************
         */

    }

    /**
     *
     * @param args
     *
     */
    public static void main(String[] args) throws FileNotFoundException {
        FromFiles(args[0],args[1]);
        System.out.println("COMBINANDO");
        //Ruta donde se guardaran los archivos combinados
        Combinar com = new Combinar(FILEPATH + "/combinados/datos/");
        //Ruta donde se encuentran los documentos a combinar
        com.data(FILEPATH + "/Data");
        System.out.println("TERMINO DE COMBINAR");

    }
}
