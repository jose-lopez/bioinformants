package bioinformants.client.model.predictor;

//package ASIP.server;
import java.io.*;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StrandsContraExamples {


    public void contraejemplos(String GI, int cant_bases, String root_url) {
        try {
            int hebra = 0;
            String directorio = root_url + "Data/" + GI + "/";
            String hebras[] = {"Watson", "Crick"};
            char inihebra[] = {'W', 'C'};
            while (hebra < 2) {
                //Contraejemplos de zona intergenica
                File dir = new File(directorio + hebras[hebra] + "/Contraejemplos");
                dir.mkdir();
                dir = new File(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_ZE");
                dir.mkdir();
                PrintStream NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_ZE/duplex" + inihebra[hebra] + "z_ZE" + cant_bases + ".txt");
                FileReader FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_ZE.txt");
                BufferedReader READ = new BufferedReader(FileIn);
                String line = "";
                String zona = "";
                int marcagen;
                int respaldo;
                String almacenar = "";
                int marcaintron;
                while ((line = READ.readLine()) != null) {
                    zona = line.substring(0, line.indexOf("p") - 1);
                    zona = zona.concat("]");
                    marcagen = 0;
                    almacenar = "";
                    while (marcagen < (cant_bases * 2) && marcagen != -1) {
                        marcagen = zona.indexOf("a,t,g", marcagen + 1);
                    }
                    if (marcagen > cant_bases * 2) {
                        almacenar = almacenar.concat("[");
                        almacenar = almacenar.concat(zona.substring(marcagen - (cant_bases * 2), marcagen));
                        almacenar = almacenar.concat("p,");
                        marcaintron = zona.indexOf("g,t", marcagen + 6);
                        if (marcaintron != -1) {
                            almacenar = almacenar.concat(zona.substring(marcagen, marcaintron - 1));
                            almacenar = almacenar.concat("]");
                            NEW.println(almacenar);
                        }
                    }
                }
                NEW.close();
                READ.close();
                //
                dir = new File(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EZ");
                dir.mkdir();
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EZ/duplex" + inihebra[hebra] + "z_EZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EZ.txt");
                READ = new BufferedReader(FileIn);
                line = "";
                zona = "";
                almacenar = "";
                while ((line = READ.readLine()) != null) {
                    zona = zona.concat("[");
                    zona = line.substring(line.indexOf("p") + 2, line.length());
                    almacenar = "";
                    respaldo = 0;
                    Random random = new Random();
                    int combinacion = random.nextInt(3);
//TODO CREO Q ES AQUI   marcaintron = zona.indexOf("g,a");
                    marcaintron = zona.indexOf("a,g");
                    marcagen = marcaintron + 4;
                    while (zona.length() > (marcagen + (cant_bases * 2) + 9) && marcagen != -1) {
                        if (combinacion == 0) {
                            marcagen = zona.indexOf("t,a,a", marcagen + 1);
                        } else {
                            if (combinacion == 1) {
                                marcagen = zona.indexOf("t,g,a", marcagen + 1);
                            } else {
                                marcagen = zona.indexOf("t,a,g", marcagen + 1);
                            }
                        }
                    }
                    if (marcagen != -1 && marcagen != marcaintron + 4 && marcagen + cant_bases + 7 < zona.length()) {
                        almacenar = almacenar.concat("[");
                        almacenar = almacenar.concat(zona.substring(marcaintron + 4, marcagen));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcagen, marcagen + cant_bases + 7));
                        almacenar = almacenar.concat("]");
                        NEW.println(almacenar);
                    }
                }
                NEW.close();
                READ.close();
                //
                dir = new File(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_IEZ");
                dir.mkdir();
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_IEZ/tri" + inihebra[hebra] + "z_IEZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_IEZ.txt");
                READ = new BufferedReader(FileIn);
                line = "";
                zona = "";
                int marcaexon;
                almacenar = "";
                while ((line = READ.readLine()) != null) {
                    zona = zona.concat("[");
                    zona = line.substring(line.lastIndexOf("p") + 2, line.length());
                    almacenar = "";
                    respaldo = 0;
                    Random random = new Random();
                    int combinacion = random.nextInt(3);
                    marcaexon = zona.indexOf("g,t");
//TODO AQUIO PUEDE SE      marcaintron = zona.indexOf("g,a", marcaexon + 4);
                    marcaintron = zona.indexOf("a,g", marcaexon + 4);
                    marcagen = marcaintron + 4;
                    while (zona.length() > (marcagen + (cant_bases * 2) + 9) && marcagen != -1) {
                        if (combinacion == 0) {
                            marcagen = zona.indexOf("t,a,a", marcagen + 1);
                        } else {
                            if (combinacion == 1) {
                                marcagen = zona.indexOf("t,g,a", marcagen + 1);
                            } else {
                                marcagen = zona.indexOf("t,a,g", marcagen + 1);
                            }
                        }
                    }
                    if (marcagen != -1 && marcagen != marcaintron + 4 && marcagen + cant_bases + 7 < zona.length()) {
                        almacenar = almacenar.concat("[p,");
                        almacenar = almacenar.concat(zona.substring(marcaexon, marcaintron));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcaintron, marcagen));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcagen, marcagen + cant_bases + 7));
                        almacenar = almacenar.concat("]");
                        NEW.println(almacenar);
                    }
                }
                NEW.close();
                READ.close();
                //
                dir = new File(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_ZEI");
                dir.mkdir();
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_ZEI/tri" + inihebra[hebra] + "z_ZEI" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_ZEI.txt");
                READ = new BufferedReader(FileIn);
                line = "";
                zona = "";
                almacenar = "";
                while ((line = READ.readLine()) != null) {
                    zona = line.substring(0, line.indexOf("p") - 1);
                    zona = zona.concat("]");
                    marcagen = 0;
                    almacenar = "";
                    while (marcagen < (cant_bases * 2) && marcagen != -1) {
                        marcagen = zona.indexOf("a,t,g", marcagen + 1);
                    }
                    marcaintron = zona.indexOf("g,t", marcagen + 6);
//TODO  AQUI     marcaexon = zona.indexOf("g,a", marcaintron + 4);
                    marcaexon = zona.indexOf("a,g", marcaintron + 4);

                    if (marcagen > cant_bases * 2 && marcaintron != -1 && marcaexon != -1) {
                        almacenar = almacenar.concat("[");
                        almacenar = almacenar.concat(zona.substring(marcagen - (cant_bases * 2), marcagen));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcagen, marcaintron));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcaintron, marcaexon));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcaexon, marcaexon + 3));
                        almacenar = almacenar.concat("]");
                        NEW.println(almacenar);
                    }
                }
                NEW.close();
                READ.close();
                // Contraejemplos moviendo se#ales a la izquierda
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_ZE/duplex" + inihebra[hebra] + "l_ZE" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_ZE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") - (cant_bases * 2) > 0) {
                        NEW.println(line.substring(0, line.indexOf("p") - (cant_bases * 2)) + "p,a,t,g," + line.substring(line.indexOf("p") - (cant_bases * 2) + 2, line.indexOf("p")) + line.substring(line.indexOf("p") + 2));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EZ/duplex" + inihebra[hebra] + "l_EZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EZ.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") - (cant_bases * 2) > 0) {
                        NEW.println(line.substring(0, line.indexOf("p") - (cant_bases * 2)) + line.substring(line.indexOf("p"), line.indexOf("p") + 8) + line.substring(line.indexOf("p") - (cant_bases * 2) + 2, line.indexOf("p")) + line.substring(line.indexOf("p") + 8));
                    }
                }
                NEW.close();
                READ.close();
                //
                dir = new File(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_IE");
                dir.mkdir();
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_IE/duplex" + inihebra[hebra] + "l_IE" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_IE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") - (cant_bases * 2) > 0) {
                        NEW.println(line.substring(0, line.indexOf("p") - (cant_bases * 2)) + "p,a,g," + line.substring(line.indexOf("p") - (cant_bases * 2) + 2, line.indexOf("p")) + line.substring(line.indexOf("p") + 2));
                    }
                }
                NEW.close();
                READ.close();
                //
                dir = new File(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EI");
                dir.mkdir();
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EI/duplex" + inihebra[hebra] + "l_EI" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EI.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") - (cant_bases * 2) > 0) {
                        NEW.println(line.substring(0, line.indexOf("p") - (cant_bases * 2)) + "p,g,t," + line.substring(line.indexOf("p") - (cant_bases * 2) + 2, line.indexOf("p")) + line.substring(line.indexOf("p") + 2));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_IEZ/tri" + inihebra[hebra] + "l_IEZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_IEZ.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p", 3) - (cant_bases * 2) > 0) {
                        if ((line.indexOf("p", 3) + (cant_bases * 2)) <= line.lastIndexOf("p")) {
                            NEW.println(line.substring(0, line.indexOf("p", 3) - (cant_bases * 2)) + "p,a,g," + line.substring(line.indexOf("p", 3) - (cant_bases * 2) + 2, line.indexOf("p", 3)) + line.substring(line.indexOf("p", 3) + 2, line.lastIndexOf("p") - (cant_bases * 2)) + line.substring(line.lastIndexOf("p"), line.lastIndexOf("p") + 8) + line.substring(line.lastIndexOf("p") - (cant_bases * 2) + 2, line.lastIndexOf("p")) + line.substring(line.lastIndexOf("p") + 8));
                        }
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_ZEI/tri" + inihebra[hebra] + "l_ZEI" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_ZEI.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") - (cant_bases * 2) > 0) {
                        int var = line.indexOf("p");
                        if ((line.indexOf("p") + (cant_bases * 2) + 3) <= line.indexOf("p", var + 1)) {
                            NEW.println(line.substring(0, line.indexOf("p") - (cant_bases * 2)) + "p,a,t,g," + line.substring(line.indexOf("p") - (cant_bases * 2) + 2, line.indexOf("p")) + line.substring(line.indexOf("p") + 2, line.indexOf("p", var + 1) - (cant_bases * 2)) + "p,g,t," + line.substring(line.indexOf("p", var + 1) - (cant_bases * 2) + 2, line.indexOf("p", var + 1)) + line.substring(line.indexOf("p", var + 1) + 8));
                        }
                    }
                }
                NEW.close();
                READ.close();
                //
                dir = new File(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_EIE");
                dir.mkdir();
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_EIE/tri" + inihebra[hebra] + "l_EIE" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_EIE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p", 3) - (cant_bases * 2) > 0) {
                        if ((line.indexOf("p", 3) + (cant_bases * 2)) <= line.lastIndexOf("p")) {
                            int i1 = line.indexOf("p", 3) - (cant_bases * 2);
                            int i2 = line.indexOf("p", 3) - (cant_bases * 2) + 2;
                            int i3 = line.indexOf("p", 3);
                            int i4 = line.indexOf("p", 3) + 2;
                            int i5 = line.lastIndexOf("p") - (cant_bases * 2);
                            int i6 = line.lastIndexOf("p") - (cant_bases * 2) + 2;
                            int i7 = line.lastIndexOf("p");
                            int i8 = line.lastIndexOf("p") + 2;
                            String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
                            try {
                                s1 = line.substring(0, i1);
                            } catch (Exception e) {
                            }
                            try {
                                s2 = line.substring(i2, i3);
                            } catch (Exception e) {
                            }
                            try {
                                s3 = line.substring(i4, i5);
                            } catch (StringIndexOutOfBoundsException e) {
                            }
                            try {
                                s4 = line.substring(i6, i7);
                            } catch (Exception e) {
                            }
                            try {
                                s5 = line.substring(i8);
                            } catch (Exception e) {
                            }

                            NEW.println(s1 + "p,g,t," + s2
                                    + s3
                                    + "p,a,g,"//TODO "p,g,a,"
                                    + s4 + s5);
                        }
                    }
                }
                NEW.close();
                READ.close();
                //	   		 Contraejemplos moviendo se#ales a la derecha
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_ZE/duplex" + inihebra[hebra] + "r_ZE" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_ZE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") + (cant_bases * 2) < line.length()) {
                        NEW.println(line.substring(0, line.indexOf("p")) + line.substring(line.indexOf("p") + 2, line.indexOf("p") + (cant_bases * 2)) + "p,a,t,g," + line.substring(line.indexOf("p") + (cant_bases * 2)));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EZ/duplex" + inihebra[hebra] + "r_EZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EZ.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") + (cant_bases * 2) < line.length()) {
                        NEW.println(line.substring(0, line.indexOf("p")) + line.substring(line.indexOf("p") + 8, line.indexOf("p") + (cant_bases * 2)) + line.substring(line.indexOf("p"), line.indexOf("p") + 8) + line.substring(line.indexOf("p") + (cant_bases * 2)));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_IE/duplex" + inihebra[hebra] + "r_IE" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_IE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") + (cant_bases * 2) < line.length()) {
                        NEW.println(line.substring(0, line.indexOf("p")) + line.substring(line.indexOf("p") + 2, line.indexOf("p") + (cant_bases * 2)) + "p,a,g," + line.substring(line.indexOf("p") + (cant_bases * 2)));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EI/duplex" + inihebra[hebra] + "r_EI" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EI.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.indexOf("p") + (cant_bases * 2) < line.length()) {
                        NEW.println(line.substring(0, line.indexOf("p")) + line.substring(line.indexOf("p") + 2, line.indexOf("p") + (cant_bases * 2)) + "p,g,t," + line.substring(line.indexOf("p") + (cant_bases * 2)));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_IEZ/tri" + inihebra[hebra] + "r_IEZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_IEZ.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.lastIndexOf("p") + (cant_bases * 2) < line.length() - 12) {
                        int one = line.indexOf("p");
                        int two = line.indexOf("p", one + 1);
                        int three = line.lastIndexOf("p");
                        String marc = line.substring(three, three + 8);
                        String temp = line.substring(0, one) + line.substring(one + 2, two) + line.substring(two + 2, three) + line.substring(three + 8);
                        NEW.println(temp.substring(0, one + (cant_bases * 2)) + "p,g,t,"
                                + temp.substring(one + (cant_bases * 2), two + (cant_bases * 2))
                                + "p,a,g,"//TODO "p,g,a,"
                                + temp.substring(two + (cant_bases * 2), three + (cant_bases * 2)) + marc + temp.substring(three + (cant_bases * 2)));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_ZEI/tri" + inihebra[hebra] + "r_ZEI" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_ZEI.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    int one = line.indexOf("p");
                    int two = line.indexOf("p", one + 1);
                    if (two + (cant_bases * 2) < line.length() - 12) {
                        String temp = line.substring(0, one) + line.substring(one + 2, two) + line.substring(two + 2);
                        NEW.println(temp.substring(0, one + (cant_bases * 2)) + "p,a,t,g," + temp.substring(one + (cant_bases * 2), two + (cant_bases * 2)) + "p,g,t," + temp.substring(two + (cant_bases * 2)));
                    }
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_EIE/tri" + inihebra[hebra] + "r_EIE" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_EIE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    if (line.lastIndexOf("p") + (cant_bases * 2) < line.length() - 12) {
                        int one = line.indexOf("p");
                        int two = line.indexOf("p", one + 1);
                        int three = line.lastIndexOf("p");
                        String marc = line.substring(one, one + 8);
                        String temp = line.substring(0, one) + line.substring(one + 2, two) + line.substring(two + 2, three) + line.substring(three + 8);
                        NEW.println(temp.substring(0, one + (cant_bases * 2)) + marc + temp.substring(one + (cant_bases * 2), two + (cant_bases * 2)) + "p,g,t," + temp.substring(two + (cant_bases * 2), three + (cant_bases * 2)) + "p,a,g," + temp.substring(three + (cant_bases * 2)));
                    }
                }
                NEW.close();
                READ.close();
                //
                //		Contraejemplos cambiando se#ales * por ATG en duplex
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_ZE/duplex" + inihebra[hebra] + "cez_ZE" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EZ" + cant_bases + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "a,t,g," + line.substring(line.indexOf("p") + 8));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_ZE/duplex" + inihebra[hebra] + "cie_ZE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_IE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "a,t,g," + line.substring(line.indexOf("p") + 6));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_ZE/duplex" + inihebra[hebra] + "cei_ZE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EI.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "a,t,g," + line.substring(line.indexOf("p") + 6));
                }
                NEW.close();
                READ.close();
                //
                //	   			Contraejemplos cambiando se#ales * por TAA,TAG,TGA en duplex
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EZ/duplex" + inihebra[hebra] + "cze_EZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_ZE" + cant_bases + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    Random random = new Random();
                    int combinacion = random.nextInt(3);
                    String marc = "";
                    if (combinacion == 0) {
                        marc = "t,a,a,";
                    } else {
                        if (combinacion == 1) {
                            marc = "t,g,a,";
                        } else {
                            marc = "t,a,g,";
                        }
                    }
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + marc + line.substring(line.indexOf("p") + 8));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EZ/duplex" + inihebra[hebra] + "cie_EZ.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_IE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    Random random = new Random();
                    int combinacion = random.nextInt(3);
                    String marc = "";
                    if (combinacion == 0) {
                        marc = "t,a,a,";
                    } else {
                        if (combinacion == 1) {
                            marc = "t,g,a,";
                        } else {
                            marc = "t,a,g,";
                        }
                    }
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + marc + line.substring(line.indexOf("p") + 6));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EZ/duplex" + inihebra[hebra] + "cei_EZ.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EI.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    Random random = new Random();
                    int combinacion = random.nextInt(3);
                    String marc = "";
                    if (combinacion == 0) {
                        marc = "t,a,a,";
                    } else {
                        if (combinacion == 1) {
                            marc = "t,g,a,";
                        } else {
                            marc = "t,a,g,";
                        }
                    }
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + marc + line.substring(line.indexOf("p") + 6));
                }
                NEW.close();
                READ.close();
                //
                //		Contraejemplos cambiando se#ales * por GA en duplex
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_IE/duplex" + inihebra[hebra] + "cez_IE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EZ" + cant_bases + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2)
                            + "a,g,"//TODO "g,a,"
                            + line.substring(line.indexOf("p") + 8));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_IE/duplex" + inihebra[hebra] + "cze_IE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_ZE" + cant_bases + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2)
                            + "a,g,"//TODO "g,a,"
                            + line.substring(line.indexOf("p") + 8));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_IE/duplex" + inihebra[hebra] + "cei_IE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EI.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2)
                            + "a,g,"//TODO "g,a,"
                            + line.substring(line.indexOf("p") + 6));
                }
                NEW.close();
                READ.close();
                //
                //		Contraejemplos cambiando se#ales * por GT en duplex
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EI/duplex" + inihebra[hebra] + "cez_EI.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_EZ" + cant_bases + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "g,t," + line.substring(line.indexOf("p") + 8));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EI/duplex" + inihebra[hebra] + "cze_EI.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_ZE" + cant_bases + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "g,t," + line.substring(line.indexOf("p") + 8));
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EI/duplex" + inihebra[hebra] + "cie_EI.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/duplex" + inihebra[hebra] + "_IE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "g,t," + line.substring(line.indexOf("p") + 6));
                }
                NEW.close();
                READ.close();
                //
                //	   			Contraejemplos cambiando se#ales * por GT AG TAA,TAG,TGA en tri
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_IEZ/tri" + inihebra[hebra] + "czei_IEZ" + cant_bases + ".txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_ZEI" + cant_bases + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    Random random = new Random();
                    int combinacion = random.nextInt(3);
                    String marc = "";
                    if (combinacion == 0) {
                        marc = "t,a,a";
                    } else {
                        if (combinacion == 1) {
                            marc = "t,g,a";
                        } else {
                            marc = "t,a,g";
                        }
                    }
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "g,t," + line.substring(line.indexOf("p") + 8, line.indexOf("p", line.indexOf("p") + 1) + 2) + "a,g," + line.substring(line.indexOf("p", line.indexOf("p") + 1) + 6, line.lastIndexOf("p") + 2) + marc + line.substring(line.lastIndexOf("p", line.lastIndexOf("p") + 1) + 6) + "]");
                }
                NEW.close();
                READ.close();
                //
                NEW = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/tri" + inihebra[hebra] + "_IEZ/tri" + inihebra[hebra] + "ceie_IEZ.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/tri" + inihebra[hebra] + "_EIE.txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    Random random = new Random();
                    int combinacion = random.nextInt(3);
                    String marc = "";
                    if (combinacion == 0) {
                        marc = "t,a,a,";
                    } else {
                        if (combinacion == 1) {
                            marc = "t,g,a,";
                        } else {
                            marc = "t,a,g,";
                        }
                    }
                    NEW.println(line.substring(0, line.indexOf("p") + 2) + "g,t," + line.substring(line.indexOf("p") + 8, line.indexOf("p", line.indexOf("p") + 1) + 2) + "a,g," + line.substring(line.indexOf("p", line.indexOf("p") + 1) + 6, line.lastIndexOf("p") + 2) + marc + line.substring(line.lastIndexOf("p", line.lastIndexOf("p") + 1) + 6));
                }
                NEW.close();
                READ.close();

