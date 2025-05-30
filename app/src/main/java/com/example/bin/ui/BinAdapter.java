package com.example.bin.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bin.R;
import com.example.bin.data.Labels;
import com.example.bin.data.SavedItem;

import java.util.List;

class BinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<SavedItem> items;
    private final OnListChangedListener listener;

    public BinAdapter(List<SavedItem> items, OnListChangedListener listener) {
        this.items = items;
        this.listener = listener;
    }

    /*
     * В качестве упрощения обновляем список полностью.
     * В идеале нужно обновлять только те элементы, которые изменились при промощи
     * notifyItemChanged/notifyItemRangeChanged или DiffUtil
     * FIXME Добавить notifyItemChanged
     * */
    @SuppressLint("NotifyDataSetChanged")
    void dataSetChange(List<SavedItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_STANDARD) {
            return new BinViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bin_list_item, parent, false));
        } else {
            return new BinAdvViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bin_adv_list_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_STANDARD) {
            ((BinViewHolder) holder).bind(items.get(position));
        } else {
            ((BinAdvViewHolder) holder).bind(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_ADV : TYPE_STANDARD;
    }

    void onItemDismiss(int position) {
        listener.onDismissItem(items.get(position));
    }

    public class BinViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout flagsContainer;
        private final TextView itemDescription;
        private final ImageView itemImage;
        private final TextView newPrice;
        private final TextView oldPrice;
        private final ConstraintLayout oldPriceLayout;
        private final TextView itemCount;
        private final ImageView minusButton;
        private final ImageView plusButton;

        public BinViewHolder(View itemView) {
            super(itemView);
            flagsContainer = itemView.findViewById(R.id.flags_layout);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemImage = itemView.findViewById(R.id.item_image);
            newPrice = itemView.findViewById(R.id.new_price);
            oldPrice = itemView.findViewById(R.id.old_price);
            oldPriceLayout = itemView.findViewById(R.id.old_price_layout);
            itemCount = itemView.findViewById(R.id.item_count);
            minusButton = itemView.findViewById(R.id.minus_button);
            plusButton = itemView.findViewById(R.id.plus_button);
        }

        void bind(SavedItem item) {
            //FIXME лейблы не добавляем, а просто хордкодим один
            setFlags(item);
            itemDescription.setText(item.getDescription());
            newPrice.setText(item.getPrice() + " ₽");
            //FIXME скидку всегда хардкодим
            setOldPrice(item);
            itemCount.setText(item.getCount() + " шт.");
            loadImage(item);
            plusButton.setOnClickListener(v -> listener.onAddItem(item));
            minusButton.setOnClickListener(v -> listener.onRemoveItem(item));
        }

        private void loadImage(SavedItem item) {
            //FIXME Загрузка заглушки из интернета
            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_load_error)
                    .into(itemImage);
        }

        private void setOldPrice(SavedItem item) {
            if (item.getPriceOld() == 0) {
                oldPriceLayout.setVisibility(View.GONE);
            } else {
                oldPrice.setText(String.valueOf(item.getPriceOld())+ " ₽");
                oldPriceLayout.setVisibility(View.VISIBLE);
            }
        }

        private void setFlags(SavedItem item) {
            //FIXME лейблы добавляются при каждом изменении списка
            //Костыль listOfFlags.size < 3
            List<Labels> listOfFlags = item.getLabels();
            if (!listOfFlags.isEmpty() && listOfFlags.size() < 4) {
                flagsContainer.removeAllViews();
                LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                for (Labels flag : listOfFlags) {
                    switch (flag) {
                        case ADD:
                            flagsContainer.addView(inflater.inflate(R.layout.flag_add_text_view, flagsContainer, false));
                            break;
                        case BESTSELLER:
                            flagsContainer.addView(inflater.inflate(R.layout.flag_bestseller_text_view, flagsContainer, false));
                            break;
                        case NEW:
                            flagsContainer.addView(inflater.inflate(R.layout.flag_new_text_view, flagsContainer, false));
                            break;
                        case SALE:
                            flagsContainer.addView(inflater.inflate(R.layout.flag_sale_text_view, flagsContainer, false));
                            break;
                        case TIME:
                            flagsContainer.addView(inflater.inflate(R.layout.flag_time_text_view, flagsContainer, false));
                            break;
                    }
                }
                flagsContainer.setVisibility(View.VISIBLE);
            } else {
                flagsContainer.setVisibility(View.GONE);
            }
        }
    }

    class BinAdvViewHolder extends RecyclerView.ViewHolder {

        private final TextView itemDescription;
        private final ImageView itemImage;
        private final TextView newPrice;
        private final TextView oldPrice;
        private final TextView itemCount;
        private final ImageView minusButton;
        private final ImageView plusButton;
        private final TextView spinnerText;

        BinAdvViewHolder(View itemView) {
            super(itemView);
            itemDescription = itemView.findViewById(R.id.item_description_adv);
            itemImage = itemView.findViewById(R.id.item_image_adv);
            newPrice = itemView.findViewById(R.id.new_price_adv);
            oldPrice = itemView.findViewById(R.id.old_price_adv);
            itemCount = itemView.findViewById(R.id.item_count_adv);
            minusButton = itemView.findViewById(R.id.minus_button_adv);
            plusButton = itemView.findViewById(R.id.plus_button_adv);
            spinnerText = itemView.findViewById(R.id.spinner_text_adv);
        }

        @SuppressLint("SetTextI18n")
        void bind(SavedItem item) {
            itemDescription.setText(item.getDescription());
            newPrice.setText(item.getPrice() + " ₽");
            oldPrice.setText(R.string.old_price_stub + " ₽");
            oldPrice.setVisibility(View.VISIBLE);
            itemCount.setText(item.getCount() + " шт.");
            loadImage(item);
            plusButton.setOnClickListener(v -> listener.onAddItem(item));
            minusButton.setOnClickListener(v -> listener.onRemoveItem(item));
            spinnerText.setText(item.getDescription());
        }

        void loadImage(SavedItem item) {
            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_load_error)
                    .into(itemImage);
        }
    }

    interface OnListChangedListener {

        void onAddItem(SavedItem item);

        void onRemoveItem(SavedItem item);

        void onDismissItem(SavedItem item);
    }

    private static final int TYPE_STANDARD = 0;
    private static final int TYPE_ADV = 1;
}