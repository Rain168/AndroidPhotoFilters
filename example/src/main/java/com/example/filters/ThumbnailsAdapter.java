package com.example.filters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;

import java.util.List;

/**
 * Created by Varun on 01/07/15.
 */
public class ThumbnailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ThumbnailCallback thumbnailCallback;
    private static final String TAG = "THUMBNAILS_ADAPTER";
    private List<ThumbnailItem> dataSet;
    private Context context;

    private static int lastPosition = -1;

    public ThumbnailsAdapter(List<ThumbnailItem> dataSet,Context context, ThumbnailCallback  thumbnailCallback){
        Log.v(TAG, "Thumbnails Adapter has "+dataSet.size()+" items");
        this.dataSet = dataSet;
        this.context = context;
        this.thumbnailCallback = thumbnailCallback;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v(TAG, "On Create View Holder Called");
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.list_thumbnail_item, viewGroup, false);
        return new ThumbnailsViewHolder(itemView);
    }

    public static class ThumbnailsViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;

        public ThumbnailsViewHolder(View v) {
            super(v);
            this.thumbnail = (ImageView)v.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {
        final ThumbnailItem thumbnailItem = dataSet.get(i);
        Log.v(TAG, "On Bind View Called");
        ThumbnailsViewHolder thumbnailsViewHolder = (ThumbnailsViewHolder)holder;
        thumbnailsViewHolder.thumbnail.setImageBitmap(thumbnailItem.image);
        thumbnailsViewHolder.thumbnail.setScaleType(ImageView.ScaleType.FIT_START);
        setAnimation(thumbnailsViewHolder.thumbnail, i);
        thumbnailsViewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPosition != i) {
                    thumbnailCallback.onThumbnailClick(thumbnailItem.filter);
                    lastPosition = i;
                    }
                }

        });
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        {
            ViewHelper.setAlpha(viewToAnimate, .0f);
            com.nineoldandroids.view.ViewPropertyAnimator.animate(viewToAnimate).alpha(1).setDuration(250).start();
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
