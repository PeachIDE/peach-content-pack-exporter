package com.github.mouse0w0.mce.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class ImageUtils {

    public static void writePNGImage(BufferedImage image, Path file) throws IOException {
        FileUtils.createFileIfNotExists(file);
        ImageIO.write(image, "png", file.toFile());
    }
}
