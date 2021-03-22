package com.example.notes.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.notes.R;
import com.example.notes.domain.domain.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final List<Note> items = new ArrayList<>();

    private OnNoteClicked noteClicked;

    public void addItems(List<Note> toAdd) {
        items.addAll(toAdd);
    }

    public void clear() {
        items.clear();
    }

    @NonNull
    @Override
    public NotesAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(root);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NoteViewHolder holder, int position) {
        Note item = items.get(position);

        holder.getTitle().setText(item.getName());

        Glide.with(holder.getImage())
                .load(item.getImageUrl())
                .into(holder.getImage());
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

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (noteClicked != null) {
                        noteClicked.onNoteClicked(items.get(getAdapterPosition()));
                    }
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
