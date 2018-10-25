package bioinformants.client.model.predictor.experimento;

//TODO TOMAR EN CUENTA LOS GENES QUE COMIENSAN CON UN JOIN
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneGeter {

    public GeneGeter() {
    }

    /**
     * crea la cabecera XML el archivo de genes
     * @param GI String con el identificador del Gen
     * @param n Banda de caracteres al rededor del gen
     */
    public void obtenerGenes(String root_url, int n, String GI) {
        new DataOrganizer().crearCabeceraXML(root_url);
        LectorXEBI xEBI = new LectorXEBI(root_url, false, true, true, 00);
//        System.out.println("Genes Encontrados: " + xEBI.genes.size());

        PrintWriter p = null;
        try {
            p = new PrintWriter(new File(root_url + "/" + GI + ".txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneGeter.class.getName()).log(Level.SEVERE, null, ex);
        }

        printGenes(xEBI, p, n);
        p.close();

    }

    public void printGenes(LectorXEBI xEBI, PrintWriter p) {

        for (int i = 0; i < xEBI.genes.size(); i++) {
            Gen gene = xEBI.genes.get(i);
            p.println("________________________________________________________________");
            gene.setAdn(xEBI.chromosomeBody, 0, 0);
            p.println("gene: " + gene.getGenes());
            p.println("locus_tag: " + gene.getLocus_tag());
            p.println("nota: " + gene.getNota());
            p.println(gene.toString());
            p.println("EXONES:");
            for (int j = 0; j < gene.getSplice().size(); j++) {
                p.println("Splice " + j + ":");
                for (int k = 0; k < gene.getSplice().get(j).getExones().size(); k++) {
                    p.print(gene.getSplice().get(j).getExones().get(k).complement ? "~" : "");
                    p.println(gene.getSplice().get(j).getExones().get(k).base);

                    p.println(gene.getSplice().get(j).getExones().get(k).limInf + "," + gene.getSplice().get(j).getExones().get(k).limSup);
                }
            }

        }


    }

    public void printGenes(LectorXEBI xEBI, PrintWriter p, int n) {
        for (int i = 0; i < xEBI.genes.size(); i++) {
            Gen gene = xEBI.genes.get(i);
            boolean sw = true;
            int aux = n;

            do {
                try {
                    gene.setAdn(xEBI.chromosomeBody, aux, aux);
                    sw = true;
                } catch (Exception ex) {
                    sw = false;
                    aux--;
                    if (aux < 1) {
                        sw = true;
                    }
                }
            } while (!sw);

            if (gene.getInicio() == null) {
                continue;
            }
            int ini = gene.getInicio();
            int fin = (gene.getParada() - gene.getInicio()) + aux;
            String r = "";
            r += ("([" + gene.getGenes() + "],");
            r += ("[" + aux + "],[" + fin + "],");
            String adn = gene.getAdn().toLowerCase();//.replaceAll("", ",");
            r += "[" + adn//.substring(1, adn.length() - 1)
                    + "],";
            r += "[" + xEBI.chromosome + "],[" + (xEBI.start + ini - aux) + "],["
                    + (xEBI.start + gene.getParada() + aux) + "],"+gene.isComplemented()+")";
            p.println(r);
//            System.out.println(r);
            for (int j = 0; j < gene.getSplice().size(); j++) {
                String out = "(";
                ArrayList<Part> exones = gene.getSplice().get(j).getExones();
                for (int k = 0; k < exones.size(); k++) {
                    out += ("[" + (exones.get(k).limInf - ini + aux) + ","
                            + (exones.get(k).limSup - ini + aux) + "],");
                }
                p.println(out + "[" + (gene.getSplice().size()) + "])");
//                System.out.println(out + "[" + (gene.getSplice().size()) + "])");
            }
//([ENSG00000131d069],[1000],[56820],[a,g,g,g,a,g...,g,g])
//([1000,1257],[11648,11844],[28])


        }


    }
}
