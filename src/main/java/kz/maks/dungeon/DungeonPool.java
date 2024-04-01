package kz.maks.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DungeonPool {

    private final Map<Dungeon, ArrayList<Dungeon>> dungeons = new HashMap<>();

    public DungeonPool(List<Dungeon> dungeonList) {
        if (dungeonList.isEmpty()) {
            throw new RuntimeException("Empty dungeons list provided!");
        }

        for (Dungeon dungeon : dungeonList) {
            if (!dungeon.isOpen()) {
                throw new RuntimeException("List contains closed dungeon!");
            }

            ArrayList<Dungeon> compatibles = new ArrayList<>();
            dungeons.put(dungeon, compatibles);

            for (Dungeon d : dungeonList) {
                if (!dungeon.equals(d) && dungeon.isCompatible(d)) {
                    compatibles.add(d);
                }
            }

            if (compatibles.isEmpty()) {
                throw new RuntimeException("List contain dungeon without any compatibles!");
            }
        }
    }

    public List<Dungeon> createXSequence(int length) {
        ArrayList<Dungeon> sequence = new ArrayList<>();

        Dungeon nextDungeon = dungeons.keySet().stream()
                .skip(new Random().nextInt(dungeons.size()))
                .findFirst().get();

        sequence.add(nextDungeon);

        for (int i = 1; i < length; i++) {
            List<Dungeon> compatibles = dungeons.get(nextDungeon);
            nextDungeon = compatibles.get(compatibles.size() > 1
                    ? new Random().nextInt(compatibles.size()) : 0);

            sequence.add(nextDungeon);
        }

        return sequence;
    }
}