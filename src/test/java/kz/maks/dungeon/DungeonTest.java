package kz.maks.dungeon;

import org.junit.jupiter.api.Test;

import static kz.maks.dungeon.Dungeon.Block.AIR;
import static kz.maks.dungeon.Dungeon.Block.GROUND;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DungeonTest {

    @Test
    public void isOpen() {
        Dungeon.Block[][] area = {
            {GROUND, AIR, AIR, AIR, AIR, AIR, AIR},
            {GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
            {AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
            {AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
        };

        assertTrue(new Dungeon(area).isOpen());

        area[2][0] = GROUND;
        area[3][0] = GROUND;

        assertFalse(new Dungeon(area).isOpen());

        area[2][0] = AIR;
        area[3][0] = AIR;
        area[0][6] = GROUND;
        area[1][6] = GROUND;

        assertFalse(new Dungeon(area).isOpen());
    }

    @Test
    public void isCompatible() {
        Dungeon.Block[][] areaA = {
            {GROUND, AIR, AIR, AIR, AIR, AIR, AIR},
            {GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
            {AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
            {AIR, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
        };

        Dungeon.Block[][] areaB = {
            {GROUND, GROUND, AIR, AIR, GROUND, GROUND, AIR},
            {AIR, AIR, AIR, AIR, AIR, AIR, AIR},
            {GROUND, GROUND, GROUND, AIR, GROUND, GROUND, GROUND},
            {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, GROUND},
        };

        Dungeon dungeonA = new Dungeon(areaA);
        Dungeon dungeonB = new Dungeon(areaB);

        assertTrue(dungeonA.isCompatible(dungeonB));
        assertFalse(dungeonB.isCompatible(dungeonA));
    }
}
