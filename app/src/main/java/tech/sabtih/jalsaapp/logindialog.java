package tech.sabtih.jalsaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.sabtih.jalsaapp.dummy.JalsaMedia;
import tech.sabtih.jalsaapp.dummy.user;
import tech.sabtih.jalsaapp.net.Api;
import tech.sabtih.jalsaapp.net.ApiInterface;

public class logindialog extends DialogFragment {

Spinner spinner;
     ArrayList<Integer> ids;
     dialoglistener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        listener = (MainActivity)getActivity();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.login_layout, null);
        spinner = v.findViewById(R.id.spinner2);

        ApiInterface service = Api.getRetrofitInstance().create(ApiInterface.class);

        final Call<List<user>> users = service.getUSers();
        users.enqueue(new Callback<List<user>>() {
            @Override
            public void onResponse(Call<List<user>> call, Response<List<user>> response) {
                ArrayList<String> names = new ArrayList<>();
                ids = new ArrayList<>();
                for(user user : response.body()){
                    names.add(user.getName());
                    ids.add(user.getID());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, names);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<user>> call, Throwable t) {

            }
        });


        builder.setMessage("Login")
                .setView(v)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int userid =  ids.get( spinner.getSelectedItemPosition());
                        SharedPreferences sharedpreferences = getActivity(). getSharedPreferences("jalsa", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("userid", userid);
                        editor.commit();
                        listener.onLogin(userid);


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

    public interface dialoglistener{
        void onLogin(int userid);

    }
}
