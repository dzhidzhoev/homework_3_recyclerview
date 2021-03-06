package com.tinkoff.androidcourse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String RECYCLER_VIEW_STATE = "MainActivity:RECYCLER_VIEW_STATE";
    private RecyclerView recyclerView;
    private WorkerAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewAdapter = new WorkerAdapter(WorkerGenerator.generateWorkers(20), this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.addItemDecoration(new WorkerItemDecorator());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (savedInstanceState != null) {
            layoutManager.onRestoreInstanceState(savedInstanceState);
        }
        if (getLastCustomNonConfigurationInstance() != null) {
            //noinspection unchecked
            recyclerViewAdapter.restoreState((List<Worker>) getLastCustomNonConfigurationInstance());
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter.addWorker(WorkerGenerator.generateWorker());
                layoutManager.scrollToPosition(recyclerViewAdapter.getItemCount() - 1);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new WorkerAdapter.ItemTouchCallback(recyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (recyclerView.getLayoutManager() != null) {
            outState.putParcelable(RECYCLER_VIEW_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return recyclerViewAdapter.storeState();
    }
}
