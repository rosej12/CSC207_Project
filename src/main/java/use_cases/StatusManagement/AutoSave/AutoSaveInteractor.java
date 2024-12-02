package use_cases.StatusManagement.AutoSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;


public class AutoSaveInteractor implements AutoSaveInputBoundary {
    private final AutoSaveDataAccessInterface dataAccess;
    private final AutoSaveOutputBoundary outputBoundary;
    private Boolean lastFastSaved = false;

    File tempDirectory = new File(System.getProperty("user.dir"), "inkflow");

    public AutoSaveInteractor(AutoSaveDataAccessInterface dataAccess, AutoSaveOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;

        if (!tempDirectory.exists()) {
            tempDirectory.mkdir();
        }
    }

    @Override
    public void saveCanvasState(Graphics2D state) {
        try {
            String fileName = "canvas_" + System.currentTimeMillis() + ".png";
            File saveFile = new File(tempDirectory, fileName);

            // Write the BufferedImage to a file in the tempDirectory
            ImageIO.write((RenderedImage) state, "png", saveFile);
            outputBoundary.prepareSuccessView(state);
        } catch (IOException e) {
            outputBoundary.prepareFailView("Error saving drawing: " + e.getMessage());
        }

    }

    @Override
    public RenderedImage loadCanvasState(String filePath) throws IOException {
        // Ensure the directory exists and contains files
        if (!tempDirectory.exists() || !tempDirectory.isDirectory()) {
            throw new IOException("Temporary directory does not exist or is not a directory.");
        }

        // Get the most recently modified file in the directory
        File latestFile = Arrays.stream(Objects.requireNonNull(tempDirectory.listFiles()))
                .filter(file -> file.isFile() && file.getName().endsWith(".png")) // Filter PNG files
                .max(Comparator.comparingLong(File::lastModified)) // Find the most recently modified file
                .orElse(null);

        if (latestFile == null) {
            throw new IOException("No canvas state files found in the directory.");
        }
        lastFastSaved = true;
        return ImageIO.read(latestFile);
    }

        @Override
    public void clearTemporaryFiles() {
        if (lastFastSaved) {
            tempDirectory.delete();
        }

    }
}
