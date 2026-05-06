package com.boymask.myapplication.callbacks;

import android.app.Activity;

import com.boymask.RysLogger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RysGPTCallback<T extends ResponseBody> implements Callback<T> {
    private CanReportOutput rep;
    private Activity activity;

    public RysGPTCallback(Activity activity,CanReportOutput rep ) {
        this.rep = rep;
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {
            System.out.println("CALLBACK");

            if (response.body() != null) {
                String val = response.body().string();
                activity.runOnUiThread(() -> rep.reportOutput(val));
            }
            if (response.errorBody() != null) {
                String val = response.errorBody().string();
                activity.runOnUiThread(() -> rep.reportOutput(val));
            }

        } catch (Exception e) {
            e.printStackTrace();
            RysLogger.add(e);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
        RysLogger.add(t);
    }
}
