package math.alg;

public class CayleyTable implements Cloneable, Comparable<CayleyTable> {

    public static final int UNDEFINED = -1;
    private final int order;
    private final String symbol;
    private final int[][] data;

    public CayleyTable(int order, String s) {
        this.order = order;
        this.symbol = s;
        this.data = new int[order][order];

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                data[i][j] = UNDEFINED;
            }
        }
    }

    public CayleyTable(int order) {
        this(order, "+");
    }

    public int getOrder() {
        return order;
    }

    public int get(int i, int j) {
        return data[i][j];
    }

    public void set(int i, int j, int k) {
        data[i][j] = k;
    }

    @Override
    public CayleyTable clone() {
        try {
            return (CayleyTable) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void exert(Permutation perm) {
        int[][] temp = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                temp[i][j] = data[perm.get(i)][perm.get(j)];
            }
        }

        for (int i = 0; i < order; i++) {
            System.arraycopy(temp[i], 0, data[i], 0, order);
        }
    }

    @Override
    public int compareTo(CayleyTable cayleyTable) {
        if (order != cayleyTable.order) {
            return order - cayleyTable.order;
        }

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                if (data[i][j] != cayleyTable.data[i][j]) {
                    return data[i][j] - cayleyTable.data[i][j];
                }
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CayleyTable{");
        sb.append("order=").append(order);
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", data=").append(dataToString());
        sb.append('}');
        return sb.toString();
    }

    private String dataToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < order; i++) {
            sb.append("[");
            for (int j = 0; j < order; j++) {
                sb.append(data[i][j]);
                if (j != order - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i != order - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
