package space.lopstory.zettelkasten.NoteData;

import java.util.ArrayList;

public class Tag {
    public String tag;
    public ArrayList<Integer> id;

    public Tag(String tag, int id) {
        this.tag = tag.toLowerCase();
        this.id = new ArrayList<>();
        this.id.add( id );
        this.id.add( -1 );

    }
}
