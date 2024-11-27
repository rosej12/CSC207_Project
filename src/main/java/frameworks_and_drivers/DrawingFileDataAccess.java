package frameworks_and_drivers;

import use_cases.Drawing.DrawingDataAccessInterface;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class DrawingFileDataAccess implements DrawingDataAccessInterface {

    @Override
    public void saveDrawing(RenderedImage image, File file) throws IOException {
        ImageIO.write(image, "png", file);
    }
}
