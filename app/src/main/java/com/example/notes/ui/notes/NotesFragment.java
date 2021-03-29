package com.example.notes.ui.notes;

import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.domain.domain.Note;
import com.example.notes.ui.notes.adapter.AdapterItem;
import com.example.notes.ui.notes.adapter.NotesAdapter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    public static final int SPAN_COUNT = 2;
    private static final int MY_DEFAULT_DURATION = 10000;
    private NotesViewModel notesViewModel;

    private NotesAdapter adapter;
    private int contextMenuItemPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesViewModel =
                new ViewModelProvider(this, new NotesViewModelFactory()).get(NotesViewModel.class);

        notesViewModel.fetchNotes();

        adapter = new NotesAdapter(this);
        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setNoteLongClicked(new NotesAdapter.OnNoteLongClicked() {
            @Override
            public void onNoteLongClicked(View itemView, int position, Note note) {

                contextMenuItemPosition = position;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    itemView.showContextMenu(10, 10);
                } else {
                    itemView.showContextMenu();
                }
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

//        DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(MY_DEFAULT_DURATION);
//        animator.setRemoveDuration(MY_DEFAULT_DURATION);
//        notesList.setItemAnimator(animator);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), SPAN_COUNT);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == NotesAdapter.ITEM_HEADER) {
                    return SPAN_COUNT;
                }
                return 1;
            }
        });

        notesList.setLayoutManager(gridLayoutManager);

        ProgressBar progressBar = view.findViewById(R.id.progress);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.action_add_new) {
                    notesViewModel.addNewNote();
                } else if (item.getItemId() == R.id.action_clear) {
                    notesViewModel.clearNotes();
                }

                return true;
            }
        });

        //notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),  LinearLayoutManager.VERTICAL);
//        Drawable separator = ResourcesCompat.getDrawable(getResources(), R.drawable.separator, null);
//        itemDecoration.setDrawable(separator);
//        notesList.addItemDecoration(itemDecoration);

        notesViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), new Observer<List<AdapterItem>>() {
                    @Override
                    public void onChanged(List<AdapterItem> notes) {
                        adapter.setItems(notes);
                    }
                });

        notesViewModel.getProgressLiveData()
                .observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isVisible) {
                        if (isVisible) {
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

        notesViewModel.getSelectedDateLiveData()
                .observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String message) {
                        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            notesViewModel.deleteAtPosition(adapter.getItemAtIndex(contextMenuItemPosition));
            return true;
        }
        if (item.getItemId() == R.id.action_show_picker) {

            MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker().build();

            picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                @Override
                public void onPositiveButtonClick(Long selection) {
                    notesViewModel.dateSelected(selection);
                }
            });

            picker.show(getChildFragmentManager(), "MaterialDatePicker");

            return true;
        }

        return super.onContextItemSelected(item);
    }
}