package data_access;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.Render.RenderDataAccessInterface;

public class RenderDataAccessObject implements RenderDataAccessInterface {
    private static final int MAXWIDTH = 1024;
    private static final int MAXHEIGHT = 1024;
    private final String sketchFileName = "sketch.png";
    private final String outputPath = "src/main/resources/render.jpg";
    private final String sketchFilePath = "src/main/resources/" + sketchFileName;

    @Override
    public Image getRender(String description, Image sketch) {
        // Convert sketch image to fit conditions
        Image processedSketch = processSketch(sketch);
        saveImage(processedSketch, sketchFilePath);

        // Get Rendered Image
        byte[] bytes = getRenderedImageAsBytes(description);
        Image image = bytesToImage(bytes);

        if (image == null) {
            image = getBlankImage(processedSketch.getWidth(null), processedSketch.getHeight(null));
        }

        // Save Render
        saveImage(image, outputPath);

        return image;
    }

    /**
     * Returns an image that fits the following conditions.
     *  - has a black background with white strokes
     *  - is square (height equals width)
     *  - height and width are <= 1024 px
     *  - is a png
     * @param sketch the sketch image
     * @return an image fitting the above conditions
     */
    private Image processSketch(Image sketch) {
        BufferedImage bufferedImage = getBufferedImage(sketch);

        // Crop the image so it is square
        bufferedImage = cropImageToSquare(bufferedImage);

        // Resize so it is <= 1024 x 1024
        bufferedImage = shrinkImage(bufferedImage, MAXWIDTH, MAXHEIGHT);

        // Invert colors
        bufferedImage = invertColors(bufferedImage);

        return bufferedImage;
    }

    /**
     * Convert white to black, and all other colors to white. (Note: assumes background is white)
     * @param bufferedImage the original image
     * @return the converted image
     */
    private BufferedImage invertColors(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Get color
                int rgb = bufferedImage.getRGB(x, y);
                // Convert
                if (rgb == Color.WHITE.getRGB()) {
                    invertedImage.setRGB(x, y, Color.BLACK.getRGB());
                }
                else {
                    invertedImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return invertedImage;
    }

    private BufferedImage shrinkImage(BufferedImage image, int maxWidth, int maxHeight) {
        int newWidth = Math.min(maxWidth, image.getWidth());
        int newHeight = Math.min(maxHeight, image.getHeight());
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return resizedImage;
    }

    private BufferedImage getBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    private BufferedImage cropImageToSquare(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Find the longer edge
        int cropSize = Math.min(width, height);

        // Find the offset to crop the center
        int offsetX = (width - cropSize) / 2;
        int offsetY = (height - cropSize) / 2;

        // Crop
        return image.getSubimage(offsetX, offsetY, cropSize, cropSize);
    }

    private byte[] getRenderedImageAsBytes(String prompt) {
        String apiKey =
                "c13a4d6a84727d7ecf8fbc4b0da96603e514136b2e87a3c690dd6c8054c29bca2ab68ff22261c4aa46f9ef00300b064b";
        String url = "https://clipdrop-api.co/sketch-to-image/v1/sketch-to-image";

        File sketchFile = new File(sketchFilePath);

        // Check if the file exists before proceeding
        if (!sketchFile.exists()) {
            System.out.println("File not found: " + sketchFile.getAbsolutePath());
            return null;
        }

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                        "sketch_file",
                        sketchFileName,
                        RequestBody.create(MediaType.parse("image/png"), sketchFile)
                )
                .addFormDataPart("prompt", prompt);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-api-key", apiKey)
                .post(bodyBuilder.build())
                .build();

        System.out.println(request.toString());

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Success");
                System.out.println("Remaining credits: " + response.header("x-remaining-credits"));

                return response.body().bytes();

            }
            else {
                System.out.println("Error: " + response.code());
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private Image bytesToImage(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            BufferedImage bufferedImage = ImageIO.read(bais);
            return bufferedImage;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void saveImage(Image image, String filePath) {

        // convert to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);

        // save
        File outputFile = new File(filePath);
        try {
            ImageIO.write(bufferedImage, "jpg", outputFile);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Image getBlankImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        return image;
    }

}
