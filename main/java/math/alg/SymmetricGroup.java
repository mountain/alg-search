package math.alg;

import java.util.Iterator;

public class SymmetricGroup implements Iterable<Permutation> {

    private static final int UNDEFINED = -1;

    private final int[][] data;

    public SymmetricGroup(int order) {
        this.data = new int[order][order * (order - 1) / 2];
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order * (order - 1) / 2; j++) {
                data[i][j] = UNDEFINED;
            }
        }


    }

    public SymmetricGroup(int[][] data, int order, int shift) {
        this.data = data;
    }

    @Override
    public Iterator<Permutation> iterator() {
        return null;
    }
}
