package com.tinkoff.androidcourse;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.ViewHolder> implements ItemTouchAdapter {


    static class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        ImageView photo;
        TextView name;
        TextView information;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            photo = itemView.findViewById(R.id.worker_photo);
            name = itemView.findViewById(R.id.worker_name);
            information = itemView.findViewById(R.id.worker_info);
        }
    }

    static class ItemTouchCallback extends ItemTouchHelper.Callback {
        private ItemTouchAdapter listener;

        public ItemTouchCallback(ItemTouchAdapter listener) {
            this.listener = listener;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder vh, @NonNull RecyclerView.ViewHolder target) {
            listener.onItemMoved(vh.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int dir) {
            listener.onItemDismiss(viewHolder.getAdapterPosition());
        }

    }

    @NonNull
    private List<Worker> listItems;
    @NonNull
    private Context context;

    WorkerAdapter(@NonNull List<Worker> items, @NonNull Context context) {
        listItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int pos) {
        Worker worker = listItems.get(pos);
        switch (worker.getGender()) {
            case MALE:
                vh.root.setBackgroundColor(context.getResources().getColor(R.color.male_item_background));
                vh.photo.setImageResource(R.drawable.ic_male_blue);
                break;
            case FEMALE:
                vh.root.setBackgroundColor(context.getResources().getColor(R.color.female_item_background));
                vh.photo.setImageResource(R.drawable.ic_female_red);
                break;
            case OTHER_NOT_SPECIFIED:
                vh.root.setBackgroundColor(context.getResources().getColor(R.color.common_item_background));
                vh.photo.setImageResource(R.drawable.ic_male_black);
                break;
        }
        vh.name.setText(worker.getName());
        vh.information.setText(context.getString(R.string.worker_information_pattern, worker.getAge(), worker.getPosition()));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    Object storeState() {
        return listItems;
    }

    void restoreState(List<Worker> state) {
        listItems = state;
        notifyDataSetChanged();
    }

    void addWorker(Worker worker) {
        listItems.add(worker);
        notifyItemInserted(listItems.size() - 1);
    }

    @Override
    public void onItemDismiss(int position) {
        listItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMoved(int index, int target) {
        Worker item = listItems.remove(index);
        listItems.add(target, item);
        notifyItemMoved(index, target);
    }
}
