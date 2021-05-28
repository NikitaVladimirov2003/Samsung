package space.lopstory.zettelkasten;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

import space.lopstory.zettelkasten.NoteData.Note;

public class CardAdapter extends PagerAdapter {

    private ArrayList<Note> notes;
    private LayoutInflater layoutInflater;
    private Context context;

    public CardAdapter(ArrayList<Note> notes, Context context){
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals( object ) ;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from( context );
        View view = layoutInflater.inflate( R.layout.card_item, container, false );

        TextView noteName, noteContent;
        View background;

        noteName = view.findViewById( R.id.tv_card_note_name );
        noteContent = view.findViewById( R.id.tv_card_note_content );
        background = view.findViewById( R.id.v_card );

        if(notes.get( position ).getHead().length() > 20)
        noteName.setText( notes.get( position ).getHead().subSequence( 0,20 )+"..." );
        else noteName.setText( notes.get( position ).getHead());

        if(notes.get( position ).getContent().length() > 40)
        noteContent.setText( notes.get( position ).getContent().subSequence( 0,40 ) + "...");
        else noteContent.setText( notes.get( position ).getContent());
        int colors[] = context.getResources().getIntArray( R.array.card_colors );
        background.setBackgroundColor(colors[position]);

        container.addView( view,0 );
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (View)object );
    }
}
