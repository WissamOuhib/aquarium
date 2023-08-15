package aquarium.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundManager {
    private MediaPlayer MediaPlayer;
    private static SoundManager SoundManager_instance;
    private SoundStrategy soundStrategy;

    private SoundManager(){}

    public static SoundManager get_instance() {
        if (SoundManager_instance == null) SoundManager_instance = new SoundManager();
        return SoundManager_instance;
    }

    public void playAmbientSound() {
        // Create a Media object with the path to audio file
        String audioPath = SoundManager.class.getResource("/assets/fish-tank-ambiance.mp3").toExternalForm();
        Media media = new Media(audioPath);

        // Create a MediaPlayer
        MediaPlayer = new MediaPlayer(media);
        MediaPlayer.setVolume(1); // Adjust the volume
        MediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the music indefinitely

        // Play the music
        MediaPlayer.play();
    }

    public void playSound(SoundStrategy soundStrategy) {
        String failSoundPath = getClass().getResource(soundStrategy.getSoundFilePath()).toExternalForm();
        Media sound = new Media(failSoundPath);
        MediaPlayer = new MediaPlayer(sound);
        MediaPlayer.seek(Duration.ZERO);
        MediaPlayer.play();
    }

    public void playFishPopInSound() {
        soundStrategy = new FishPopInSoundStrategy();
        playSound(soundStrategy);
    }

    public void playFishPopOutSound() {
        soundStrategy = new FishPopOutSoundStrategy();
        playSound(soundStrategy);
    }

    public void playMaxFishReachedSound() {
        soundStrategy = new MaxFishReachedSoundStrategy();
        playSound(soundStrategy);
    }

}
