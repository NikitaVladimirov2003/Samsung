package space.lopstory.zettelkasten;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import space.lopstory.zettelkasten.NoteData.Note;
import space.lopstory.zettelkasten.R;

public class NoteAdapter extends ArrayAdapter<Note> {

    private LayoutInflater inflater;
    private int layout;
    private List<Note> notes;

    public NoteAdapter(Context context, int resource, ArrayList<Note> notes) {
        super(context, resource, notes);
        this.notes = notes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);
        TextView Note_name =  view.findViewById( R.id.note_name);
        Note note = notes.get( position );
        Note_name.setText(note.getHead());

        return view;
    }

    @Override
    public void remove(@Nullable Note object) {
        super.remove( object );
        notifyDataSetChanged();
    }
}