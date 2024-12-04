package data_access;

import org.junit.jupiter.api.Test;
import use_case.Drawing.DrawingDataAccessInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DrawingFileDataAccessTest {

    @Test
    public void testSaveDrawing() throws IOException {
        DrawingDataAccessInterface dataAccess = new DrawingFileDataAccess();
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        File tempFile = File.createTempFile("test_drawing", ".png");
        tempFile.deleteOnExit();

        dataAccess.saveDrawing(image, tempFile);

        // Verify that the file was created and is not empty
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);

        // Optionally, verify that the image can be read back
        BufferedImage loadedImage = ImageIO.read(tempFile);
        assertNotNull(loadedImage);
        assertEquals(100, loadedImage.getWidth());
        assertEquals(100, loadedImage.getHeight());
    }
}

