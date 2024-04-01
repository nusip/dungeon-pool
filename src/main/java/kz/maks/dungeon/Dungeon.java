package kz.maks.dungeon;

import java.util.Arrays;

public class Dungeon {
    private static final int ROW_SIZE = 4;
    private static final int COL_SIZE = 7;
    private final Block[][] area;

    public Dungeon(Block[][] area) {
        if (area.length != ROW_SIZE && area[0].length != COL_SIZE) {
            throw new RuntimeException("Incorrect size!");
        }
        this.area = new Block[area.length][area[0].length];

        for (int i = 0; i < ROW_SIZE; i++) {
            System.arraycopy(area[i], 0, this.area[i], 0, COL_SIZE);
        }
    }

    public boolean isOpen() {
        boolean hasEntrance = false;

        for (int i = 0; i < ROW_SIZE; i++) {
            if (area[i][0] == Block.AIR) {
                hasEntrance = true;
                break;
            }
        }

        boolean hasExit = false;

        for (int i = 0; i < ROW_SIZE; i++) {
            if (area[i][COL_SIZE - 1] == Block.AIR) {
                hasExit = true;
                break;
            }
        }

        return hasEntrance && hasExit;
    }

    public boolean isCompatible(Dungeon d) {
        for (int i = 0; i < ROW_SIZE; i++) {
            if (area[i][COL_SIZE - 1] == Block.AIR && d.area[i][0] == Block.AIR) {
                return true;
            }
        }
        return false;
    }

    public static enum Block {
        GROUND,
        AIR
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dungeon dungeon = (Dungeon) o;
        return Arrays.deepEquals(area, dungeon.area);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(area);
    }
}