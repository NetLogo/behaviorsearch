package bsearch.util;

/**
 * Generally useful utility class, for storing a typed pair of objects.
 * Based on http://forums.sun.com/thread.jspa?threadID=5132045 , with minor modifications.
 *
 * TODO: Remove this class, since it's not being used.  ~Forrest (10/4/2009)
 */
public class Pair<L, R> {

    private final L left;
    private final R right;

    public R getRight() {
        return right;
    }

    public L getLeft() {
        return left;
    }

    public Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public static <A, B> Pair<A, B> create(A left, B right) {
        return new Pair<A, B>(left, right);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Pair<?, ?>))
            return false;

        final Pair<?, ?> other = (Pair<?, ?>) o;
        return equal(getLeft(), other.getLeft()) && equal(getRight(), other.getRight());
    }

    private static final boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    @Override
    public int hashCode() {
        int hLeft = getLeft() == null ? 0 : getLeft().hashCode();
        int hRight = getRight() == null ? 0 : getRight().hashCode();

        return hLeft + (37 * hRight);
    }
}

