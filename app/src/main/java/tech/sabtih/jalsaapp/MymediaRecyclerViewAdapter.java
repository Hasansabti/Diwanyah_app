package tech.sabtih.jalsaapp;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tech.sabtih.jalsaapp.dummy.JalsaMedia;
import tech.sabtih.jalsaapp.mediaFragment.OnListFragmentInteractionListener;


import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link JalsaMedia} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymediaRecyclerViewAdapter extends RecyclerView.Adapter<MymediaRecyclerViewAdapter.ViewHolder> {
    public boolean select = false;
    public ArrayList<String> selected = new ArrayList<>();
    private  List<JalsaMedia> mValues;
    public boolean selectall = false;
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
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mListener.onListLongClick(holder.mItem);
                holder.cb.setChecked(true);
                return false;
            }
        });
        if(select == true){
            if(selectall){
                holder.cb.setChecked(true);
            }else{
                holder.cb.setChecked(false);
            }
            holder.cb.setVisibility(View.VISIBLE);
        }else{
            holder.cb.setVisibility(View.GONE);
        }

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    selected.add(""+holder.mItem.getID());
                }else{
                    selected.remove(""+holder.mItem.getID());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setSelect(boolean b) {
        select = b;
    }

    public void selectAll(boolean allselect) {
        selectall = allselect;
         notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView mContentView;
        public final ImageView image;
        public JalsaMedia mItem;
        public CheckBox cb;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mContentView = (TextView) view.findViewById(R.id.content);
            image = view.findViewById(R.id.myimage);
            cb = view.findViewById(R.id.checkBox);

        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
