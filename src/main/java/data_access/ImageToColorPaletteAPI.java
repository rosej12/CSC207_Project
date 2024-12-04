package data_access;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import use_case.ImageToColorPalette.ImageToColorPaletteDataAccessInterface;

public class ImageToColorPaletteAPI implements ImageToColorPaletteDataAccessInterface {

    private final String apiKey;
    private final String apiSecret;
    private final OkHttpClient client = new OkHttpClient();

    public ImageToColorPaletteAPI(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @Override
    public String[] getColorPalette(File imageFile) throws IOException {
        // Implement the API call to Imagga's color extraction API.
        String credentials = Credentials.basic(apiKey, apiSecret);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getName(),
                        RequestBody.create(imageFile, MediaType.parse("image/jpeg")))
                .build();

        Request request = new Request.Builder()
                .url("https://api.imagga.com/v2/colors")
                .header("Authorization", credentials)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Parse the JSON response
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray colorsArray = jsonObject.getJSONObject("result")
                    .getJSONObject("colors")
                    .getJSONArray("image_colors");

            List<String> colorList = new ArrayList<>();
            for (int i = 0; i < colorsArray.length(); i++) {
                JSONObject colorObj = colorsArray.getJSONObject(i);
                String hexCode = "#" + colorObj.getString("html_code").substring(1).toUpperCase();
                colorList.add(hexCode);
            }

            return colorList.toArray(new String[0]);
        }
    }
}
