package org.kepe.beancp.tool.vo;

import java.util.Objects;

/**
 * A tuple of 3 elements.
 */
public class Tuple3<R1, R2, R3> {

    public R1 r1;
    public R2 r2;
    public R3 r3;

    public Tuple3(R1 r1, R2 r2, R3 r3) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
    }

    /**
     * Factory method for creating a Tuple.
     *
     * @return new Tuple
     */
    public static <C1, C2, C3> Tuple3<C1, C2,C3> of(C1 c1, C2 c2, C3 c3) {
        return new Tuple3<C1, C2, C3>(c1, c2, c3);
    }

    /**
     * Swaps the element of this Tuple.
     *
     * @return a new Tuple where the first element is the second element of this Tuple and the second element is the first element of this Tuple.
     */
    public Tuple3<R3, R2, R1> swap() {
        return new Tuple3<R3, R2, R1>(this.r3, this.r2, this.r1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tuple3)) {
            return false;
        }
        Tuple3 that = (Tuple3) o;
        return Objects.equals(this.r1, that.r1) && Objects.equals(this.r2, that.r2)&& Objects.equals(this.r3, that.r3);
    }

    @Override
    public int hashCode() {
        int result = r1 != null ? r1.hashCode() : 0;
        result = 31 * result + (r2 != null ? r2.hashCode() : 0);
        result = 31 * result + (r3 != null ? r3.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tuple2{" +
                "r1=" + r1 +
                ", r2=" + r2 +
                ", r3=" + r3 +
                '}';
    }
}
