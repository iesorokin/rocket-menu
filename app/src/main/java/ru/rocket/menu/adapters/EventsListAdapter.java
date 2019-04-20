package ru.rocket.menu.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ru.rocket.menu.R;
import ru.rocket.menu.activities.EventActivity;
import ru.rocket.menu.activities.MainActivity;
import ru.rocket.menu.domain.Order;

import java.util.ArrayList;
import java.util.List;


public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.EventViewHolder> {


    private List<Order> mEventList;

    public EventsListAdapter(List<Order> eventList) {
        mEventList = eventList;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        MyEventListener mEventListener;
        TextView eventTitle;
        //        TextView eventDescribe;
//        TextView eventAuthor;
        ImageView eventImage;

        EventViewHolder(View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.itemEventTitle);
//            eventDescribe = itemView.findViewById(R.id.itemEventDescribe);
//            eventAuthor = itemView.findViewById(R.id.itemEventAuthor);
            mEventListener = new MyEventListener();
            eventImage = itemView.findViewById(R.id.itemEventImage);
            itemView.setOnClickListener(mEventListener);

        }

        class MyEventListener implements View.OnClickListener {

            String id;

            @Override
            public void onClick(View v) {

                Intent eventIntent = new Intent(v.getContext(), EventActivity.class);
                eventIntent.putExtra("ID", id);

                Context context = v.getContext();
                context.startActivity(eventIntent);

//toDO: added on Click
            }

        }

    }

    @NonNull
    @Override
    public EventsListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

        return new EventViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventsListAdapter.EventViewHolder holder, int position) {

        holder.eventTitle.setText(mEventList.get(position).getName());
//        holder.eventDescribe.setText(mEventList.get(position).getName());
//        holder.eventAuthor.setText(mEventList.get(position).getName());
        holder.mEventListener.id = mEventList.get(position).getName();
        holder.eventImage.setImageBitmap(getRandomBitmap(mEventList.get(position).getImage()));

    }

    private Bitmap getRandomBitmap(String image) {
        ArrayList<Drawable> drawables = new ArrayList<>();
        switch (image) {
            case "1":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_salat_seled));
            case "2":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_meat_assorti));
            case "3":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_blini_with_fish));
            case "4":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_xolodec));
            case "5":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_salat));
            case "6":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_6));
            case "7":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_7));
            case "8":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_8));
            case "9":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_9));
            case "10":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_10));
            case "11":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_11));
            case "12":
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_12));
            default:
                return drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_1));
        }
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);

    }

}