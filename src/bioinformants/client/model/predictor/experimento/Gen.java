package bioinformants.client.model.predictor.experimento;

import java.util.ArrayList;

public class Gen {

    private static final String[] prop = {"/gene=", "/locus_tag=", "/note=", "join(", "complement("};
    private String codigo = "";
    private String locus_tag = "";
    private String genes = "";
    private String nota = "";
    private String adn = "";
    private Integer inicio;
    private Integer parada;
    private boolean complemented;
    private ArrayList<Splice> Splice= new ArrayList<Splice>();

    public Gen() {
        
    }

    void decodeGen() {
        int pos = 0;
        int aux = -1;
        String in = codigo.substring(0, codigo.indexOf("\n")).trim();

        if (!in.startsWith(prop[4])) {
            establecerBordes(in, false);
        } else if (codigo.indexOf(prop[4]) != -1) {
            complement(codigo);
        }

        pos = 0;
        aux = -1;
        if ((pos = codigo.indexOf(prop[0], pos)) != -1) {
            if ((aux = codigo.indexOf(prop[1], pos)) != -1) {
                genes = codigo.substring(pos, aux).trim();
                pos = aux;
            }
            if ((aux = codigo.indexOf(prop[2], pos)) != -1) {
                if (genes.length() == 0) {
                    genes = codigo.substring(pos, aux).trim();
                } else {
                    locus_tag = codigo.substring(pos, aux).trim();
                }
                nota = codigo.substring(aux).trim();
            }
            if (genes.length() == 0) {
                genes = codigo.substring(pos).trim();
            }
            if (locus_tag.length() == 0) {
                locus_tag = codigo.substring(pos).trim();
            }
        }

        genes = genes.replaceFirst(prop[0], "");
        locus_tag = locus_tag.replaceFirst(prop[1], "");
        nota = nota.replaceFirst(prop[2], "");
        nota = nota.replaceAll("\n\t\t", " ");

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getGenes() {
        return genes;
    }

    public void setGenes(String genes) {
        this.genes = genes;
    }

    public String getLocus_tag() {
        return locus_tag;
    }

    public void setLocus_tag(String locus_tag) {
        this.locus_tag = locus_tag;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    private void complement(String comple) {
        comple = comple.substring(comple.indexOf("(") + 1, comple.indexOf(")"));
        establecerBordes(comple, true);
    }

    private void establecerBordes(String comple, boolean isComplemented) {
        this.complemented = isComplemented;
        try {
            int x = comple.indexOf(":");
            x = x == -1 ? 0 : x;
            String base = comple.substring(x);
            inicio =  Integer.parseInt(base.split("\\.\\.")[ 0]);
            parada =  Integer.parseInt(base.split("\\.\\.")[ 1]);
        } catch (Exception e) {
        }

    }

    public Integer getInicio() {
        return inicio;
    }

    public void setInicio(Integer inicio) {
        this.inicio = inicio;
    }

    public Integer getParada() {
        return parada;
    }

    public void setParada(Integer parada) {
        this.parada = parada;
    }

    public String getAdn() {
        return adn;
    }




    /**establece en adn el gen con un offset al pricipio o al fin del tamaño dado*/
    public void setAdn(StringBuffer chromo, int offsetIni,int OffsetFin) {
        int start=this.inicio-offsetIni-1;
        int stop=this.parada+OffsetFin;
        adn = chromo.substring(start , stop);
        if (isComplemented()) {
            adn= new StringBuffer(adn).reverse().toString();
            adn = adn.replaceAll("A", "O");
            adn = adn.replaceAll("T", "A");
            adn = adn.replaceAll("O", "T");
            adn = adn.replaceAll("G", "X");
            adn = adn.replaceAll("C", "G");
            adn = adn.replaceAll("X", "C");
        }
    }

    public boolean isComplemented() {
        return complemented;
    }

    public void setComplemented(boolean isComplemented) {
        this.complemented = isComplemented;
    }

    @Override
    public String toString() {

        return "start:" + " " + inicio + " end:" + " " + parada + "\t" + adn;
    }


    public ArrayList<Splice> getSplice() {
      
        return Splice;
   }

    public void setSplice(ArrayList<Splice> Splice) {
        this.Splice = Splice;
    }


}
