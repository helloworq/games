package org.game.plane.source;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class GameSource {
    private static ConcurrentHashMap<Integer, BufferedImage> IMAGE_MAP = new ConcurrentHashMap<>();
    private static final String BASE_PATH = "/images/";
    private static final String IMAGE_PREFIX = ".png";

    static {
        IMAGE_MAP.put(1, Objects.requireNonNull(readImage("plane/复古飞机" + IMAGE_PREFIX)));
        IMAGE_MAP.put(2, Objects.requireNonNull(readImage("plane/现代飞机" + IMAGE_PREFIX)));
        IMAGE_MAP.put(3, Objects.requireNonNull(readImage("bullet/小火子弹" + IMAGE_PREFIX)));
        IMAGE_MAP.put(4, Objects.requireNonNull(readImage("bullet/火球子弹" + IMAGE_PREFIX)));
        IMAGE_MAP.put(5, Objects.requireNonNull(readImage("bullet/蓝色火球子弹" + IMAGE_PREFIX)));
    }

    public static int get(){
        return 111;
    }

    public static BufferedImage get(Integer id) {
        return IMAGE_MAP.get(id);
    }

    private static BufferedImage readImage(String filename) {
        try {
            return ImageIO.read(
                    Objects.requireNonNull(GameSource.class.getResourceAsStream(BASE_PATH + filename))
            );
        } catch (Exception e) {
            return null;
        }
    }
}
