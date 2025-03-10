package agh.ics.oop.model.util;

import java.util.Objects;

public class Vector2d {
    private final int x,y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString ()
    {
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.getX() && this.y <= other.getY();
    }

    public boolean follows(Vector2d other){
        return this.x >= other.getX() && this.y >= other.getY();
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.getX(), this.y + other.getY());
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.getX(),this.y-other.getY());
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.getX()), Math.max(this.y, other.getY()));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.getX()), Math.min(this.y, other.getY()));
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}