//	   		contraejemplos EI IE
                PrintStream ZEI = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_EI/duplex" + inihebra[hebra] + "z_EI.txt");
                PrintStream ZIE = new PrintStream(directorio + hebras[hebra] + "/Contraejemplos/duplex" + inihebra[hebra] + "_IE/duplex" + inihebra[hebra] + "z_IE.txt");
                FileIn = new FileReader(directorio + hebras[hebra] + "/Ejemplos/zonas" + inihebra[hebra] + ".txt");
                READ = new BufferedReader(FileIn);
                while ((line = READ.readLine()) != null) {
                    zona = line;
                    int marcagt = 0;
                    almacenar = "";
                    while (marcagt < (cant_bases * 2) && marcagt != -1) {
                        marcagt = zona.indexOf("g,t", marcagt + 1);
                    }
                    if (marcagt > cant_bases * 2 && marcagt + cant_bases * 2 < zona.length()) {
                        almacenar = almacenar.concat("[");
                        almacenar = almacenar.concat(zona.substring(marcagt - (cant_bases * 2), marcagt));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcagt, marcagt + (cant_bases * 2) - 1));
                        almacenar = almacenar.concat("]");
                        ZEI.println(almacenar);
                    }
                    marcagt = 0;
                    almacenar = "";
                    while (marcagt < (cant_bases * 2) && marcagt != -1) {
                        marcagt = zona.indexOf("a,g", marcagt + 1);
                    }
                    if (marcagt > cant_bases * 2 && marcagt + cant_bases * 2 < zona.length()) {
                        almacenar = almacenar.concat("[");
                        almacenar = almacenar.concat(zona.substring(marcagt - (cant_bases * 2), marcagt));
                        almacenar = almacenar.concat("p,");
                        almacenar = almacenar.concat(zona.substring(marcagt, marcagt + (cant_bases * 2) - 1));
                        almacenar = almacenar.concat("]");
                        ZIE.println(almacenar);
                    }
                }
                ZEI.close();
                ZIE.close();
                READ.close();
                //

                hebra++;
            }
        } catch (Exception errormessage) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "contraejemplos", errormessage);
        }
    }

}

