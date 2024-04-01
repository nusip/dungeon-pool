package kz.maks.dungeon;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static kz.maks.dungeon.Dungeon.Block.AIR;
import static kz.maks.dungeon.Dungeon.Block.GROUND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DungeonPoolTest {

    @Test
    public void shouldFailCreatingEmptyDungeonPool() {
        Throwable e = assertThrows(RuntimeException.class,
                () -> new DungeonPool(Collections.emptyList()));

        assertEquals("Empty dungeons list provided!", e.getMessage());
    }

    @Test
    public void shouldFailCreatingWithClosedDungeon() {
        Dungeon.Block[][] area = {
                {GROUND, AIR, AIR, AIR, AIR, AIR, AIR},
                {GROUND, AIR, AIR, GROUND, AIR, GROUND, AIR},
                {GROUND, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
                {GROUND, AIR, GROUND, GROUND, GROUND, GROUND, GROUND},
        };

        ArrayList<Dungeon> dungeonPool = new ArrayList<>();
        dungeonPool.add(new Dungeon(area));

        Throwable e = assertThrows(RuntimeException.class,
                () -> new DungeonPool(dungeonPool));

        assertEquals("List contains closed dungeon!", e.getMessage());
    }

    @Test
    public void shouldFailCreatingWithDungeonWithoutCompatibles() {
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

        ArrayList<Dungeon> dungeonPool = new ArrayList<>();
        dungeonPool.add(new Dungeon(areaA));
        dungeonPool.add(new Dungeon(areaB));

        Throwable e = assertThrows(RuntimeException.class,
                () -> new DungeonPool(dungeonPool));

        assertEquals("List contain dungeon without any compatibles!", e.getMessage());
    }

    @Test
    public void shouldCreateDungeonPoolSuccessfully() {
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
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, AIR},
        };

        assertDoesNotThrow(() -> new DungeonPool(Arrays.asList(new Dungeon(areaA), new Dungeon(areaB))));
    }

    @Test
    public void createXSequence() {
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
                {GROUND, GROUND, GROUND, GROUND, GROUND, GROUND, AIR},
        };

        Dungeon dungeonA = new Dungeon(areaA);
        Dungeon dungeonB = new Dungeon(areaB);
        DungeonPool dungeonPool = new DungeonPool(Arrays.asList(dungeonA, dungeonB));

        List<Dungeon> sequence = dungeonPool.createXSequence(5);
        if (sequence.get(0) == dungeonA) {
            assertEquals(sequence, Arrays.asList(dungeonA, dungeonB, dungeonA, dungeonB, dungeonA));
        } else {
            assertEquals(sequence, Arrays.asList(dungeonB, dungeonA, dungeonB, dungeonA, dungeonB));
        }
    }
}
