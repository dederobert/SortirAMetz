package a1819.m2ihm.sortirametz.helpers;

import android.location.Location;

public enum MathHelper {
    INSTANCE;

    public class Point2D {
        public double x;
        public double y;

        public Point2D() {this(0,0);}
        public Point2D(Location location) {
        }
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }


    }

    public double distanceBetweenPoints2D(Point2D start, Point2D end) {
        return Math.sqrt(
                Math.pow(start.x-end.x,2) +
                        Math.pow(start.y- end.y,2)
        );
    }
}
