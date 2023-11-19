package com.example.dansandroidtestangelaldapaulus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private adapter adapter;
    private RelativeLayout rloading;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvlist);
        rloading = findViewById(R.id.loading);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new adapter();
        recyclerView.setAdapter(adapter);

        fetchJobList();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && !isLastPage && isLastItemDisplayed(recyclerView)) {
                    currentPage++;
                    fetchJobList();
                }
            }
        });
    }
    private void fetchJobList() {
        isLoading = true;
        showLoading();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev3.dansmultipro.co.id/api/recruitment/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiservice apiService = retrofit.create(apiservice.class);

        Call<List<itemjob>> call = apiService.getJobList(
                "java",
                "jakarta",
                "true",
                currentPage
        );

        call.enqueue(new Callback<List<itemjob>>() {
            @Override
            public void onResponse(@NonNull Call<List<itemjob>>call, @NonNull Response<List<itemjob>> response) {
                isLoading = false;
                hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    List<itemjob> jobList = response.body();

                    if (jobList.isEmpty()) {
                        isLastPage = true;
                    }

                    adapter.addJobs(jobList);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<itemjob>> call, @NonNull Throwable t) {
                isLoading = false;
                hideLoading();

                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, notfound.class);
                startActivity(intent);

            }
        });
    }
    private void showLoading() {
        rloading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        rloading.setVisibility(View.GONE);
    }
    private boolean isLastItemDisplayed(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        return (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0;
    }
}
