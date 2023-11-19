import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundMain {
    private static Clip hurtSound;
    private static Clip PlayerShoot;
    private static Clip DeathSound;
    private static Clip StartSound;
    private static Clip backgroundMusic;

    // Initialize the sounds when the class is loaded
    static {
        initSound("hurtSound", "./CPlayerHurt_1.wav");
        initSound("PlayerShoot", "./CPlayerShoot_1.wav");
        initSound("DeathSound", "./CBGDeath_1.wav");
        initSound("StartSound", "./CBGStart_1.wav");
        initSound("backgroundMusic", "./CBGUndertale-Hopes-and-Dreams.wav");
    }

    // Method to initialize a sound
    private static void initSound(String soundName, String filePath) {
        AudioInputStream audioInputStream = null;
        try {
            // Load the sound from file
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);

            // Assign the clip to the corresponding variable based on the soundName
            switch (soundName) {
                case "hurtSound":
                    hurtSound = soundClip;
                    break;
                case "PlayerShoot":
                    PlayerShoot = soundClip;
                    break;
                case "DeathSound":
                    DeathSound = soundClip;
                    break;
                case "StartSound":
                    StartSound = soundClip;
                    break;
                case "backgroundMusic":
                    backgroundMusic = soundClip;
                    break;
                // Add more cases for additional sounds
            }
        } catch (Exception e) {
            // Print stack trace if an exception occurs during sound initialization
            e.printStackTrace();
        } finally {
            // Close the audioInputStream
            if (audioInputStream != null) {
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Method to play the hurt sound
    public static void playHurtSound() {
        playSound(hurtSound);
    }

    // Method to play the laser sound
    public static void playLaserSound() {
        playSound(PlayerShoot);
    }

    // Method to play the Death sound
    public static void playDeathSound() {
        playSound(DeathSound);
    }

    // Method to play the Start sound
    public static void playStartSound() {
        playSound(StartSound);
    }

    // Method to play the background music
    public static void playBackgroundMusic() {
        playSound(backgroundMusic);
    }

    // Method to stop the background music
    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public static void stopHurtSound() {
        if (hurtSound != null) {
            hurtSound.stop();
        }
    }

    public static void stopStartSound() {
        if (StartSound != null) {
            StartSound.stop();
        }
    }

    public static void stopDeathSound() {
        if (DeathSound != null) {
            DeathSound.stop();
        }
    }

    public static void stopPlayerLaserSound() {
        if (PlayerShoot != null) {
            PlayerShoot.stop();
        }
    }

    // Method to play a generic sound
    private static void playSound(Clip soundClip) {
        if (soundClip != null) {
            soundClip.setFramePosition(0);
            soundClip.start();
        }
    }
}
