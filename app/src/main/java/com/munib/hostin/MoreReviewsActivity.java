package com.munib.hostin;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.munib.hostin.Adapters.ReviewsAdapter;
import com.munib.hostin.DataModel.HostelsData;
import com.munib.hostin.DataModel.Reviews;

import java.util.ArrayList;

/**
 * Created by rana_ on 12/14/2017.
 */

public class MoreReviewsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_more_reviews);

        ArrayList<Reviews> reviews_list=(ArrayList<Reviews>) getIntent().getSerializableExtra("reviews");
        Log.d("mubi-r",reviews_list.size()+"ss");
        RecyclerView rv_reviews = (RecyclerView)findViewById(R.id.rv_reviews);

        ReviewsAdapter adapter = new ReviewsAdapter(this,reviews_list);

        rv_reviews.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_reviews.setNestedScrollingEnabled(false);
        rv_reviews.setLayoutManager(layoutManager);
        rv_reviews.setAdapter(adapter);

    }
}
