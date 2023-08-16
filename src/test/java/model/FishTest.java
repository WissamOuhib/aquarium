package model;

import aquarium.model.Coordinate;
import aquarium.model.Fish;
import aquarium.model.FishService;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class FishTest {

    @Mock private ImageView imageView;
    private Coordinate coordinate;
    private Fish fish;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fish = new Fish(100, 100, imageView);
        coordinate = new Coordinate(10.0,20.0);
    }

    @Test
    public void testInitializeFish() {
        double initialX = 50;
        double initialY = 60;

        fish = new Fish(initialX, initialY, imageView);
        fish.initializeFish();

        // Verify that the ImageView is properly positioned and visible
        verify(imageView).setLayoutX(initialX);
        verify(imageView).setLayoutY(initialY);
        verify(imageView).setVisible(true);

    }

    //@Test
    public void testMove() {
        // Mocked ImageView methods
        when(imageView.getLayoutX()).thenReturn(100.0);
        when(imageView.getLayoutY()).thenReturn(100.0);
        when(imageView.getScaleX()).thenReturn(1.0); // Initial scale

        fish.setSpeedX(1); // Move to the right
        fish.setSpeedY(0); // No vertical movement

        fish.move();

        // Verify that the ImageView layout is updated
        verify(imageView).setLayoutX(101.0); // Initial position + speedX
        verify(imageView, never()).setScaleX(-1); // No direction change

        // Verify that the fish's position is updated
        assert fish.getX() == 101.0;
        assert fish.getY() == 100.0;

        // Verify that occupied space is updated
        verify(coordinate, times(1)).updateOccupiedSpace(100, 100, 101, 100);

    }

    //@Test
    public void testMoveCollidesWithOtherFish() {
        // Mocked ImageView methods
        when(imageView.getLayoutX()).thenReturn(100.0);
        when(imageView.getLayoutY()).thenReturn(100.0);
        when(imageView.getScaleX()).thenReturn(1.0); // Initial scale

        fish.setSpeedX(1); // Move to the right
        fish.setSpeedY(0); // No vertical movement

        // Mock a collision with another fish
        Fish otherFish = mock(Fish.class);
        Set<Coordinate> otherFishCoordinates = new HashSet<>();
        otherFishCoordinates.add(new Coordinate(110, 100));
        when(otherFish.getX()).thenReturn(110.0);
        when(otherFish.getY()).thenReturn(100.0);
        when(Coordinate.calculateOccupiedSpace(110.0, 100.0)).thenReturn(otherFishCoordinates);
        when(FishService.getFishList()).thenReturn(Collections.singletonList(otherFish));

        fish.move();

        // Verify that the direction is changed due to collision
        verify(imageView, never()).setLayoutX(anyDouble()); // No position change
        verify(imageView).setScaleX(-1); // Direction change
    }

}
