package com.example.notes.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.domain.domain.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";

    private NotesViewModel notesViewModel;

    private NotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesViewModel =
                new ViewModelProvider(this).get(NotesViewModel.class);

        notesViewModel.fetchNotes();

        adapter = new NotesAdapter();
        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setAdapter(adapter);

        notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        //notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),  LinearLayoutManager.VERTICAL);
//        Drawable separator = ResourcesCompat.getDrawable(getResources(), R.drawable.separator, null);
//        itemDecoration.setDrawable(separator);
//        notesList.addItemDecoration(itemDecoration);

        notesViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        adapter.clear();
                        adapter.addItems(notes);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}