import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;
import java.io.IOException;

public class SoundMain {
    private static Clip hurtSound;
    private static Clip PlayerShoot;
    private static Clip DeathSound;
    private static Clip StartSound;
    private static Clip backgroundMusic;

    // Inisialisasi suara ketika kelas dimuat
    static {
        initSound("hurtSound", "./CPlayerHurt_1.wav");
        initSound("PlayerShoot", "./CPlayerShoot_1.wav");
        initSound("DeathSound", "./CBGDeath_1.wav");
        initSound("StartSound", "./CBGStart_1.wav");
        initSound("backgroundMusic", "./CBGUndertale-Hopes-and-Dreams.wav");
    }

    // Metode untuk menginisialisasi suara
    private static void initSound(String soundName, String filePath) {
        AudioInputStream audioInputStream = null;
        try {
            // Muat suara dari file
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip soundClip = AudioSystem.getClip();

                        // Set Loop untuk background sound
            if (soundName.equals("backgroundMusic") || soundName.equals("StartSound")) {
                soundClip.loop(Clip.LOOP_CONTINUOUSLY);

                // Add a LineListener to restart the clip when it ends
                soundClip.addLineListener(new LineListener() {
                    @Override
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            soundClip.setFramePosition(0);
                            soundClip.start();
                        }
                    }
                });
            }
            
            // Tetapkan clip ke variabel yang sesuai berdasarkan soundName
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
            }

            soundClip.open(audioInputStream);

        } catch (Exception e) {
            // Cetak jejak tumpukan jika terjadi pengecualian selama inisialisasi suara
            e.printStackTrace();
        } finally {
            // Tutup audioInputStream
            if (audioInputStream != null) {
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Metode untuk memutar suara cedera
    public static void playHurtSound() {
        playSound(hurtSound);
    }

    // Metode untuk memutar suara tembakan pemain
    public static void playLaserSound() {
        playSound(PlayerShoot);
    }

    // Metode untuk memutar suara kematian
    public static void playDeathSound() {
        playSound(DeathSound);
    }

    // Metode untuk memutar suara awal permainan
    public static void playStartSound() {
        if (StartSound == null) {
            initSound("StartSound", "./CBGStart_1.wav");
        }
        playSound(StartSound);
    }

    // Metode untuk memutar musik latar belakang
    public static void playBackgroundMusic() {
        if (backgroundMusic == null) {
            initSound("backgroundMusic", "./CBGUndertale-Hopes-and-Dreams.wav");
        }
        playSound(backgroundMusic);
    }

    // Metode untuk menghentikan musik latar belakang
    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.setFramePosition(0);  // lagu mulai dari awal
            backgroundMusic.close();  // Tutup Clip
            backgroundMusic = null;  // Di set null untuk dibuka lagi
        }
    }

    // Metode untuk menghentikan suara cedera
    public static void stopHurtSound() {
        if (hurtSound != null) {
            hurtSound.stop();
        }
    }

    // Metode untuk menghentikan suara awal permainan
    public static void stopStartSound() {
        if (StartSound != null) {
            StartSound.stop();
            StartSound.setFramePosition(0);
            StartSound.close();  // Tutup Clip
            StartSound = null; // Di set null untuk di buka lagi 
        }
    }

    // Metode untuk menghentikan suara kematian
    public static void stopDeathSound() {
        if (DeathSound != null) {
            DeathSound.stop();
        }
    }

    // Metode untuk menghentikan suara tembakan pemain
    public static void stopPlayerLaserSound() {
        if (PlayerShoot != null) {
            PlayerShoot.stop();
        }
    }

    private static void playSound(Clip soundClip) {
        if (soundClip != null) {
            soundClip.setFramePosition(0);
            soundClip.start();
        }
    }
}
