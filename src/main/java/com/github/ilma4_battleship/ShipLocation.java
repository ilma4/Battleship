package com.github.ilma4_battleship;

import java.util.Iterator;
import java.util.Objects;

public record ShipLocation(Point fromInclusive, Point toInclusive) implements Iterable<Point> {

    public ShipLocation {
        if (fromInclusive.line() != toInclusive.line()
            && fromInclusive.row() != toInclusive.row()) {
            throw new IllegalArgumentException("Error! Wrong ship location!");
        }
    }

    public int size() {
        if (fromInclusive.line() != toInclusive.line()) {
            return Math.abs(fromInclusive.line() - toInclusive.line()) + 1;
        }
        return Math.abs(fromInclusive.row() - toInclusive.row()) + 1;
    }

    @Override
    public Iterator<Point> iterator() {
        return new SegmentIterator(fromInclusive, toInclusive);
    }

    static private class SegmentIterator implements Iterator<Point> {

        private final int delta_line;
        private final int delta_row;
        private final Point lastPoint;
        private final Point firstPoint;
        private Point current = null;

        private SegmentIterator(Point from, Point to) {
            Objects.requireNonNull(from);
            Objects.requireNonNull(to);

            firstPoint = from;
            lastPoint = to;
            if (from.line() == to.line()) {
                if (from.row() < to.row()) {
                    delta_row = 1;
                } else {
                    delta_row = -1;
                }
                delta_line = 0;
            } else {
                delta_row = 0;
                if (from.line() < to.line()) {
                    delta_line = 1;
                } else {
                    delta_line = -1;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !Objects.equals(current, lastPoint);
        }

        @Override
        public Point next() {
            if (!hasNext()) {
                throw new IllegalStateException("No next");
            }

            if (current == null) {
                current = firstPoint;
                return current;
            }
            current = new Point(current.line() + delta_line, current.row() + delta_row);
            return current;
        }
    }
}
