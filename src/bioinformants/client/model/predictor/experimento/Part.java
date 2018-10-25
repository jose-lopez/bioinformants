/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bioinformants.client.model.predictor.experimento;

/**
 *
 * @author Casa
 */
public class Part {

    String base;
    int limInf = 0;
    int limSup = 0;
    boolean complement = false;

    public Part(String in) {
        this(in, false);
    }

    public Part(String in, boolean complement) {
        this.complement = complement;
        base = in;
        try {
            limInf = Integer.parseInt(base.split("\\.\\.")[0]);
            limSup = Integer.parseInt(base.split("\\.\\.")[1]);

        } catch (Exception e) {

        }
    }

    @Override
    public String toString() {
        String out = "start:" + " " +limInf+ " end:" + " " + limSup +"\t"+(complement ? "~" : "") + "(" + base + ")";
        return out;
    }
}
