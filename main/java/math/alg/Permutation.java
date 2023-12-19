package math.alg;

public class Permutation {

    private int[] perm;

    public Permutation(int order) {
        this.perm = new int[order];
    }

    public int get(int i) {
        return perm[i];
    }
}
