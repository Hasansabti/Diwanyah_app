package tech.sabtih.jalsaapp;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tech.sabtih.jalsaapp.dummy.JalsaMedia;
import tech.sabtih.jalsaapp.mediaFragment.OnListFragmentInteractionListener;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link JalsaMedia} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymediaRecyclerViewAdapter extends RecyclerView.Adapter<MymediaRecyclerViewAdapter.ViewHolder> {

    private  List<JalsaMedia> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MymediaRecyclerViewAdapter(List<JalsaMedia> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    public void setValues(List<JalsaMedia> values){
        this.mValues = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
       // holder.mIdView.setText(""+mValues.get(position).getID());
        holder.mContentView.setText(mValues.get(position).getTitle());
        if(mValues.get(position).getType() == 0){
            Picasso.get().load(R.drawable.folder).resize(200, 200).centerCrop() .into(holder.image);
           /// Bitmap fldr = BitmapFactory.
           /// holder.image.setImageResource(R.drawable.folder);
        }else{
            Picasso.get().load("https://drive.google.com/thumbnail?authuser=0&sz=w300&id="+mValues.get(position).getImageid()).resize(200, 200).centerCrop().noFade() .into(holder.image);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an itemnop has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mContentView;
        public final ImageView image;
        public JalsaMedia mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mContentView = (TextView) view.findViewById(R.id.content);
            image = view.findViewById(R.id.myimage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
