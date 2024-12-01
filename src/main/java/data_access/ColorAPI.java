package data_access;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ColorAPI {
    static String sketchFileName = "sketch.png";
    static String prompt = "A blue apple on a wooden table.";
    static String outputPath = "src/main/resources/generatedImage.jpg";

    static String sketchFilePath = "src/main/resources/" + sketchFileName;


    //TODO: temporarily implemented in this class
    public static void main(String[] args) {
//        byte[] bytes = sketchAPI();
//        System.out.println(bytes.length);
//        Image image = bytesToImage(bytes);
//        displayImage(image);
//        saveImage(image);

    }

    public static void saveImage(Image image) {

        // convert to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);

        // save
        File outputFile = new File(outputPath);
        try {
            ImageIO.write(bufferedImage, "jpg", outputFile); // TODO: assuming jpg
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayImage(Image image) {

        // Create a JLabel to display the image
        JLabel label = new JLabel(new ImageIcon(image));

        // Create a JFrame to hold the label
        JFrame frame = new JFrame("Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static Image bytesToImage(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            BufferedImage bufferedImage = ImageIO.read(bais);
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] imageToBytes(String filePath) {
        try {
            File imageFile = new File(filePath);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            return outputStream.toByteArray();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] sketchAPI() {
        String apiKey = "c13a4d6a84727d7ecf8fbc4b0da96603e514136b2e87a3c690dd6c8054c29bca2ab68ff22261c4aa46f9ef00300b064b";
        String url = "https://clipdrop-api.co/sketch-to-image/v1/sketch-to-image";

        File sketchFile = new File(sketchFilePath);

        // Check if the file exists before proceeding
        if (!sketchFile.exists()) {
            System.out.println("File not found: " + sketchFile.getAbsolutePath());
            return null;
        }

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        // TODO: currently assuming png
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

            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void paletteFromImage() {
        String apiKey = "acc_054cca57db5a48e";
        String apiSecret = "6721441a35c051ac95446e1cc94cc6a4";

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        // Build the Authorization header (Base64 encode api_key:api_secret)
        String base64Credentials = okhttp3.Credentials.basic(apiKey, apiSecret);

        // Image URL to be used for color extraction
        String imageUrl = "https://www.smaku.com/wp-content/uploads/2019/11/DSC_1361.jpg";
        String url = "https://api.imagga.com/v2/colors?image_url=" + imageUrl + "&extract_overall_colors=1&overall_count=5";

        // Build the request with the Authorization header
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", base64Credentials)
                .get()
                .build();

        try {
            // get response
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Print the JSON response in a formatted way
            System.out.println(jsonResponse.toString(4));

            JSONArray imgColors = jsonResponse.getJSONObject("result").getJSONObject("colors").getJSONArray("image_colors");

            // store the hex codes of the colors
            String[] hexColors = new String[imgColors.length()];
            for (int i = 0; i < imgColors.length(); i++) {
                JSONObject color = imgColors.getJSONObject(i);
                hexColors[i] = color.getString("closest_palette_color_html_code");
            }

            // display colors
            JFrame frame = new JFrame();
            frame.setSize(500, 100);
            frame.setLayout(new FlowLayout(FlowLayout.LEFT));

            for (String hexColor : hexColors) {
                JLabel label = new JLabel(hexColor);
                label.setSize(100,100);
                label.setBackground(Color.decode(hexColor));
                label.setOpaque(true);
                frame.add(label);
            }
            frame.setVisible(true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}