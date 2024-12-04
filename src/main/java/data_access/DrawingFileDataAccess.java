package data_access;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import use_case.Drawing.DrawingDataAccessInterface;

public class DrawingFileDataAccess implements DrawingDataAccessInterface {

    @Override
    public void saveDrawing(RenderedImage image, File file) throws IOException {
        ImageIO.write(image, "png", file);
    }
}
