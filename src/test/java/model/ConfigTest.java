package model;

import aquarium.model.Config;
import aquarium.model.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    private Config config;

    @BeforeEach
    public void setUp() {
        config = Config.getInstance();
    }

    @Test
    public void testUpdateAquariumDimensions() {
        config.updateAquariumDimensions(800, 600);
        assertEquals(800, Config.getAquarium_Width());
        assertEquals(600, Config.getAquarium_height());
    }

    @Test
    public void testAddAndFreeOccupiedCoordinate() {
        config.addOccupiedCoordinate(20, 30);
        assertTrue(Config.getOccupiedCoordinates().contains(new Coordinate(20, 30)));

        config.freeOccupiedCoordinate(20, 30);
        assertFalse(Config.getOccupiedCoordinates().contains(new Coordinate(20, 30)));
    }

    @Test
    public void testCanSpawnMoreFish() {
        config.setMaxFishCount(5);
        config.setSpawnedFishCount(3);
        assertTrue(config.canSpawnMoreFish());

        config.setSpawnedFishCount(5);
        assertFalse(config.canSpawnMoreFish());
    }

    @Test
    public void testIncrementAndDecrementSpawnedFishCount() {
        config.setSpawnedFishCount(0);
        assertEquals(0, Config.getSpawnedFishCount());

        config.incrementSpawnedFishCount();
        assertEquals(1, Config.getSpawnedFishCount());

        config.decrementSpawnedFishCount();
        assertEquals(0, Config.getSpawnedFishCount());
    }

}
