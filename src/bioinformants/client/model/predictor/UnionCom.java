package bioinformants.client.model.predictor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package ASIP.server;
import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 *
 * @author jose
 */
public class UnionCom {

    String lineas = "";
    /**
     * @param args
     */
    String headerfile, bodyfile;
    int lines_number;
    Vector genes;
    StringBuffer bodyString;
    String Taxon;
    StringBuffer c2w;
    StringBuffer c2c;
    BufferedReader body;
    int break_point;
    int limitGen;
    boolean enabled;
    int last_limit;
    StringBuffer body_crick;
    String locusgenes = "";
    boolean complement;

    //----------------------------------------------------------------------------------------------------------
    public String union(String GI, String root_url, boolean completos, boolean intronesGTAG, int limitGen, String modo) {
        try {
            Taxon = "";
            genes = new Vector();
            last_limit = 0;
            lines_number = 0;
            break_point = 0;
            //limitGen=4000;
            body_crick = new StringBuffer();

            FileReader fileHeader = new FileReader(root_url + "Data/" + GI + "/sequence_headers.ebi");
            BufferedReader headers = new BufferedReader(fileHeader);
            FileReader fileBody = new FileReader(root_url + "Data/" + GI + "/sequence_body.ebi");
            body = new BufferedReader(fileBody);
            int base_back_w;//posicion en donde quede del vector
            int base_back_c;//posicion en donde quede del vector
            c2w = new StringBuffer();
            c2c = new StringBuffer();
            //Vector Contig=new Vector();
            bodyString = new StringBuffer();
            String source_regex = "^SOURCE +";
            String source_regex1 = " +chromosome:";
            String genes_regex = " +gene +.+";
            String genes_regex1 = "";
            String genes_regex_aux = "";
            String contig_regex = "CONTIG +join";
            String alfa = "\\p{Alpha}+?";
            Pattern source_pattern = Pattern.compile(source_regex);
            Pattern source_pattern1 = Pattern.compile(source_regex1);
            Pattern genes_pattern = Pattern.compile(genes_regex);
            Pattern genes_pattern1;
            //Pattern contig_pattern = Pattern.compile(contig_regex);
            Pattern genes_pattern_aux;
            Pattern alfa_pattern = Pattern.compile(alfa);
            Matcher matcher;
            Matcher matcher_aux;
            Matcher matcher2;
            Matcher alfaCheck;
            boolean repeatfind = false, genesfind = false, contigfind = false, genesbreak = false;
            Gene gen_obj = new Gene();
            long time_init = System.currentTimeMillis();
            int cont = 0;
            String line = headers.readLine().substring(2);
            pro:
            while (line != null) {
                matcher = source_pattern.matcher(line);
                if (!repeatfind && matcher.find()) {
                    Taxon = matcher.replaceAll("");
                    repeatfind = true;
                }
                if (Taxon.isEmpty()) {
                    matcher = source_pattern1.matcher(line);
                    if (!repeatfind && matcher.find()) {
                        Taxon = matcher.replaceAll("");
                        repeatfind = true;
                    }
                }

                genes_regex = " +gene {2,}";
                genes_pattern = Pattern.compile(genes_regex);
                matcher = genes_pattern.matcher(line);
                String alfaS = genes_pattern.matcher(line).replaceAll("").replace("complement(", "").replace(")", "");
                alfaCheck = alfa_pattern.matcher(alfaS);
                boolean a = alfaCheck.find();

                if (matcher.find() && !alfaCheck.find()) {
                    genesfind = true;
                    cont++;
                    //System.out.print(cont+"\n");
                    gen_obj.setGenes(matcher.replaceAll(""));
                    genes_regex_aux = " +gene {2,}";
                    genes_regex1 = " +"+modo+" +(join\\()?(complement\\()*+";
                    //genes_regex1 = " +mRNA +(join\\()?(complement\\()*+";
                    //genes_regex1 = " +CDS +(join\\()?(complement\\()*+";
                    genes_pattern_aux = Pattern.compile(genes_regex_aux);
                    genes_pattern1 = Pattern.compile(genes_regex1);
                    //String pseudo=" +gene {2,}";
                    //Pattern pseudo_pattern=Pattern.compile(pseudo);
                    while (genesfind && (line != null)) {
                        line = headers.readLine();
                        if (line != null && line.length() > 2) {
                            line = line.substring(2);
                            System.out.print(gen_obj.getGenes() + " " + gen_obj.getLocusTag() + " **");
                            System.out.print("line=" + line + ". " + "\n");
                            matcher_aux = genes_pattern_aux.matcher(line);
                            matcher2 = genes_pattern1.matcher(line);
                            boolean match1 = matcher_aux.find();
                            boolean match2 = matcher2.find();
                            if (!match1) {
                                if (match2 && (line != null)) {
                                    String del_comple = "";
                                    del_comple = matcher2.replaceAll("").replace("complement(", "");
                                    gen_obj.addExones(del_comple.replace(")", ""));
                                    genes_regex = " +/[(locus_tag)(gene)]+=";
                                    genes_pattern = Pattern.compile(genes_regex);
                                    while (true) {
                                        String compl_regex = " +(complement\\()*+"; // Vega
                                        Pattern compl_pattern = Pattern.compile(compl_regex);
                                        line = headers.readLine().substring(2);
                                        if (line != null&&line.length() > 2) {
                                            matcher = genes_pattern.matcher(line);
                                            if (matcher.find()) {
                                                //System.out.print("SI\n");
                                                gen_obj.setLocusTag(matcher.replaceAll(""));
                                                locusgenes = locusgenes.concat(matcher.replaceAll("") + ";");
                                                break;
                                            }
                                        }
                                        //System.out.print(line);

                                        Matcher matcher1 = compl_pattern.matcher(line);
                                        del_comple = matcher1.replaceAll("").replace("complement(", "");
                                        gen_obj.addExones(del_comple.replace(")", ""));
                                    }
                                    genes.addElement(gen_obj);
                                    //System.out.print(gen_obj.getGenes()+" "+gen_obj.getLocusTag()+" "+gen_obj.getExones()+"\n");
                                    //System.out.print(gen_obj.getExones()+" ");
                                    gen_obj = new Gene();
                                    genesfind = false;
                                    //break pro;
                                }
                            } else {
                                genesbreak = true;
                                genesfind = false;
                                break;
                            }/*
                             else{
                             matcher = pseudo_pattern.matcher(line);
                             if(matcher.find()){
                             gen_obj=new gen_data();
                             genesfind=false;
                             break;
                             }
                             }*/
                        }//*/ Leo lineas hasta que genfind sea false (o por mRNA o por pseudogene)
                    }
                }

                if (line != null) {
                    //if ()
                    if (genesbreak) { // Hubo ruptura de analisis debido a que se hallo' otro gen
                        // sin que se hallara CDS para el anterior.
                        line = line.substring(2);
                        genesbreak = false;
                    } else {
                        lineas += line + "\n";
                        line = headers.readLine();
                        if (line != null&&line.length()>2) {// caso especial en que la ultima linea es un caracter en blanco.
                            line = line.substring(2);
                        }
                    }


                }
            }
            
            System.out.print((System.currentTimeMillis() - time_init) / 1000 + "\n");
            //System.out.print(contig.toString());
            headers.close();
            fileHeader.close();
            genes_regex = "[\\s[0-9]]+";
            genes_pattern = Pattern.compile(genes_regex);
            //int linenum=0;
            //Contig=new Vector(generate_contig(contig));
            reload_body(false); // Lee la hebra Watson parcialmente y genera dos buffers, uno con la watson y otro
            // con la crick. Esta ultima se lee de arriba hacia abajo y de derecha a izquierda.
				/*
             System.out.print((System.currentTimeMillis()-time_init)/1000+"\n");*/
            c2w.append("\nORIGIN\nwatson strand\n");
            c2c.append("\nORIGIN\ncrick strand\n");
            base_back_w = 1;
            base_back_c = 1;
            RandomAccessFile randFile = new RandomAccessFile(root_url + "Data/secuencias.xml", "rws");
            long pos = getPosGI(randFile, GI);
            boolean band = false;
            if (pos == randFile.length()) {
                pos = pos - "</Organism-Data>".length() - 1;
                band = true;
            }
            randFile.seek(pos);
            Calendar toDay = Calendar.getInstance(TimeZone.getDefault());
            String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
            sdf.setTimeZone(TimeZone.getDefault());
            String Line = "<Data Organism=\"" + Taxon + "\" GI=\"" + GI + "\" TimeStamp=\"" + sdf.format(toDay.getTime()) + "\" />\n";
            randFile.writeBytes(Line);
            if (band == true) {
                randFile.writeBytes("</Organism-Data>\n");
            }

            randFile.close();
            File creator = new File(root_url + "Data/" + GI + "/Watson/");
            creator.mkdir();
            creator = new File(root_url + "Data/" + GI + "/Watson/In/");
            creator.mkdir();
            creator = new File(root_url + "Data/" + GI + "/Crick/");
            creator.mkdir();
            creator = new File(root_url + "Data/" + GI + "/Crick/In/");
            creator.mkdir();
            PrintStream c2W = new PrintStream(root_url + "Data/" + GI + "/Watson/In/c2W.txt");
            PrintStream c2C = new PrintStream(root_url + "Data/" + GI + "/Crick/In/c2C.txt");
            int i = 0;
            int num_genes = (genes.size());
            //int num_genes=genes.size();
            System.out.print("Escribiendo hebra Watson" + "\n");
            for (i = 0; i < num_genes; i++) {// Se genera el archivo c2W.txt usando todo gen no complement
                gen_obj = new Gene();
                gen_obj = (Gene) genes.elementAt(i);//System.out.print(gen_obj.getGenes());
                Vector Bases;
                Bases = new Vector(generate_base(gen_obj.getGenes())); // Genera limites del gen gen_obj.
                gen_obj.setComplement(complement);
                gen_obj.setEnabled(enabled);
                gen_obj.addBase(Bases);//System.out.print(gen_obj.getBases(0).getBase()+" ");
                Bases = new Vector(generate_base(gen_obj.getExones())); // Genera limites de los exones para gen gen_obj
                gen_obj.addExon(Bases);
                genes.setElementAt(gen_obj, i); // Se guarda gen i en genes con limites bien definidos.
                String base;
                if (!gen_obj.isComplement() && gen_obj.isEnabled()) {
                    int base_back_wlast = 0, base_back_ag = 1, base_back_gt = 1;
                    Matcher inicio_matcher, stop_matcher, gt_matcher, ag_matcher;
                    System.out.print("Gen =" + gen_obj.getLocusTag() + "\n");
                    boolean completoDetectado = false, gtagDetectado = true;// 	completoDetectado verifica si el gen inicia en ATG y finaliza en (TAA,TGA,TAG).
                    String baseUlt = gen_obj.getExon((gen_obj.numExon() - 1)).getLimitSup();
                    //pos_sig_exon=null;
                    String regex = " +";
                    Pattern gtag_pattern = Pattern.compile(regex);
                    boolean toolong = false;

                    for (int k = 0; k < gen_obj.numExon(); k++) {
                        base = gen_obj.getExon(k).getLimitInf();

                        base_back_w = file_print(base, c2W, base_back_w); // Escribe en c2W base_back_w hasta limit inferior del exon.

                        if (k == 0) {// Si el gen a procesar es demasiado largo se debe saltar para que progol pueda procesarlo
                            int baseInf = Integer.valueOf(base).intValue();
                            int baseSup = Integer.valueOf(baseUlt).intValue();
                            if ((baseSup - baseInf) > limitGen) {
                                toolong = true;
                            }

                        }

                        /*if(k==0){
                         base_back_w=file_print(base,c2W,base_back_w); // Escribe en c2W desde base_back_w hasta base
                         }*/

                        if (k == 0 && intronesGTAG && !toolong) { // Se chequea si el gen tiene intrones del tipo GT->AG
                            for (int k1 = 0; k1 < (gen_obj.numExon() - 1); k1++) {
                                base = gen_obj.getExon(k1).getLimitSup(); // Se obtiene posicion superior del exon
                                base_back_gt = pos_last_exon(base, base_back_gt) + 1; // Se mueve el apuntador hasta posicion superior del exon
                                String GT = bodyString.substring(base_back_gt, (base_back_gt + 2)); // Se lee inicio de intron
                                // Se revisa si el GT o el AG estan entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                gt_matcher = gtag_pattern.matcher(GT); // Se compara con patron para ver si hay blanco incrustado
                                if (gt_matcher.find()) {
                                    GT = bodyString.substring(base_back_gt, (base_back_gt + 3)); // Se lee inicio intron de nuevo
                                    GT = GT.replaceAll(" ", "");
                                }//.. se ajusta inicioATG eliminando el espacio en blanco
                                if (GT.equalsIgnoreCase("gt")) {// Si el intron tiene inicio GT, entonces:
                                    base = gen_obj.getExon(k1 + 1).getLimitInf(); // Se busca limite inferior siguiente exon
                                    base_back_ag = pos_last_exon(base, base_back_gt); // Se mueve apuntador hasta inicio del siguiente exon
                                    String AG = bodyString.substring((base_back_ag - 2), (base_back_ag)); //Se lee senal AG
                                    // Se revisa si el AG esta entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                    Pattern ag_pattern = Pattern.compile(regex);
                                    ag_matcher = ag_pattern.matcher(AG);
                                    if (ag_matcher.find()) {
                                        AG = bodyString.substring((base_back_ag - 3), (base_back_ag));
                                        AG = AG.replaceAll(" ", "");
                                    }//.. se ajusta AG eliminando el espacio en blanco.
                                    if (!AG.equalsIgnoreCase("ag")) { // Si AG no es igual a 'ag' entonces hay que salir del chequeo
                                        gtagDetectado = false;
                                        break;
                                    }
                                } else {
                                    gtagDetectado = false;
                                    break; // Si GT no es igual a 'gt' entonces hay que salir del chequeo
                                }

                            } // Si el for termina normalmente entonces todo intron es del tipo GT->AG

                        }


                        //base_back_w=file_print(base,c2W,base_back_w); // Escribe en c2W desde base_back_w hasta base
                        if (k == 0 && !toolong) {
                            if (completos) {// Si solo se desean genes ATG->(TAA,TGA,TAG)
                                String inicioATG = bodyString.substring(base_back_w, (base_back_w + 3));
                                //baseUlt=gen_obj.getExon((gen_obj.numExon()-1)).getLimitSup();
                                // Se revisa si el ATG esta entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                String inicio_regex = " +";
                                Pattern inicio_pattern = Pattern.compile(inicio_regex);
                                inicio_matcher = inicio_pattern.matcher(inicioATG);
                                if (inicio_matcher.find()) {
                                    inicioATG = bodyString.substring(base_back_w, (base_back_w + 4));
                                    inicioATG = inicioATG.replaceAll(" ", "");
                                }//.. se ajusta inicioATG eliminando el espacio en blanco
                                if (inicioATG.equalsIgnoreCase("atg")) {
                                    base_back_wlast = pos_last_exon(baseUlt, base_back_w);
                                    String Stop = bodyString.substring((base_back_wlast - 2), (base_back_wlast + 1));
                                    // Se revisa si el Stop esta entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                    String stop_regex = " +";
                                    Pattern stop_pattern = Pattern.compile(stop_regex);
                                    stop_matcher = stop_pattern.matcher(Stop);
                                    if (stop_matcher.find()) {
                                        Stop = bodyString.substring((base_back_wlast - 3), (base_back_wlast + 1));
                                        Stop = Stop.replaceAll(" ", "");
                                    }//.. se ajusta Stop eliminando el espacio en blanco.
                                    if (Stop.equalsIgnoreCase("taa") || Stop.equalsIgnoreCase("tag") || Stop.equalsIgnoreCase("tga")) {
                                        completoDetectado = true;
                                    }
                                }
                            }
                        }

                        if (toolong) {
                            baseUlt = String.valueOf(Integer.valueOf(baseUlt).intValue() + 1);
                            base_back_w = file_print(baseUlt, c2W, base_back_w);
                            break; // Dado que se quiere imprimir solo genes completos se rompe el for y se va por el siguiente gen
                            // de la hebra Watson.
                        }

                        if ((completos && !completoDetectado) || (intronesGTAG && !gtagDetectado)) {
                            baseUlt = String.valueOf(Integer.valueOf(baseUlt).intValue() + 1);
                            base_back_w = file_print(baseUlt, c2W, base_back_w);
                            break; // Dado que se quiere imprimir solo genes completos se rompe el for y se va por el siguiente gen
                            // de la hebra Watson.
                        }

                        /*if(intronesGTAG&&!gtagDetectado){
                         baseUlt=String.valueOf(Integer.valueOf(baseUlt).intValue()+1);
                         base_back_w=file_print(baseUlt,c2W,base_back_w);
                         break; // Dado que se quiere imprimir solo intrones GT->AG se rompe el for y se va por el siguiente gen
                         // de la hebra Watson.
                         }*/

                        // Este trozo es el que normalmente imprime los exones del gen en el c2W.txt.
                        if (k == 0) {
                            c2w.append("\n\nstart " + gen_obj.getLocusTag() + "\n");
                        }
                        // Este trozo es el que normalmente imprime los exones de un gen en el c2W.
                        c2w.append("\nexon" + " " + (k + 1) + "\n");
                        base = String.valueOf(Integer.valueOf(gen_obj.getExon(k).getLimitSup()).intValue() + 1);
                        //System.out.print(gen_obj.getLocusTag()+" "+base+" "+"base_back="+base_back_w+"\n");
                        base_back_w = file_print(base, c2W, base_back_w);
                        if (k + 1 < gen_obj.numExon()) {
                            c2w.append("\n\nintron\n");
                        } else {
                            c2w.append("\n\nstop " + gen_obj.getLocusTag() + "\n");
                        }// Hasta aqui el trozo.
                        //if(k==0) break;
                    }
                }
                //System.out.print(gen_obj.getGenes());
                //if(i==0) break;

            }
            reload_body(true);
            c2w.append(bodyString.substring(base_back_w, bodyString.length()));
            //print_rest_body(c2W);
            //System.out.print((System.currentTimeMillis()-time_init)/1000+"\n");
            break_point = 0;
            System.out.print("Escribiendo hebra Crick" + "\n");
            //int num_genes_c=num_genes*4;
            for (i = 0; i < num_genes; i++) { // Se genera el archivo c2C.txt usando todo gen complement
                String base;
                int group_length = 10;
                gen_obj = new Gene();
                int index = (i);
                gen_obj = (Gene) genes.elementAt(num_genes - i - 1);
                //gen_obj=(gen_data)genes.elementAt(num_genes_c-index);
                if (gen_obj.isComplement() && gen_obj.isEnabled()) {
                    int base_back_clast = 0, base_back_ag = 1, base_back_gt = 1;
                    Matcher inicio_matcher, stop_matcher, gt_matcher, ag_matcher;
                    System.out.print("Gen =" + gen_obj.getLocusTag() + "\n");
                    boolean completoDetectado = false, gtagDetectado = true;
                    // completoDetectado verifica si el gen inicia en ATG y finaliza en (TAA,TGA,TAG).
                    String baseUlt = gen_obj.getExon((gen_obj.numExon() - 1)).getLimitInf();
                    //pos_sig_exon=null;
                    String regex = " +";
                    Pattern gtag_pattern = Pattern.compile(regex);
                    boolean toolong = false;
                    for (int k = 0; k < gen_obj.numExon(); k++) {
                        base = gen_obj.getExon(k).getLimitSup();
                        //System.out.print(gen_obj.getLocusTag()+" "+gen_obj.getExon(gen_obj.numExon()-k-1).getLimitSup());//+base+" "+"base_back="+base_back_c+"\n");
                        base_back_c = file_print_c(base, c2c, base_back_c);//  Imprime hasta base - 1, borra en body_crick lineas anteriores y deja base_back_c como nueva
                        //  primera base a imprimir.

                        if (k == 0) {// Si el gen a procesar es demasiado largo se debe saltar para que progol pueda procesarlo
                            int baseInf = Integer.valueOf(base).intValue();
                            int baseSup = Integer.valueOf(baseUlt).intValue();
                            if ((baseInf - baseSup) > limitGen) {
                                toolong = true;
                            }

                        }

                        if (k == 0 && intronesGTAG && !toolong) { // Se chequea si el gen tiene intrones del tipo GT->AG
                            for (int k1 = 0; k1 < (gen_obj.numExon() - 1); k1++) {
                                base = gen_obj.getExon(k1).getLimitInf(); // Se obtiene posicion superior del exon
                                base_back_gt = pos_first_exon_c(base, base_back_gt) + 1; // Se mueve el apuntador hasta posicion superior del exon
                                String GT = body_crick.substring(base_back_gt, (base_back_gt + 2)); // Se lee inicio de intron
                                // Se revisa si el GT o el AG estan entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                gt_matcher = gtag_pattern.matcher(GT); // Se compara con patron para ver si hay blanco incrustado
                                if (gt_matcher.find()) {
                                    GT = body_crick.substring(base_back_gt, (base_back_gt + 3)); // Se lee inicio intron de nuevo
                                    GT = GT.replaceAll(" ", "");
                                }//.. se ajusta inicioATG eliminando el espacio en blanco
                                if (GT.equalsIgnoreCase("gt")) {// Si el intron tiene inicio GT, entonces:
                                    base = gen_obj.getExon(k1 + 1).getLimitSup(); // Se busca limite inferior siguiente exon
                                    base_back_ag = pos_first_exon_c(base, base_back_gt); // Se mueve apuntador hasta inicio del siguiente exon
                                    String AG = body_crick.substring((base_back_ag - 2), (base_back_ag)); //Se lee senal AG
                                    // Se revisa si el AG esta entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                    Pattern ag_pattern = Pattern.compile(regex);
                                    ag_matcher = ag_pattern.matcher(AG);
                                    if (ag_matcher.find()) {
                                        AG = body_crick.substring((base_back_ag - 3), (base_back_ag));
                                        AG = AG.replaceAll(" ", "");
                                    }//.. se ajusta AG eliminando el espacio en blanco.
                                    if (!AG.equalsIgnoreCase("ag")) { // Si AG no es igual a 'ag' entonces hay que salir del chequeo
                                        gtagDetectado = false;
                                        break;
                                    }
                                } else {
                                    gtagDetectado = false;
                                    break; // Si GT no es igual a 'gt' entonces hay que salir del chequeo
                                }

                            } // Si el for termina normalmente entonces todo intron es del tipo GT->AG

                        }


                        if (k == 0 && !toolong) {
                            if (completos) {// Si solo se desean genes ATG->(TAA,TGA,TAG)
                                String inicioATG = body_crick.substring(base_back_c, (base_back_c + 3));
                                //baseUlt=gen_obj.getExon((gen_obj.numExon()-1)).getLimitInf();
                                // Se revisa si el ATG esta entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                String inicio_regex = " +";
                                Pattern inicio_pattern = Pattern.compile(inicio_regex);
                                inicio_matcher = inicio_pattern.matcher(inicioATG);
                                if (inicio_matcher.find()) {
                                    inicioATG = body_crick.substring(base_back_c, (base_back_c + 4));
                                    inicioATG = inicioATG.replaceAll(" ", "");
                                }//.. se ajusta inicioATG eliminando el espacio en blanco
                                if (inicioATG.equalsIgnoreCase("atg")) {
                                    base_back_clast = pos_first_exon_c(baseUlt, base_back_c) + 1;
                                    String Stop = body_crick.substring((base_back_clast - 3), (base_back_clast));
                                    // Se revisa si el Stop esta entre dos grupos de caracteres (contiene un blanco) y si es asi..
                                    String stop_regex = " +";
                                    Pattern stop_pattern = Pattern.compile(stop_regex);
                                    stop_matcher = stop_pattern.matcher(Stop);
                                    if (stop_matcher.find()) {
                                        Stop = body_crick.substring((base_back_clast - 4), (base_back_clast));
                                        Stop = Stop.replaceAll(" ", "");
                                    }//.. se ajusta Stop eliminando el espacio en blanco.
                                    if (Stop.equalsIgnoreCase("taa") || Stop.equalsIgnoreCase("tag") || Stop.equalsIgnoreCase("tga")) {
                                        completoDetectado = true;
                                    }
                                }
                            }
                        }

                        if (toolong) {
                            baseUlt = String.valueOf(Integer.valueOf(baseUlt).intValue() - 1);
                            base_back_c = file_print_c(baseUlt, c2c, base_back_c);
                            break; // Dado que el gen es muy grande no se procesa
                        }

                        if ((completos && !completoDetectado) || (intronesGTAG && !gtagDetectado)) {
                            baseUlt = String.valueOf(Integer.valueOf(baseUlt).intValue() - 1);
                            base_back_c = file_print_c(baseUlt, c2c, base_back_c);
                            break; // Dado que se quiere imprimir solo genes completos se rompe el for y se va por el siguiente gen
                            // de la hebra Crick.
                        }

                        /*if(intronesGTAG&&!gtagDetectado){
                         baseUlt=String.valueOf(Integer.valueOf(baseUlt).intValue()-1);
                         base_back_c=file_print_c(baseUlt,c2c,base_back_c);
                         break; // Dado que se quiere imprimir solo intrones GT->AG se rompe el for y se va por el siguiente gen
                         // de la hebra Crick.
                         }*/

                        // Este trozo es el que normalmente imprime los exones del gen en el c2W.txt.
                        if (k == 0) {
                            c2c.append("\n\nstart " + gen_obj.getLocusTag() + "\n");
                        }

                        c2c.append("\nexon" + " " + (k + 1) + "\n");
                        //base=String.valueOf(Integer.valueOf(gen_obj.getExon(gen_obj.numExon()-k-1).getLimitInf()).intValue()-1);
                        base = String.valueOf(Integer.valueOf(gen_obj.getExon(k).getLimitInf()).intValue() - 1);
                        //System.out.print(gen_obj.getLocusTag()+" "+gen_obj.getExon(gen_obj.numExon()-k-1).getBase());//base+" "+"base_back="+base_back_c+"\n");
                        base_back_c = file_print_c(base, c2c, base_back_c);
                        if (k + 1 < gen_obj.numExon()) {
                            c2c.append("\n\nintron\n");
                        } else {
                            c2c.append("\n\nstop " + gen_obj.getLocusTag() + "\n");
                        }
                    }
                }
                //if(i==0) break;
            }
            c2c.append(body_crick.substring(base_back_c, body_crick.length()));
            System.out.print((System.currentTimeMillis() - time_init) / 1000 + "\n");
            //System.out.print(bodyString.length()+" "+base_back);//System.out.print(locus_tag.toString()+"\n");System.out.print(genes.toString()+"\n");System.out.print(contig.toString()+"\n");
            c2C.print(c2c);
            c2W.print(c2w);
            c2C.close();
            c2W.close();
            body.close();
            fileBody.close();
            System.out.print((System.currentTimeMillis() - time_init) / 1000);
        } catch (IOException errormessage) {
            errormessage.printStackTrace();
        }
        return locusgenes;
    }

