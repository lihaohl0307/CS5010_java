package dungeon;

/**
 * A builder for constructing a medieval-themed dungeon level while enforcing
 * consistency and target counts for rooms, monsters, and treasures.
 *
 * The builder refuses to:
 *  - Add more than the target number of rooms / monsters / treasures
 *  - Add anything to a room that does not yet exist
 *  - Build the level before all targets are satisfied
 */
public class MedievalLevelBuilder {

    private final int targetRooms;
    private final int targetMonsters;
    private final int targetTreasures;

    private int roomsAdded;
    private int monstersAdded;
    private int treasuresAdded;

    private final Level level;
    private boolean built;

    /**
     * Construct a builder for a medieval level.
     *
     * @param levelNumber     which level is being created
     * @param targetRooms     non-negative target number of rooms
     * @param targetMonsters  non-negative target number of monsters
     * @param targetTreasures non-negative target number of treasures
     * @throws IllegalArgumentException if any target is negative
     */
    public MedievalLevelBuilder(int levelNumber,
                                int targetRooms,
                                int targetMonsters,
                                int targetTreasures) {
        if (levelNumber < 0) {
            throw new IllegalArgumentException("Level number must be non-negative.");
        }
        if (targetRooms < 0 || targetMonsters < 0 || targetTreasures < 0) {
            throw new IllegalArgumentException("Targets must be non-negative.");
        }
        this.targetRooms = targetRooms;
        this.targetMonsters = targetMonsters;
        this.targetTreasures = targetTreasures;
        this.roomsAdded = 0;
        this.monstersAdded = 0;
        this.treasuresAdded = 0;
        this.level = new Level(levelNumber);
        this.built = false;
    }

    // ----------------------------- Helpers -----------------------------

    private void ensureNotBuilt() {
        if (built) {
            throw new IllegalStateException("Level already built.");
        }
    }

    private void ensureRoomExists(int roomNumber) {
        if (roomNumber < 0 || roomNumber >= roomsAdded) {
            throw new IllegalArgumentException("Target room has not yet been created: " + roomNumber);
        }
    }

    private void ensureRoomCapacityAvailable() {
        if (roomsAdded >= targetRooms) {
            throw new IllegalStateException("Target number of rooms already reached.");
        }
    }

    private void ensureMonsterCapacityAvailable(int incoming) {
        if (incoming <= 0) {
            throw new IllegalArgumentException("Number of monsters to add must be positive.");
        }
        if (monstersAdded >= targetMonsters || monstersAdded + incoming > targetMonsters) {
            throw new IllegalStateException("Adding monsters would exceed target.");
        }
    }

    private void ensureTreasureCapacityAvailable(int incoming) {
        if (incoming <= 0) {
            throw new IllegalArgumentException("Number of treasures to add must be positive.");
        }
        if (treasuresAdded >= targetTreasures || treasuresAdded + incoming > targetTreasures) {
            throw new IllegalStateException("Adding treasures would exceed target.");
        }
    }

    // ---------------------------- Rooms --------------------------------

    /**
     * Add a room with the given description.
     *
     * @param description room description
     * @throws IllegalStateException if adding would exceed target rooms
     */
    public void addRoom(String description) {
        ensureNotBuilt();
        ensureRoomCapacityAvailable();
        level.addRoom(description);
        roomsAdded++;
    }

    // --------------------------- Monsters ------------------------------

    /**
     * Add the specified number of goblins (7 HP each) to a room.
     *
     * @param roomNumber target room index
     * @param number     number of goblins (must be > 0)
     * @throws IllegalStateException    if monster target reached/exceeded
     * @throws IllegalArgumentException if room not yet created
     */
    public void addGoblins(int roomNumber, int number) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        ensureMonsterCapacityAvailable(number);

        final String desc =
                "mischievous and very unpleasant, vengeful, and greedy creature whose primary purpose is to cause trouble to humankind";
        for (int i = 0; i < number; i++) {
            level.addMonster(roomNumber, new Monster("goblin", desc, 7));
            monstersAdded++;
        }
    }

    /**
     * Add one orc (20 HP) to a room.
     */
    public void addOrc(int roomNumber) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        ensureMonsterCapacityAvailable(1);

        final String desc =
                "brutish, aggressive, malevolent being serving evil";
        level.addMonster(roomNumber, new Monster("orc", desc, 20));
        monstersAdded++;
    }

    /**
     * Add one ogre (50 HP) to a room.
     */
    public void addOgre(int roomNumber) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        ensureMonsterCapacityAvailable(1);

        final String desc =
                "large, hideous man-like being that likes to eat humans for lunch";
        level.addMonster(roomNumber, new Monster("ogre", desc, 50));
        monstersAdded++;
    }

    /**
     * Add a human with provided details to a room.
     */
    public void addHuman(int roomNumber, String name, String description, int hitPoints) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        if (hitPoints <= 0) {
            throw new IllegalArgumentException("Human hit points must be positive.");
        }
        ensureMonsterCapacityAvailable(1);

        level.addMonster(roomNumber, new Monster(name, description, hitPoints));
        monstersAdded++;
    }

    // --------------------------- Treasures -----------------------------

    /**
     * Add a healing potion (value = 1).
     */
    public void addPotion(int roomNumber) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        ensureTreasureCapacityAvailable(1);

        level.addTreasure(roomNumber, new Treasure("a healing potion", 1));
        treasuresAdded++;
    }

    /**
     * Add pieces of gold with the specified value.
     */
    public void addGold(int roomNumber, int value) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        if (value <= 0) {
            throw new IllegalArgumentException("Gold value must be positive.");
        }
        ensureTreasureCapacityAvailable(1);

        level.addTreasure(roomNumber, new Treasure("pieces of gold", value));
        treasuresAdded++;
    }

    /**
     * Add a weapon (all weapons have value = 10).
     */
    public void addWeapon(int roomNumber, String weapon) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        ensureTreasureCapacityAvailable(1);

        level.addTreasure(roomNumber, new Treasure(weapon, 10));
        treasuresAdded++;
    }

    /**
     * Add a special treasure with a custom description and value.
     */
    public void addSpecial(int roomNumber, String description, int value) {
        ensureNotBuilt();
        ensureRoomExists(roomNumber);
        if (value <= 0) {
            throw new IllegalArgumentException("Treasure value must be positive.");
        }
        ensureTreasureCapacityAvailable(1);

        level.addTreasure(roomNumber, new Treasure(description, value));
        treasuresAdded++;
    }

    // ----------------------------- Build -------------------------------

    /**
     * Finish building and return the Level.
     *
     * @return the completed Level
     * @throws IllegalStateException if the level is not yet complete
     */
    public Level build() {
        ensureNotBuilt();
        if (roomsAdded != targetRooms ||
                monstersAdded != targetMonsters ||
                treasuresAdded != targetTreasures) {
            throw new IllegalStateException(
                    String.format("Level incomplete: rooms %d/%d, monsters %d/%d, treasures %d/%d",
                            roomsAdded, targetRooms, monstersAdded, targetMonsters, treasuresAdded, targetTreasures));
        }
        built = true;
        return level;
    }
}
