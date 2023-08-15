package aquarium.sound;
import lombok.Getter;

public abstract class SoundStrategy {
    @Getter
    protected String soundFilePath;

    public SoundStrategy(String soundFilePath) {
        this.soundFilePath = soundFilePath;
    }

}
