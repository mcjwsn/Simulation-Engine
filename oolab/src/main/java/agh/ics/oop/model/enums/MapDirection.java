package agh.ics.oop.model.enums;

import agh.ics.oop.model.util.Vector2d;

public enum MapDirection {
    NORTH,EAST,SOUTH,WEST,NORTHEAST,SOUTHEAST,SOUTHWEST,NORTHWEST;

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "^";
            case SOUTH -> "v";
            case WEST -> "<";
            case EAST -> ">";
            case NORTHEAST -> "↗";
            case SOUTHEAST -> "↘";
            case SOUTHWEST -> "↙";
            case NORTHWEST -> "↖";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> NORTHEAST;
            case NORTHEAST -> EAST;
            case EAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> WEST;
            case WEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1, 1);
        };
    }

    public MapDirection rotate(int angle) {
        MapDirection result = this;
        for (int i = 0; i < angle; i++) {
            result = result.next();
        }
        return result;
    }
}