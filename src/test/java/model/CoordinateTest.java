package model;

import aquarium.model.Config;
import aquarium.model.Coordinate;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CoordinateTest {

    private Pane mockAquariumPane;

    @BeforeEach
    public void setUp() {
        mockAquariumPane = mock(Pane.class);
        when(mockAquariumPane.getWidth()).thenReturn(800.0);
        when(mockAquariumPane.getHeight()).thenReturn(600.0);
    }

    @Test
    public void testGenerateRandomCoordinates() {
        Coordinate generatedCoordinate = Coordinate.generateRandomCoordinates(mockAquariumPane);
        assertNotNull(generatedCoordinate);
        assertTrue(generatedCoordinate.getX() >= 0 && generatedCoordinate.getX() < 800);
        assertTrue(generatedCoordinate.getY() >= 0 && generatedCoordinate.getY() < 600);
    }

    @Test
    public void testHasOverlap() {
        Config.getOccupiedCoordinates().clear();
        Config.getOccupiedCoordinates().add(new Coordinate(150, 150));

        assertTrue(Coordinate.hasOverlap(100, 100));
        assertFalse(Coordinate.hasOverlap(10, 10));
    }

    @Test
    public void testCalculateOccupiedSpace() {
        int gridCellSize = Config.getGridCellSize();
        int fishWidth = Config.getFish_Width();
        int fishHeight = Config.getFish_height();

        Set<Coordinate> occupiedCoordinates = Coordinate.calculateOccupiedSpace(25, 35);

        Set<Coordinate> expectedCoordinates = IntStream.range(2, 2 + fishWidth / gridCellSize)
                .boxed()
                .flatMap(i -> IntStream.range(3, 3 + fishHeight / gridCellSize)
                        .mapToObj(j -> new Coordinate(i * gridCellSize, j * gridCellSize)))
                .collect(Collectors.toSet());

        assertEquals(expectedCoordinates, occupiedCoordinates);
    }

//    @Test
//    public void testUpdateOccupiedSpace() {
//        double oldX = 0;
//        double oldY = 0;
//        double newX = 120;
//        double newY = 0;
//
//        Set<Coordinate> oldOccupiedCoordinates = new HashSet<>();
//        for (int i = 0; i < Config.getFish_Width(); i += Config.getGridCellSize()) {
//            for (int j = 0; j < Config.getFish_height(); j += Config.getGridCellSize()) {
//                oldOccupiedCoordinates.add(new Coordinate(i, j));
//            }
//        }
//
//        Set<Coordinate> newOccupiedCoordinates = new HashSet<>();
//        for (int i = 0; i < Config.getFish_Width(); i += Config.getGridCellSize()) {
//            for (int j = 0; j < Config.getFish_height(); j += Config.getGridCellSize()) {
//                newOccupiedCoordinates.add(new Coordinate(i+newX, j));
//            }
//        }
//
//        Config.setOccupiedCoordinates(oldOccupiedCoordinates);
//
//        Coordinate.updateOccupiedSpace(oldX, oldY, newX, newY);
//
//        Set<Coordinate> expectedOccupiedCoordinates = new HashSet<>();
//        for (int i = 0; i < Config.getFish_Width(); i += Config.getGridCellSize()) {
//            for (int j = 0; j < Config.getFish_height(); j += Config.getGridCellSize()) {
//                expectedOccupiedCoordinates.add(new Coordinate(i, j));
//            }
//        }
//
//        assertEquals(expectedOccupiedCoordinates, Config.getOccupiedCoordinates());
//    }

}

