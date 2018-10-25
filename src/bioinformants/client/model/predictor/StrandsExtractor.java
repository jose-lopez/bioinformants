package bioinformants.client.model.predictor;

//package ASIP.server;
import java.io.*;
import java.util.*;
import java.util.regex.*;

//import javax.servlet.ServletException;
//import javax.servlet.http.*;
//import javax.servlet.*;
public class StrandsExtractor {

    public void extractor(String GI, String root_url) {
        try {
            int hebra = 0;
            String directorio = root_url + "Data/" + GI + "/";
            String hebras[] = {"Watson", "Crick"};
            char inihebra[] = {'W', 'C'};
            while (hebra < 2) {
                File dir = new File(directorio + hebras[hebra] + "/Out");
                dir.mkdir();
                dir = new File(directorio + hebras[hebra] + "/Ejemplos");
                dir.mkdir();
                //Busca la palabra strand, para guardar solo las bases biologicas
                //elimina lineas en blanco
                //elimina la numeracion de las bases biologicas
                PrintStream NUEVO = new PrintStream(directorio + hebras[hebra] + "/Out" + "/temp1" + inihebra[hebra] + ".txt");
                FileReader FileIn = new FileReader(directorio + hebras[hebra] + "/In" + "/c2" + inihebra[hebra] + ".txt");
                BufferedReader TEMPORAL2 = new BufferedReader(FileIn);
                String nueva_linea = "";
                int noguardar = 0;
                StringBuffer tem1 = new StringBuffer();
                int stoping = 0;
                String line = "";
                int ya = 0;
                while ((line = TEMPORAL2.readLine()) != null) {
                    if (ya == 1) {
                        if (exp_reg(line, "(^ |^[TCAG]|^[tcga])")) {
                            if (exp_reg(line, "^+\\s")) {
                                if (line.length() < 10) {
                                    noguardar = 1;
                                } else {
                                    nueva_linea = line.substring(10);
                                }
                            } else {
                                nueva_linea = line;
                            }
                            if (noguardar == 0) {
                                tem1.append(nueva_linea + "\n");
                            }
                            noguardar = 0;
                        } else {
                            if (exp_reg(line, "(^start|^stop|^exon|^intron)")) {
                                if (exp_reg(line, "^+\\s")) {
                                    if (line.length() < 10) {
                                        noguardar = 1;
                                    } else {
                                        nueva_linea = line.substring(10);
                                    }
                                } else {
                                    nueva_linea = line;
                                }
                                if (noguardar == 0) {
                                    tem1.append(nueva_linea + "\n");
                                }
                                noguardar = 0;
                            }
                        }
                    }
                    if (exp_reg(line, "\\bstrand\\b")) {
                        ya = 1;
                    }

                    stoping++;
                    if (stoping > 5000) {
                        NUEVO.print(tem1);
                        tem1.delete(0, tem1.length());
                        stoping = 0;
                    }
                }
                NUEVO.print(tem1);
                tem1.delete(0, tem1.length());
                NUEVO.close();
                TEMPORAL2.close();
                //Une en una sola linea las bases de los exones e intrones
                PrintStream MAS_NUEVO = new PrintStream(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp1" + inihebra[hebra] + ".txt");
                BufferedReader NUEVO1 = new BufferedReader(FileIn);
                StringBuffer tem2 = new StringBuffer();
                stoping = 0;
                while ((line = NUEVO1.readLine()) != null) {
                    String regex = "\\s";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(line);
                    line = matcher.replaceAll("");
                    if (exp_reg(line, "^start")) {
                        tem2.append("\n" + "\n" + line + "\n");
                    }
                    if (exp_reg(line, "^stop")) {
                        tem2.append("\n" + line + "\n" + "\n");
                    }
                    if (exp_reg(line, "^exon")) {
                        tem2.append("\n" + line + "\n");
                    }
                    if (exp_reg(line, "^intron")) {
                        tem2.append("\n" + line + "\n");
                    }
                    if (!exp_reg(line, "(^intron|^exon|^start|^stop)")) {
                        regex = "";
                        pattern = Pattern.compile(regex);
                        matcher = pattern.matcher(line);
                        line = matcher.replaceAll(",");
                        tem2.append(line.substring(1));
                    }
                    stoping++;
                    if (stoping > 5000) {
                        MAS_NUEVO.print(tem2);
                        tem2.delete(0, tem2.length());
                        stoping = 0;
                    }
                }
                MAS_NUEVO.print(tem2);
                tem2.delete(0, tem2.length());
                MAS_NUEVO.close();
                NUEVO1.close();
                //Separa en dos archivos (intrones y exones), separando las bases por comas entre corchetes, convierte en minuscalas las bases
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                BufferedReader SEPARADOS = new BufferedReader(FileIn);
                PrintStream INTRONES = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/intrones" + inihebra[hebra] + ".txt");
                PrintStream EXONES = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/exones" + inihebra[hebra] + ".txt");
                PrintStream ZONAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/zonas" + inihebra[hebra] + ".txt");
                String last_line = "";
                StringBuffer exons = new StringBuffer();
                StringBuffer introns = new StringBuffer();
                StringBuffer zonas = new StringBuffer();
                String linezona = "";
                stoping = 0;
                while ((line = SEPARADOS.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (exp_reg(last_line, "^exon")) {
                            exons.append("[p");
                            exons.append("," + line.substring(0, line.length() - 1));
                            exons.append("]" + "\n");
                        }
                        if (exp_reg(last_line, "^intron")) {
                            introns.append("[p");
                            introns.append("," + line);
                            introns.append("p,a,g]" + "\n");
                        }
                        if (exp_reg(line, "^start") && linezona.length() != 0) {
                            zonas.append("[");
                            zonas.append(linezona.substring(0, linezona.length() - 1));
                            zonas.append("]" + "\n");
                        }
                    }
                    linezona = last_line;
                    last_line = line;
                    stoping++;
                    if (stoping > 500) {
                        INTRONES.print(introns);
                        introns.delete(0, introns.length());
                        EXONES.print(exons);
                        exons.delete(0, exons.length());
                        ZONAS.print(zonas);
                        zonas.delete(0, zonas.length());
                        stoping = 0;
                    }
                }
                INTRONES.print(introns);
                introns.delete(0, introns.length());
                EXONES.print(exons);
                exons.delete(0, exons.length());
                ZONAS.print(zonas);
                zonas.delete(0, zonas.length());
                ZONAS.close();
                INTRONES.close();
                EXONES.close();
                SEPARADOS.close();
                //
                PrintStream TRIPLETAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/duplex" + inihebra[hebra] + "_EI.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                BufferedReader UNA_VEZ = new BufferedReader(FileIn);
                int intron = 0;
                String last = "";
                StringBuffer duple_EI = new StringBuffer();
                stoping = 0;
                while ((line = UNA_VEZ.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (exp_reg(last, "^intron")) {
                            duple_EI.append("p");
                            duple_EI.append("," + line.substring(0, line.length() - 1));
                            duple_EI.append("]\n");
                            intron = 0;
                            if (stoping > 5000) {
                                TRIPLETAS.print(duple_EI);
                                duple_EI.delete(0, duple_EI.length());
                                stoping = 0;
                            }
                        }
                        if (exp_reg(line, "^intron")) {
                            duple_EI.append("[");
                            duple_EI.append(last);
                            intron = 1;
                        }
                    }
                    last = line;
                    stoping++;
                }
                TRIPLETAS.print(duple_EI);
                TRIPLETAS.close();
                UNA_VEZ.close();
                //
                TRIPLETAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/duplex" + inihebra[hebra] + "_IE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                UNA_VEZ = new BufferedReader(FileIn);
                intron = 0;
                last = "";
                StringBuffer duple_IE = new StringBuffer();
                stoping = 0;
                while ((line = UNA_VEZ.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (intron == 1 && exp_reg(last, "^exon")) {
                            duple_IE.append("," + line.substring(0, line.length() - 1));
                            duple_IE.append("]\n");
                            if (stoping > 5000) {
                                TRIPLETAS.print(duple_IE);
                                duple_IE.delete(0, duple_IE.length());
                                stoping = 0;
                            }
                            intron = 0;
                        }
                        if (exp_reg(last, "^intron")) {
                            duple_IE.append("[");
                            duple_IE.append(line);
                            duple_IE.append("p,a,g");
                            intron = 1;
                        }
                    }
                    last = line;
                    stoping++;
                }
                TRIPLETAS.print(duple_IE);
                TRIPLETAS.close();
                UNA_VEZ.close();
                //
                TRIPLETAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/duplex" + inihebra[hebra] + "_ZE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                UNA_VEZ = new BufferedReader(FileIn);
                last = "";
                String last_a = "";
                int stop = 0;
                StringBuffer duple_ZE = new StringBuffer();
                stoping = 0;
                while ((line = UNA_VEZ.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (exp_reg(last, "exon1$")) {
                            duple_ZE.append("p");
                            duple_ZE.append("," + line.substring(0, line.length() - 1));
                            duple_ZE.append("]\n");
                            if (stoping > 10) {
                                TRIPLETAS.print(duple_ZE);
                                duple_ZE.delete(0, duple_ZE.length());
                                stoping = 0;
                            }
                            stop = 0;
                        }
                        if (exp_reg(line, "^start")) {
                            duple_ZE.append("[");
                            duple_ZE.append(last_a);
                        }
                    }
                    last_a = last;
                    last = line;
                    stoping++;
                }
                TRIPLETAS.print(duple_ZE);
                TRIPLETAS.close();
                UNA_VEZ.close();
                //
                TRIPLETAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/duplex" + inihebra[hebra] + "_EZ.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                UNA_VEZ = new BufferedReader(FileIn);
                last = "";
                last_a = "";
                stop = 0;
                StringBuffer duple_EZ = new StringBuffer();
                stoping = 0;
                while ((line = UNA_VEZ.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (stop == 1) {
                            duple_EZ.append("," + line.substring(0, line.length() - 1));
                            duple_EZ.append("]\n");
                            if (stoping > 10) {
                                TRIPLETAS.print(duple_EZ);
                                duple_EZ.delete(0, duple_EZ.length());
                                stoping = 0;
                            }
                            stop = 0;
                        }
                        if (exp_reg(line, "^stop")) {
                            duple_EZ.append("[");
                            duple_EZ.append(last.substring(0, last.length() - 6) + "p," + last.substring(last.length() - 6, last.length() - 1));
                            stop = 1;
                        }
                    }
                    last = line;
                    stoping++;
                }
                TRIPLETAS.print(duple_EZ);
                TRIPLETAS.close();
                UNA_VEZ.close();
                //
                //tripletas
                TRIPLETAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/tri" + inihebra[hebra] + "_IEZ.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                UNA_VEZ = new BufferedReader(FileIn);
                int inicio = 0;
                int fin = 1;
                stop = 0;
                intron = 0;
                int exon = 0;
                last = "";
                StringBuffer temp = new StringBuffer();
                while ((line = UNA_VEZ.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (exp_reg(last, "^intron") && intron == 0) {
                            intron = 1;
                            fin = 1;
                            temp.append("[p");
                            temp.append("," + line.substring(0, line.length() - 1));
                        }
                        if (exp_reg(last, "^exon") && intron == 1) {
                            try {
                                temp.append("," + line.substring(0, line.length() - 4));
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("__________________" + line);
                            }
                            temp.append("p,a,g,");
                            exon = 1;
                        }
                        if (stop == 1) {
                            if (!exp_reg(line, "^start")) {
                                if (!exp_reg(line, "^stop")) {
                                    temp.append("," + line.substring(0, line.length() - 1));
                                }
                            } else {
                                temp.append("]\n");
                                intron = 0;
                                stop = 0;
                                exon = 0;
                                fin = 0;
                                TRIPLETAS.print(temp);
                                temp.delete(0, temp.length());
                            }
                        }
                        if (exon == 1) {
                            if (exp_reg(last, "^intron")) {
                                temp.delete(0, temp.length());
                                temp.append("[p");
                                temp.append("," + line.substring(0, line.length() - 1));
                                intron = 1;
                                exon = 0;
                            }
                            if (exp_reg(line, "^stop")) {
                                temp.append(last.substring(0, last.length() - 6) + "p," + last.substring(last.length() - 6, last.length() - 1));
                                stop = 1;
                                exon = 0;
                                intron = 0;
                            }
                        }
                    }
                    last = line;
                }
                temp.append("]\n");
                TRIPLETAS.print(temp);
                temp.delete(0, temp.length());
                TRIPLETAS.close();
                UNA_VEZ.close();
                //
                TRIPLETAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/tri" + inihebra[hebra] + "_EIE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                BufferedReader OTRA_VEZ = new BufferedReader(FileIn);
                last_line = "";
                String l_last_line = "";
                StringBuffer tri_EIE = new StringBuffer();
                intron = 0;
                exon = 1;
                stoping = 0;
                while ((line = OTRA_VEZ.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (exp_reg(last_line, "^exon")) {
                            l_last_line = line;
                        }
                        if (exp_reg(last_line, "^intron") && exon == 1) {
                            exon = 0;
                            tri_EIE.append("[p");
                            tri_EIE.append("," + l_last_line.substring(0, l_last_line.length() - 1));
                            tri_EIE.append(",p");
                            tri_EIE.append("," + line.substring(0, line.length() - 4));
                            tri_EIE.append("p,a,g");
                            intron = 1;
                        }
                        if (exp_reg(last_line, "^exon") && intron == 1) {
                            intron = 0;
                            exon = 1;
                            tri_EIE.append("," + line.substring(0, line.length() - 1));
                            tri_EIE.append("]\n");
                            if (stoping > 2000) {
                                TRIPLETAS.print(tri_EIE);
                                tri_EIE.delete(0, tri_EIE.length());
                                stoping = 0;
                            }
                        }
                    }
                    last_line = line;
                    stoping++;
                }
                TRIPLETAS.print(tri_EIE);
                TRIPLETAS.close();
                OTRA_VEZ.close();
                //
                FileIn = new FileReader(directorio + hebras[hebra] + "/Out" + "/temp2" + inihebra[hebra] + ".txt");
                UNA_VEZ = new BufferedReader(FileIn);
                TRIPLETAS = new PrintStream(directorio + hebras[hebra] + "/Ejemplos" + "/tri" + inihebra[hebra] + "_ZEI.txt");
                inicio = 0;
                fin = 1;
                exon = 0;
                intron = 0;
                last = "";
                l_last_line = "";
                StringBuffer tripletas = new StringBuffer();
                while ((line = UNA_VEZ.readLine()) != null) {
                    line = line.toLowerCase();
                    if (exp_reg(line, "[a-z]+")) {
                        if (inicio == 0 && fin == 1) {
                            inicio = 1;
                            fin = 0;
                            tripletas.append("[");
                        }
                        if (exon == 0 && intron == 0 && inicio == 1 && fin == 0) {
                            if (!exp_reg(line, "(^start|^exon|^stop|^intron)")) {
                                tripletas.append(line);
                            }
                        }
                        if (exp_reg(last, "^exon1") && inicio == 1) {
                            tripletas.append("p,");
                            exon = 1;
                            l_last_line = last;
                        }
                        if (exp_reg(last, "^intron") && inicio == 1) {
                            tripletas.append("p,");
                            intron = 1;
                            exon = 0;
                        }
                        if (exon == 1 && l_last_line.compareTo(last) != 0 && !exp_reg(line, "^intron") && inicio == 1) {
                            tripletas.delete(0, tripletas.length());
                            inicio = 0;
                            l_last_line = "";
                            exon = 0;
                        }
                        if (exon == 1) {
                            if (!exp_reg(line, "(^start|^exon|^stop|^intron)") && inicio == 1) {
                                tripletas.append(line);
                            }
                        }
                        if (intron == 1 && inicio == 1) {
                            if (!exp_reg(line, "(^start|^exon|^stop|^intron)")) {
                                tripletas.append(line.substring(0, line.length() - 4));
                                tripletas.append("p,a,g]\n");
                                TRIPLETAS.print(tripletas);
                                tripletas.delete(0, tripletas.length());
                                intron = 0;
                                inicio = 0;
                            }
                        }
                        if (exp_reg(line, "^stop")) {
                            fin = 1;
                        }
                    }
                    last = line;
                }
                TRIPLETAS.close();
                hebra++;
            }//fin while
        } catch (IOException errormessage) {
            errormessage.printStackTrace();
        }
    }

    public boolean exp_reg(String str, String exp) {
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
}
