/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bioinformants.client.model.predictor.experimento;

import java.util.ArrayList;

/**
 *
 * @author Adrian
 */
public class Splice {
    private ArrayList<Part> exones = new ArrayList<Part>();
    private String notas;
    private String genId;

    public ArrayList<Part> getExones() {
        return exones;
    }

    public void setExones(ArrayList<Part> exones) {
        this.exones = exones;
    }

    public String getGenId() {
        return genId;
    }

    public void setGenId(String genId) {
        this.genId = genId;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    


}
