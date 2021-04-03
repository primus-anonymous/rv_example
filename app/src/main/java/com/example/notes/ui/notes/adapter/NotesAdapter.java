package com.example.notes.ui.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.notes.R;
import com.example.notes.domain.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_NOTE = 0;
    public static final int ITEM_HEADER = 1;

    private final List<AdapterItem> items = new ArrayList<>();
    private final Fragment fragment;
    private OnNoteClicked noteClicked;
    private OnNoteLongClicked noteLongClicked;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setItems(List<AdapterItem> toSet) {

        DiffUtil.Callback callback = new DiffUtilCallBack(items, toSet);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        items.clear();
        items.addAll(toSet);

        result.dispatchUpdatesTo(this);
    }

    public void addItem(Note note) {
        // items.add(note);
    }

    public void removeAtPosition(int position) {
        // items.remove(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_NOTE) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

            return new NoteViewHolder(root);
        }

        if (viewType == ITEM_HEADER) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);

            return new HeaderViewHolder(root);
        }

        throw new IllegalStateException("It can't be happening");
    }

    @Override
    public int getItemViewType(int position) {
        AdapterItem item = items.get(position);

        if (item instanceof HeaderAdapterItem) {
            return ITEM_HEADER;
        }

        if (item instanceof NoteAdapterItem) {
            return ITEM_NOTE;
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NoteViewHolder) {

            NoteViewHolder noteViewHolder = (NoteViewHolder) holder;

            Note item = ((NoteAdapterItem) items.get(position)).getNote();

            noteViewHolder.getTitle().setText(item.getName());

            Glide.with(noteViewHolder.getImage())
                    .load(item.getImageUrl())
                    .into(noteViewHolder.getImage());
        }

        if (holder instanceof HeaderViewHolder) {
            String header = ((HeaderAdapterItem) items.get(position)).getHeader();

            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.getHeader().setText(header);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public OnNoteLongClicked getNoteLongClicked() {
        return noteLongClicked;
    }

    public void setNoteLongClicked(OnNoteLongClicked noteLongClicked) {
        this.noteLongClicked = noteLongClicked;
    }

    public Note getItemAtIndex(int contextMenuItemPosition) {
       return ((NoteAdapterItem)items.get(contextMenuItemPosition)).getNote();
    }

    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public interface OnNoteLongClicked {
        void onNoteLongClicked(View itemView, int position, Note note);
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView header;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            header = (TextView) itemView;
        }

        public TextView getHeader() {
            return header;
        }
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView image;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (noteClicked != null) {
                        noteClicked.onNoteClicked(((NoteAdapterItem) items.get(getAdapterPosition())).getNote());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (noteLongClicked != null) {
                        noteLongClicked.onNoteLongClicked(itemView, getAdapterPosition(), ((NoteAdapterItem) items.get(getAdapterPosition())).getNote());
                    }
                    return true;
                }
            });
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImage() {
            return image;
        }
    }
}
