package space.lopstory.zettelkasten.MenuFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import space.lopstory.zettelkasten.FragmentViewNote;
import space.lopstory.zettelkasten.MainActivity;
import space.lopstory.zettelkasten.NoteAdapter;
import space.lopstory.zettelkasten.NoteData.Note;
import space.lopstory.zettelkasten.R;
import space.lopstory.zettelkasten.constant;

public class SearchFragment extends Fragment {

    private DatabaseReference myDataBase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate( R.layout.fragment_search, container, false );
        MainActivity.nowFragment = "SEARCH";
        myDataBase = FirebaseDatabase.getInstance().getReference().child( constant.User ).child( constant.Email );
        SearchView searchView = root.findViewById( R.id.searchView );
        ListView listView = root.findViewById( R.id.listView );

        ArrayList<Note> ArrayListNotes = new ArrayList();



        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Integer> ArrayListId = new ArrayList();
                ArrayListNotes.clear();
                myDataBase.child( constant.tags ).child( newText ).addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {

                            if (snapshot.exists()) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    ArrayListId.add( child.getValue( Integer.class ) );
                                }
                            }
                            myDataBase.child( constant.notes ).addListenerForSingleValueEvent( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot child : snapshot.getChildren()) {
                                            for (int id : ArrayListId) {
                                                if (child.child( constant.id ).getValue( Integer.class ) == id) {
                                                    ArrayListNotes.add( new Note( child.child( constant.id ).getValue( Integer.class ),
                                                            child.child( constant.head ).getValue().toString(),
                                                            child.child( constant.content ).getValue().toString()
                                                    ) );
                                                    break;
                                                }
                                            }
                                        }
                                        NoteAdapter noteAdapter = new NoteAdapter( root.getContext(), R.layout.note_list_item, ArrayListNotes );
                                        listView.setAdapter( noteAdapter );
                                        noteAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e( "SearchDatabaseErorr", error.getMessage() );
                                }
                            } );


                        }catch (DatabaseException erorr){
                            Log.e("DatabaseConvertErorr" , "Failed to convert erorr: " + erorr.getMessage());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e( "SearchDatabaseErorr" ,error.getMessage());
                    }
                } );


                return false;
            }
        } );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < ArrayListNotes.size()) {

                    Fragment viewNote = new FragmentViewNote();
                    Bundle bundle = new Bundle();
                    bundle.putString( constant.content, ArrayListNotes.get( position ).getContent());
                    bundle.putString( constant.head, ArrayListNotes.get( position ).getHead());
                    viewNote.setArguments( bundle );
                    getParentFragmentManager().beginTransaction().add( R.id.host_fragment, viewNote ).commit();
                    //TODO
                    view.setVisibility( View.VISIBLE );

                }
            }
        } );


        return root;
    }
}