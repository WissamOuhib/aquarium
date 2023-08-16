package model;

import aquarium.model.Coordinate;
import aquarium.model.Fish;
import aquarium.model.Config;
import aquarium.model.FishFactory;
import aquarium.model.FishService;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FishServiceTest {

    private FishService fishService;
    @Mock
    private Config config;

    @Mock
    private Pane aquariumPane;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fishService = FishService.getInstance(aquariumPane);
    }

   // @Test
    void testAddFishWithRandomPosition() {
        // Mock the Coordinate generation
        Coordinate mockPosition = new Coordinate(100, 100);
        when(Coordinate.generateRandomCoordinates(aquariumPane)).thenReturn(mockPosition);

        // Mock the FishFactory
        Fish mockFish = mock(Fish.class);
        when(FishFactory.createFish(100, 100)).thenReturn(mockFish);

        // Mock the Fish's behavior
        doNothing().when(mockFish).initializeFish();
        when(mockFish.getX()).thenReturn(100.0);
        when(mockFish.getY()).thenReturn(100.0);

        Fish addedFish = fishService.addFishWithRandomPosition();

        // Verify interactions
        verify(mockFish).initializeFish();
        verify(config).addOccupiedCoordinate(100.0, 100.0);
        verify(config).incrementSpawnedFishCount();
        verify(mockFish).move();

        // Check the returned fish
        assertEquals(mockFish, addedFish);
    }

    //@Test
    void testRemoveFish() {
        Fish mockFish = mock(Fish.class);
        when(mockFish.getX()).thenReturn(150.0);
        when(mockFish.getY()).thenReturn(150.0);

        fishService.removeFish(mockFish);

        verify(mockFish).getX();
        verify(mockFish).getY();
        verify(config).freeOccupiedCoordinate(150.0, 150.0);
        verify(config).decrementSpawnedFishCount();
        verify(fishService.getFishList()).remove(mockFish);
    }
}
