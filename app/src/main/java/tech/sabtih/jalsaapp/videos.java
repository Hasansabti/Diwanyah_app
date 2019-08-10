package tech.sabtih.jalsaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;
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
    static String parent;

    private int showmenu = R.menu.mediaselect;
    Toolbar tb;

    static MymediaRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = findViewById(R.id.list);
        tb = findViewById(R.id.mymenu);

        vid = this;

        tb.setTitle("Editing");
        tb.inflateMenu(R.menu.mediaselect);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.delete){
                    ApiInterface service = Api.getRetrofitInstance().create(ApiInterface.class);
                    Call<List<JalsaMedia>> media = service.deletefiles(adapter.selected, parent);
                    media.enqueue(new Callback<List<JalsaMedia>>() {
                        @Override
                        public void onResponse(Call<List<JalsaMedia>> call, Response<List<JalsaMedia>> response) {
                            adapter.setValues(response.body());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<List<JalsaMedia>> call, Throwable t) {

                        }
                    });
                 }

                return false;
            }
        });





        parent= getIntent().getExtras().getString("parent");
        if(getIntent().hasExtra("title")){
            setTitle(getIntent().getExtras().getString("title"));
        }
        ApiInterface service = Api.getRetrofitInstance().create(ApiInterface.class);

        final Call<List<JalsaMedia>> media = service.getMediaList(parent);

        media.enqueue(new Callback<List<JalsaMedia>>() {
            @Override
            public void onResponse(Call<List<JalsaMedia>> call, Response<List<JalsaMedia>> response) {


                RecyclerView recyclerView = rv;
                if (2 <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
                }
                adapter = new MymediaRecyclerViewAdapter(response.body(), vid);
                recyclerView.setAdapter(adapter);

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
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.upload, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {


if(menuItem.getItemId() == R.id.addflder) {
     DialogFragment df = new mydialog();
    df.show(getSupportFragmentManager(),"createfile");
}else {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
     actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setDisplayShowTitleEnabled(false);

    adapter.setSelect(true);
    adapter.notifyDataSetChanged();
    tb.setVisibility(View.VISIBLE);

}
                        return false;
                    }
                });

            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.media_bar, null);
        CheckBox cb = v.findViewById(R.id.selectall);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                adapter.selectAll(b);
            }
        });

        actionBar.setCustomView(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // MenuInflater inflater = getMenuInflater();
       // inflater.inflate(showmenu, menu);
        /*
        if (adapter.select == false)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }else {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(true);
        }
        */
        return super.onCreateOptionsMenu(menu);
    }

    public static class mydialog extends DialogFragment{

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.createfile_layout, null);

            builder.setMessage("Create folder")
                    .setView(v)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            TextView nametv = v.findViewById(R.id.username);
                            String name  = nametv.getText().toString();

                            ApiInterface service = Api.getRetrofitInstance().create(ApiInterface.class);

                            Call<List<JalsaMedia>> media = service.createfile(name, parent);
                            media.enqueue(new Callback<List<JalsaMedia>>() {
                                @Override
                                public void onResponse(Call<List<JalsaMedia>> call, Response<List<JalsaMedia>> response) {
                                    adapter.setValues(response.body());
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<List<JalsaMedia>> call, Throwable t) {

                                }
                            });


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }

    }

    @Override
    public void onListFragmentInteraction(JalsaMedia item) {
        if(item.getType() == 0) {

            Intent intent = new Intent(this, videos.class);
            intent.putExtra("parent", "" + item.getID());
            intent.putExtra("title", "" + item.getTitle());
            startActivity(intent);
        }else if(item.getType() == 1) {
            Intent intent = new Intent(this, Imageviewer.class);
            intent.putExtra("imageid", "" + item.getImageid());
            intent.putExtra("title", "" + item.getTitle());
            startActivity(intent);
        }

    }

}
