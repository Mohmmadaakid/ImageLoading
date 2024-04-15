package com.example.assignmentimageloading.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentimageloading.R;
import com.example.assignmentimageloading.adapters.ImageAdapter;
import com.example.assignmentimageloading.api.ApiUtilities;
import com.example.assignmentimageloading.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> list;
    private GridLayoutManager manager;
    private ImageAdapter adapter;
    private int page=1;
    private ProgressDialog dialog;
    private int pageSize=30;
    private boolean isLoading;
    private boolean isLastPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        list=new ArrayList<>();
        adapter=new ImageAdapter(this,list);
        manager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        if (isNetworkConnected())
        {
            getData();


        }
        else {
            Toast.makeText(this,"No internet Connection",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem=manager.getChildCount();
                int totalItem=manager.getItemCount();
                int firstvisibleitempos=manager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage)
                {
                    if ((visibleItem+firstvisibleitempos>=totalItem)
                    && firstvisibleitempos>=0&&totalItem>=pageSize){
                        page++;
                        getData();
                    }

                }
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager!=null)
        {
            NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
            return activeNetwork!=null&&activeNetwork.isConnectedOrConnecting();
        }
        return false;

    }

    private void getData() {
        isLoading=true;
        ApiUtilities.getApiInterface().getImages(page,30)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        if (response.body()!=null)
                        {
                            list.addAll(response.body());
                            adapter.notifyDataSetChanged();

                        }
                        isLoading=false;
                        dialog.dismiss();

                        if (list.size()>0)
                        {
                            isLastPage=list.size()<pageSize;
                        }else isLastPage=true;

                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();


                    }
                });
    }
}