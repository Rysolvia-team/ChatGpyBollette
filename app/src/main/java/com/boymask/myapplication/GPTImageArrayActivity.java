package com.boymask.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boymask.RysLogger;
import com.boymask.myapplication.listaparametri.RowModel;
import com.boymask.myapplication.listaparametri.TableAdapter;
import com.boymask.myapplication.retrofit.ApiGpt;
import com.boymask.myapplication.retrofit.OpenAIApi;
import com.boymask.myapplication.retrofit.RetrofitClient;
import com.boymask.testpay.retrofit_boot.PaymentApi;
import com.boymask.testpay.retrofit_boot.RetrofitBootClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GPTImageArrayActivity extends AppCompatActivity {
    String API_KEY = MainActivity2.API_KEY;
    private ArrayList<RowModel> data = new ArrayList<>();
    private Button askGpt;
    private View progressBar;
    private View loadingText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gptdata_reader);

        boolean isRemote = getIntent().getBooleanExtra("isRemote", false);

        progressBar = findViewById(R.id.progressBar);
        loadingText = findViewById(R.id.loadingText);
        askGpt = findViewById(R.id.askgpt);
        View loadingContainer = findViewById(R.id.loadingContainer);
        recyclerView = findViewById(R.id.recyclerView);

// all'inizio mostro loading e nascondo lista
        loadingContainer.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        askGpt.setVisibility(View.GONE);

        ArrayList<String> paths = getIntent().getStringArrayListExtra("content");
        if (paths == null || paths.isEmpty()) {
            finish();
            return;
        }

        if (isRemote)
            analyzeImagesRemote(paths);
        else
            analyzeImages(paths);
    }

    private void analyzeImagesRemote(ArrayList<String> paths) {
        List<String> filesbase64 = new ArrayList<>();
        for (String path : paths)
            filesbase64.add( encodeImageToBase64(path));

        Map<String, Object> body = new HashMap<>();
        body.put("prompt", Prompt.PROMPT_ASK);
        body.put("images", filesbase64);


        ApiGpt api = RetrofitBootClient.getClient().create(ApiGpt.class);


        api.analyze(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        String val = response.body().string();
                        runOnUiThread(() -> reportOutput(val));
                    }
                    if (response.errorBody() != null) {
                        String val = response.errorBody().string();
                        runOnUiThread(() -> reportOutput(val));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    RysLogger.add(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                RysLogger.add(t);
            }
        });
    }

    private void analyzeImages(ArrayList<String> imagePaths) {

        try {
            JSONObject requestJson = new JSONObject();
            requestJson.put("model", Prompt.MODEL);

            JSONArray inputArray = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");

            JSONArray contentArray = new JSONArray();

            // 🧠 Prompt testuale
            contentArray.put(new JSONObject()
                    .put("type", "input_text")
                    .put("text", Prompt.PROMPT_ASK));

            // 🖼️ immagini
            for (String path : imagePaths) {
                String base64 = encodeImageToBase64(path);
                if (base64 == null) continue;
               /* contentArray.put(new JSONObject()
                        .put("type", "input_image")
                        .put("image_base64", base64));*/
                contentArray.put(new JSONObject()
                        .put("type", "input_image")
                        .put("image_url", "data:image/jpeg;base64," + base64));
            }

            message.put("content", contentArray);
            inputArray.put(message);
            requestJson.put("input", inputArray);

            RequestBody body = RequestBody.create(
                    requestJson.toString(),
                    MediaType.parse("application/json")
            );

            OpenAIApi api = RetrofitClient.getClient();

            api.analyzeFile("Bearer " + API_KEY, body)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if (response.body() != null) {
                                    String val = response.body().string();
                                    runOnUiThread(() -> reportOutput(val));
                                }
                                if (response.errorBody() != null) {
                                    String val = response.errorBody().string();
                                    runOnUiThread(() -> reportOutput(val));
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                RysLogger.add(e);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            RysLogger.add(t);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            RysLogger.add(e);
        }
    }

    private String encodeImageToBase64(String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            fis.close();

            return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void reportOutput(String string) {
        runOnUiThread(() -> {
            //   loadingContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            askGpt.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);
            loadingText.setVisibility(View.GONE);
        });

        //     RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // 🔥 dati iniziali
        data.clear();

        try {
            try {
                JSONObject jsonObject = new JSONObject(string);

                JSONArray output = jsonObject.optJSONArray("output");
                if (output == null || output.length() == 0) return;

                JSONObject first = output.optJSONObject(0);
                if (first == null) return;

                JSONArray content = first.optJSONArray("content");
                if (content == null || content.length() == 0) return;

                JSONObject c0 = content.optJSONObject(0);
                if (c0 == null) return;

                String text = c0.optString("text");
                text = text.replace("```json", "")
                        .replace("```", "")
                        .trim();
                //    JSONObject innerJson = new JSONObject(text);
                setValues2(text, data);
            }catch(JSONException je){
                RysLogger.add(je);
                setValues2(string, data);
            }
            try {

                TableAdapter adapter = new TableAdapter(data);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
                RysLogger.add(e);

            }

        } catch (Exception e) {
            e.printStackTrace();
            RysLogger.add(e);
        }

    }

    private void setValues2(String value, ArrayList<RowModel> data) {


        data.add(new RowModel(value, ""));

    }
 /*   private void setValues(String key, String value, ArrayList<RowModel> data) {

        String s = key + ":" + value;
        s = s.replace("\"", "");

        String[] coppie = s.split(",");

        for (String coppia : coppie) {
            int index = coppia.lastIndexOf(":");

            if (index == -1) {
                data.add(new RowModel(coppia.trim(), ""));
                continue;
            }

            String v1 = coppia.substring(0, index).trim();
            String v2 = coppia.substring(index + 1).trim();

            data.add(new RowModel(v1, v2));
        }
    }*/
}