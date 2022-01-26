import java.util.Iterator;

class Range implements Iterable<Long> {

    private long fromInclusive;
    private long toExclusive;

    public Range(long from, long to) {
        this.fromInclusive = from;
        this.toExclusive = to;
    }

    @Override
    public Iterator<Long> iterator() {
        Iterator<Long> it = new Iterator<Long>() {

            private long currentIndex = fromInclusive;

            @Override
            public boolean hasNext() {
                return currentIndex < toExclusive;
            }

            @Override
            public Long next() {
                return currentIndex++;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}