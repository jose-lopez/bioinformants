package bioinformants.client.model.predictor.experimento;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package ASIP.server;
import java.io.*;
import java.util.ArrayList;

/**
 * Lector de los archivos xml de EBI
 * @author Luis Adrian Gonzalez Benavides
 */
public class LectorXEBI {

    ArrayList<Gen> genes = new ArrayList<Gen>();
    BufferedReader bodyReader, headersReader;
    StringBuffer chromosomeBody;
    long start = 0;
    long end = 0;
    String chromosome;

    /**7-115597756-117475183
     * Contructor de la clase se carga la data y se sacan la informacion
     */
    public LectorXEBI(String root_url, boolean completos, boolean intronesGTAG, boolean readMiscRNA, int limitGen) {
        try {
            headersReader = new BufferedReader(new FileReader(root_url + "/Header.ebi.xml"));
            bodyReader = new BufferedReader(new FileReader(root_url + "/sequence_body.ebi"));
            loadBody();
            readHeaders(readMiscRNA);

        } catch (IOException errormessage) {
            errormessage.printStackTrace();
        }
    }

    /** 
     * LoadBody
     */
    private void loadBody() throws IOException {

        chromosomeBody = new StringBuffer();
        String linea = "";

        while ((linea = bodyReader.readLine()) != null) {
            linea = (linea).toUpperCase().replaceAll("[^(A-Z)]", "");
            chromosomeBody.append(linea);
        }

    }

    /**
     * Lee la cabecera del Cromosoma
     */
    private void readHeaders(boolean sw) throws IOException {
        String linea = "";
        while ((linea = headersReader.readLine()) != null) {
            if (linea.compareTo("<AC>") == 0) {
                leerAC(linea);
            } else if (linea.compareTo("\t<gene>") == 0) {
                leerGen(linea);
            } else if (linea.equalsIgnoreCase(
                    "\t<mRNA>")) {
//                    "\t<CDS>")) {
                leerMRNA(false);
            }// else if (sw && linea.equalsIgnoreCase(
//                    "\t<misc_RNA>")) {
//                leerMRNA(true);
//            }
            }

    }

    private void leerAC(String linea) throws IOException{
        linea = headersReader.readLine();
        String[] cromosoma = linea.split(":");
        start = Long.parseLong(cromosoma[3]);
        end = Long.parseLong(cromosoma[4]);
        chromosome = cromosoma[2];
        linea = headersReader.readLine();

    }

    /**
     * lee cuando es un gen
     */
    private void leerGen(String linea) throws IOException {
        String aux = "";
        linea = headersReader.readLine();

        do {
            aux += linea + "\n";
            linea = headersReader.readLine();
        } while (linea.compareTo("\t</gene>") != 0);


        if (aux.indexOf("join") == -1) {
            Gen g = new Gen();
            g.setCodigo(aux);
            g.decodeGen();
            genes.add(g);


        }

    }

    /**
     *lee y carga mRNA o misc y lo asigna a un gen
     *
     */
    private void leerMRNA(boolean sw) throws IOException {
        String aux = "";
        String linea = headersReader.readLine();


        do {
            aux += linea + "\n";
            linea = headersReader.readLine();
//        } while (sw ? !linea.equalsIgnoreCase("\t</misc_RNA>") : !linea.equalsIgnoreCase("\t</mRNA>"));
        } while (!linea.equalsIgnoreCase("\t</CDS>"));

        int pos = aux.indexOf("/gene");


        if (pos != -1) {
            linea = aux.substring(0, pos).trim();
            aux = aux.substring(pos);
            Splice s = new Splice();


            int sa = aux.indexOf("\n");
            s.setGenId(aux.substring(7, sa - 1));
            s.setNotas(aux.substring(1 + sa));
            join(
                    s, linea);
            pos = genPosicion(genes, s.getGenId());


            if (pos != -1) {
                Gen gen = genes.get(pos);
                gen.getSplice().add(s);


            }

        }
    }

    /**
     * lee y separa los joins
     */
    private void join(Splice s, String linea) {
        if (linea.startsWith("join(")) {
            linea = linea.substring(5, linea.length() - 1);


        }
        String[] Exon = linea.split(",");
        s.setExones(new ArrayList<Part>(0));


        for (int i = 0; i
                < Exon.length; i++) {
            Exon[i] = Exon[i].trim();


            boolean sw = false;


            if (Exon[i].startsWith("complement(")) {
                Exon[i] = Exon[i].substring(11, Exon[i].length() - 1);
                sw = true;


            }
            s.getExones().add(new Part(Exon[i], sw));


        }
    }

    private int genPosicion(ArrayList<Gen> genes, String genId) {
        for (int i = 0; i
                < genes.size(); i++) {
            if (genes.get(i).getGenes().equals(genId)) {
                return i;


            }
        }
        return -1;

    }
}
