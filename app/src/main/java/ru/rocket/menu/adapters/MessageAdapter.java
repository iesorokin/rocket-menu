package ru.rocket.menu.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import ru.rocket.menu.activities.MainActivity;
import ru.rocket.menu.domain.Message;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int TYPE_AUTHOR = 0;
    private static final int TYPE_GUEST = 1;
    List<Message> mMessagesList;

    public MessageAdapter(List<Message> messagesList) {

        mMessagesList = messagesList;
    }

    @Override
    public int getItemViewType(int position) {

        return isAuthor(position) ? TYPE_GUEST : TYPE_AUTHOR;

    }

    private boolean isAuthor(int position) {
        return mMessagesList.get(position).getFrom().equals("Холодный цех");

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
//        view = new Random().nextBoolean() ?
//                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_author, parent, false) :
//                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_guest, parent, false);
        switch (viewType) {
            case TYPE_AUTHOR: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_author, parent, false);
                break;
            }
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_guest, parent, false);
                break;
            }
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
//        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!" + mMessagesList.get(position).getMessage() + "    "
//                + mMessagesList.get(position).getFrom() + "!!!" + (mMessagesList.get(position).getDate() == null ? "NOO" : "NORM"));
        holder.messageText.setText(mMessagesList.get(position).getMessage());
        holder.messageAuthor.setText(mMessagesList.get(position).getFrom());
        holder.messageDate.setText(mMessagesList.get(position).getDate().toString());
//        User user = Commands.userByEmail(mMessagesList.get(position).getFrom());
        if (mMessagesList.get(position).getFrom().equals("Холодный цех")) {
            holder.messageImage.setImageBitmap(drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_salat)));
        }
        if (mMessagesList.get(position).getFrom().equals("Кандитерская")) {
            holder.messageImage.setImageBitmap(drawableToBitmap(MainActivity.getResourseForDraw().getDrawable(R.drawable.food_cake)));
        }
//        holder.messageImage.setImageBitmap(getBitmap(user.getImage()));
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView messageAuthor;
        TextView messageDate;
        ImageView messageImage;

        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.chat_message);
            messageAuthor = itemView.findViewById(R.id.chat_author);
            messageDate = itemView.findViewById(R.id.chat_date);
            messageImage = itemView.findViewById(R.id.chat_image);

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
        return mMessagesList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private Bitmap getBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);

    }

}
