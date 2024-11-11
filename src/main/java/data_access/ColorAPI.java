package data_access;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class ColorAPI {


    //TODO: temp
    public static void main(String[] args) {
        String apiKey = "acc_054cca57db5a48e";  // Replace with your Imagga API Key
        String apiSecret = "6721441a35c051ac95446e1cc94cc6a4";  // Replace with your Imagga API Secret

        OkHttpClient client = new OkHttpClient();

        // Build the Authorization header (Base64 encode api_key:api_secret)
        String base64Credentials = okhttp3.Credentials.basic(apiKey, apiSecret);
        System.out.println(base64Credentials);


        // Image URL to be used for color extraction
        String imageUrl = "https://images.pexels.com/photos/1032650/pexels-photo-1032650.jpeg";
        String url = "https://api.imagga.com/v2/colors?image_url=" + imageUrl + "&extract_overall_colors=1&overall_count=5";

        // Build the request with the Authorization header
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", base64Credentials)  // This is where Basic Auth happens
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
            // Get the response body as a string
            String responseBody = response.body().string();

            // Convert the response string into a JSONObject
            JSONObject jsonResponse = new JSONObject(responseBody);

            // Print the JSON response in a formatted way
            System.out.println(jsonResponse.toString(4));  // Indent by 4 spaces for readability
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
