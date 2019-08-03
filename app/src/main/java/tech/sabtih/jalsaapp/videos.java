package tech.sabtih.jalsaapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.sabtih.jalsaapp.dummy.DummyContent;
import tech.sabtih.jalsaapp.dummy.JalsaMedia;
import tech.sabtih.jalsaapp.net.Api;
import tech.sabtih.jalsaapp.net.ApiInterface;

public class videos extends AppCompatActivity implements mediaFragment.OnListFragmentInteractionListener {

    RecyclerView rv;
    videos vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.list);

        vid = this;
        ApiInterface service = Api.getRetrofitInstance().create(ApiInterface.class);

        Call<List<JalsaMedia>> media = service.getMediaList();
        media.enqueue(new Callback<List<JalsaMedia>>() {
            @Override
            public void onResponse(Call<List<JalsaMedia>> call, Response<List<JalsaMedia>> response) {


                RecyclerView recyclerView = rv;
                if (2 <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
                }
                recyclerView.setAdapter(new MymediaRecyclerViewAdapter(response.body(), vid));

            }

            @Override
            public void onFailure(Call<List<JalsaMedia>> call, Throwable t) {
                Toast.makeText(videos.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getLocalizedMessage());
            }
        });




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(JalsaMedia item) {

    }
}
