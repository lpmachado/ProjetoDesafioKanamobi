package com.example.luispaulo.kanamobi.web;

import com.example.luispaulo.kanamobi.model.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubInterface {

    @GET("search/repositories?q=language:Java&sort=stars")
    Call<GitHubResponse> getJavaPopRepositories(@Query("page") int page);

    @GET("repos/{owner}/{repo}/pulls")
    Call<List<Pull>> getPullRequests(@Path("owner") String owner, @Path("repo") String repo);
}
