package data_access;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ColorAPI {


    //TODO: temporarily implemented in main()
    public static void main(String[] args) {
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
                System.out.println(hexColors[i] + " (" + color.getString("closest_palette_color") + ")");
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
