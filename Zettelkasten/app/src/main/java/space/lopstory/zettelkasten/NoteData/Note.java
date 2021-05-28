package space.lopstory.zettelkasten.NoteData;

public class Note {

    Integer id;
    private String head,
                   content;


    public Note(Integer id, String head, String content){
        this.id = id;
        this.head = head;
        this.content = content;
    }



    public String getHead() {
        return head;
    }
    public String getContent() {
        return content;
    }
    public Integer getId() {
        return id;
    }
}
