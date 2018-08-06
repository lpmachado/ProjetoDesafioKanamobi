package com.example.luispaulo.kanamobi.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.luispaulo.kanamobi.model.Repository;
import com.example.luispaulo.kanamobi.web.GitHubInterface;
import com.example.luispaulo.kanamobi.web.GitHubResponse;
import com.example.luispaulo.kanamobi.web.WebClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadRepositories extends AsyncTask {

    private final Context mContext;
    private final int mPage;
    private Listener mListener;
    private ProgressDialog dialog;

    public LoadRepositories(Context context, Listener listener, int page) {
        mContext = context;
        mListener = listener;
        mPage = page;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "Please wait", "Searching repositories...", true, false);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        httpGetRepositories();
        return null;
    }

    private void httpGetRepositories() {
        GitHubInterface ghInterface = WebClient.getClient().create(GitHubInterface.class);
        Call<GitHubResponse> call = ghInterface.getJavaPopRepositories(mPage);
        call.enqueue(new Callback<GitHubResponse>() {
            @Override
            public void onResponse(Call<GitHubResponse> call, Response<GitHubResponse> response) {
                if(response.isSuccessful()){
                    dialog.dismiss();
                    mListener.onLoadRepositoriesTaskCompleted(response.body().getItems());
                } else {
                    dialog.dismiss();
                    mListener.onLoadRepositoriesTaskFailed("It wasn't possible to search the repositories, please try again late.");
                }
            }

            @Override
            public void onFailure(Call<GitHubResponse> call, Throwable t) {
                if(t instanceof IOException){
                    mListener.onLoadRepositoriesTaskFailed("Failed on attempt to connect to the server, please check your internet connection.");
                } else {
                    mListener.onLoadRepositoriesTaskFailed("So sorry, there is anything wrong! try again late.");
                }
                dialog.dismiss();
                Log.e("HTTP", "", t);
            }
        });
    }

    public interface Listener {

        void onLoadRepositoriesTaskCompleted(List<Repository> repositoryList);
        void onLoadRepositoriesTaskFailed(String message);
    }
}