    //-----------------------------------------------------------------------------------------------
    public static long getPosGI(RandomAccessFile randFile, String GI) {

        String Line;
        long pos = 0;
        try {
            Line = randFile.readLine();

            Pattern pattern_GI = Pattern.compile(" GI=\"[0-9]+\"");
            while (Line != null) {
                Matcher matcher = pattern_GI.matcher(Line);
                if (matcher.find()) {
                    String GI_file = matcher.group();
                    if (GI_file.compareTo(" GI=\"" + GI + "\"") == 0) {
                        break;
                    }
                }
                pos = randFile.getFilePointer();
                Line = randFile.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pos;
    }

//		----------------------------------------------------------------------------------------------------------
    public void reload_body(boolean todo) {
        try {
            String line = body.readLine();
            StringBuilder part_crick = new StringBuilder();
            while (line != null) {
                Formatter f = new Formatter();
                line = f.format("%9d", ((lines_number * 60) + 1)).toString() + " " + line.substring(0, 70).trim();
                f.close();
                lines_number++;
                line += "\n";
                part_crick.append(line);
                bodyString.append(line);
                if (lines_number % 5000 == 0 && !todo) {
                    break;
                }
                Pattern p = Pattern.compile(" +[0-9]+ [ACGT ][0-9]");
                Matcher m = p.matcher(line);
                if (m.find()) {
                    int x = 10;
                }
                line = body.readLine();
                if (line != null) {
                    if (line.length() < 70) {
                        line = null;
                    }
                }
            }
            body_crick.insert(0, reverseComplement(part_crick.toString()));

        } catch (IOException errormessage) {
            errormessage.printStackTrace();
        }
    }

//		----------------------------------------------------------------------------------------------------------
    public void print_rest_body(PrintStream file_strand) {
        try {
            //while(todo){
            String line = body.readLine();
            //StringBuffer part_crick=new StringBuffer();
            while (line != null) {
                lines_number++;
                //part_crick.append(line+"\n");
                file_strand.append(line + "\n");
                /* if(lines_number%5000==0){
                 lines_number=0;
                 break;
                 }*/
                line = body.readLine();
            }
            //body_crick.insert(0,reverseComplement(part_crick.toString()));
            //file_strand.append(bodyString.substring(base_back_w,bodyString.length()));
            //bodyString=new StringBuffer();

        } catch (IOException errormessage) {
            errormessage.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------------------

    public StringBuffer reverseComplement(String strand_watson) {
        StringBuffer strand_crick = new StringBuffer();
        StringTokenizer lines = new StringTokenizer(strand_watson, "\n");
        while (lines.hasMoreTokens()) {
            String line = lines.nextToken();
            int large = line.length();
            StringBuilder solo_bases = new StringBuilder();
            StringBuffer completa = new StringBuffer();
            boolean space = false;
            for (int i = 0; i < large; i++) {
                char nucleotido = line.charAt(i);
                switch (nucleotido) {
                    case 'a':
                        solo_bases.append('t');
                        space = true;
                        break;
                    case 't':
                        solo_bases.append('a');
                        space = true;
                        break;
                    case 'g':
                        solo_bases.append('c');
                        space = true;
                        break;
                    case 'c':
                        solo_bases.append('g');
                        space = true;
                        break;
                    case 'A':
                        solo_bases.append('T');
                        space = true;
                        break;
                    case 'T':
                        solo_bases.append('A');
                        space = true;
                        break;
                    case 'G':
                        solo_bases.append('C');
                        space = true;
                        break;
                    case 'C':
                        solo_bases.append('G');
                        space = true;
                        break;
                    case ' ':
                        solo_bases.append(' ');
                        if (!space) {
                            completa.append(nucleotido);
                        }
                        break;

                    default:
                        completa.append(nucleotido);
                        break;
                }
            }
            completa.append(solo_bases.reverse());
            completa.append("\n");
            strand_crick.insert(0, completa);
        }
        return strand_crick;
    }
//		----------------------------------------------------------------------------------------------------------	

    public int file_print(String base, PrintStream file_strand, int base_back) { // Escribe en el archivo c2W desde base_back hasta base.
        int base_int = Integer.valueOf(base).intValue() - 1;
        int find_base = (base_int / 60); // define numero de lineas antes del limite definido por base
        find_base = find_base * 60; // Numero de bases contenidas en las lineas halladas en paso anterior
        String find_base_string = String.valueOf(find_base + 1); // Define etiqueta numeral justo en la linea siguiente
        int bases_groups = bodyString.indexOf(find_base_string); // Halla posicion en bodyString de la etiqueta ,
        // bodyString contiene el grupo de lineas de la hebra W leida en reload_body
        if (bases_groups == -1) {
            bodyString.delete(0, break_point);
            base_back = base_back - break_point;
            while (bases_groups == -1) {
                reload_body(false);
                bases_groups = bodyString.indexOf(find_base_string);
            }
            file_strand.print(c2w);
            c2w = new StringBuffer();

        }
        break_point = bases_groups;
        int ultimas = base_int - (find_base); // ultimas contiene el numero de bases restantes al eliminar
        // aquellas que estan contenidas en las lineas anteriores al limite base.
        // Es decir, el numero de bases en la linea, justo antes del limite base.

        ultimas = ultimas + ultimas / 10; // Agrega un espacio por cada modulo 10
        ultimas = ultimas + bases_groups + find_base_string.length() + 1; // Cantidad de caracteres justo hasta del limite base
        if (ultimas < base_back - 1) {
            //System.out.print(ultimas+" ");System.out.print(base_back-1);
        }
        System.out.print("base_back =" + " " + base_back + " " + "Limite=" + " " + base + " " + "bases_groups =" + " " + bases_groups + " " + "ultimas =" + " " + ultimas + " " + "find_base_string =" + find_base_string + "\n");
        c2w.append(bodyString.substring(base_back, ultimas));
        return ultimas;
    }
//		----------------------------------------------------------------------------------------------------------	

    public int file_print_c(String base, StringBuffer c2c, int base_back) {
        int base_int = Integer.valueOf(base).intValue() - 1;
        int find_base = (base_int / 60);
        find_base = find_base * 60;
        String find_base_string = String.valueOf(find_base + 1);
        int bases_groups = body_crick.indexOf(find_base_string); // Cantidad de caracteres hasta la etiqueta find_base_string
        break_point = bases_groups;
        String line = body_crick.substring(bases_groups, body_crick.indexOf("\n", bases_groups));
        String genes_regex = "[0-9 \n]+";
        Pattern genes_pattern = Pattern.compile(genes_regex);
        Matcher matcher = genes_pattern.matcher(line);
        int ultimas = base_int - (find_base);
        if (matcher.find()) {
            String line_bases_only = matcher.replaceAll("");
            ultimas = line_bases_only.length() - ultimas - 1;
        }

        ultimas = ultimas + ultimas / 10;
        ultimas = ultimas + bases_groups + find_base_string.length() + 1;
        System.out.print("base_back =" + " " + base_back + " " + "Limite=" + " " + base + " " + "bases_groups =" + " " + bases_groups + " " + "ultimas =" + " " + ultimas + " " + "find_base_string =" + find_base_string + "\n");
        c2c.append(body_crick.substring(base_back, ultimas));
        body_crick.delete(0, break_point);
        return ultimas - break_point;
    }

//		----------------------------------------------------------------------------------------------------------	
    public int pos_first_exon_c(String base, int base_back) {
        int base_int = Integer.valueOf(base).intValue() - 1;
        int find_base = (base_int / 60);
        find_base = find_base * 60;
        String find_base_string = String.valueOf(find_base + 1);
        int bases_groups = body_crick.indexOf(find_base_string); // Cantidad de caracteres hasta la etiqueta find_base_string
        String line = body_crick.substring(bases_groups, body_crick.indexOf("\n", bases_groups));
        String genes_regex = "[0-9 \n]+";
        Pattern genes_pattern = Pattern.compile(genes_regex);
        Matcher matcher = genes_pattern.matcher(line);
        int ultimas = base_int - (find_base);
        if (matcher.find()) {
            String line_bases_only = matcher.replaceAll("");
            ultimas = line_bases_only.length() - ultimas - 1;
        }

        ultimas = ultimas + ultimas / 10;
        ultimas = ultimas + bases_groups + find_base_string.length() + 1;
        System.out.print("base_back =" + " " + base_back + " " + "Limite=" + " " + base + " " + "bases_groups =" + " " + bases_groups + " " + "ultimas =" + " " + ultimas + " " + "find_base_string =" + find_base_string + "\n");
        //c2c.append(body_crick.substring(base_back,ultimas));
        //body_crick.delete(0,break_point);
        return ultimas;
    }

//		----------------------------------------------------------------------------------------------------------	
    public int pos_last_exon(String base, int base_back) { // Devuelve posicion ultimo exon en caracteres
        int base_int = Integer.valueOf(base).intValue() - 1;
        int find_base = (base_int / 60); // define numero de lineas antes del limite definido por base
        find_base = find_base * 60; // Numero de bases contenidas en las lineas halladas en paso anterior
        String find_base_string = String.valueOf(find_base + 1); // Define etiqueta numeral justo en la linea siguiente
        int bases_groups = bodyString.indexOf(find_base_string); // Halla posicion en bodyString de la etiqueta ,
        // bodyString contiene el grupo de lineas de la hebra W leida en reload_body
        if (bases_groups == -1) {
            /*bodyString.delete(0,break_point);
             base_back=base_back-break_point;*/
            while (bases_groups == -1) {
                reload_body(false);
                bases_groups = bodyString.indexOf(find_base_string);
            }

        }
        //break_point=bases_groups;
        int ultimas = base_int - (find_base); // ultimas contiene el numero de bases restantes al eliminar
        // aquellas que estan contenidas en las lineas anteriores al limite base.
        // Es decir, el numero de bases en la linea, justo antes del limite base.

        ultimas = ultimas + ultimas / 10; // Agrega un espacio por cada modulo 10
        ultimas = ultimas + bases_groups + find_base_string.length() + 1; // Cantidad de caracteres justo hasta el limite base
        if (ultimas < base_back - 1) {
            //System.out.print(ultimas+" ");System.out.print(base_back-1);
        }
        System.out.print("base_back =" + " " + base_back + " " + "Limite=" + " " + base + " " + "bases_groups =" + " " + bases_groups + " " + "ultimas =" + " " + ultimas + " " + "find_base_string =" + find_base_string + "\n");

        return ultimas;
    }

    //----------------------------------------------------------------------------------------------------------
    public Vector generate_base(String genes) {
        Vector Bases = new Vector(10, 2);//System.out.print("genes="+genes+" ");
        String genes_regex = "[(complement)\\(\\)]+";
        Pattern genes_pattern = Pattern.compile(genes_regex);
        Matcher matcher = genes_pattern.matcher(genes);
        complement = false;
        enabled = false;
        if (matcher.find()) {
            complement = true;
            genes = matcher.replaceAll("");
        }
        StringTokenizer gen = new StringTokenizer(genes, ",");
        while (gen.hasMoreTokens()) {
            //StringTokenizer contigs = new StringTokenizer(contig.toString(),",");
            //int num_contig=contig.size();
            String bb = gen.nextToken();
            Base base_obj = new Base();
            String gen_limits = bb;//System.out.print(gen_limits);
            genes_regex = "[\\((\\.\\.)>\\)]+";
            genes_pattern = Pattern.compile(genes_regex);
            gen_limits = gen_limits.replace("<", "");
            matcher = genes_pattern.matcher(gen_limits);
            if (matcher.find()) {

                //System.out.print(gen_limits+" ");
                String gen_limit[] = matcher.replaceAll(" ").split(" ");
                base_obj.setLimits(gen_limit);

                try {
                    int limit_inf_obj = Integer.valueOf(base_obj.getLimitInf()).intValue();
                    if (limit_inf_obj > last_limit) {
                        enabled = true;
                        last_limit = Integer.valueOf(base_obj.getLimitSup()).intValue();
                    }
                    Bases.add(base_obj);//base_back=base;
                    //System.out.print(gen_limit[0]+" "+gen_limit[1]);//gen_limit[0]+" "+gen_limit[1]+" "+gen_limit[2]);
                    //System.out.print(bodyString.substring(0,10));
                } catch (Exception ex) {
                }
            }


        }
        return Bases;
    }
//		----------------------------------------------------------------------------------------------------------

    public Vector generate_contig(StringBuffer contig) {
        Vector Contig = new Vector(10, 2);
        String contig_regex = "[A-Z]{1,2}[0-9.:]{0,9}";
        Pattern contig_pattern = Pattern.compile(contig_regex);
        StringTokenizer contigs = new StringTokenizer(contig.toString(), ",");
        Matcher matcher;
        while (contigs.hasMoreTokens()) {
            matcher = contig_pattern.matcher(contigs.nextToken());
            if (matcher.find()) {
                Base cont = new Base();
                String contig_code;
                contig_code = matcher.group();
                String bases[] = matcher.replaceAll("").split("\\.\\.");
                cont.setBase(contig_code);
                cont.setLimits(bases);
                Contig.add(cont);
            }
        }
        return Contig;
    }
}
