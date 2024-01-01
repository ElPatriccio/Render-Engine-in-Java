package Vector;

import java.util.Iterator;

public class VecIter implements Iterator<Float> {
    private final float[] v;
    private int i = 0;
    private final int x;

    public VecIter(float[] v, boolean homogenized){
        this.v = v;
        x = homogenized ? 0 : 1;
    }

    @Override
    public boolean hasNext() {
        return i < v.length - x;
    }

    @Override
    public Float next() {
        return v[i++];
    }
}
