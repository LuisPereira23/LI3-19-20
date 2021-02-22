package model.data;

/**
 * Constructor for Par
 */
public class Par<X,Y> {
    private X x;
    private Y y;
    public Par(X x, Y y){
        this.x = x;
        this.y = y;
    }

    /**
     * @param x adds x to pair
     */
    public void setX(X x) {
        this.x = x;
    }

    /**
     * @param y adds y to pair
     */
    public void setY(Y y) {
        this.y = y;
    }

    /**
     * get for X
     * @return first of pair
     */
    public X getX() {
        return x;
    }

    /**
     * get for y
     * @return second of pair
     */
    public Y getY() {
        return y;
    }
}
