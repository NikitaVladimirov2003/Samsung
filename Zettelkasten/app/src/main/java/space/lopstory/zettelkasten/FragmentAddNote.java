package space.lopstory.zettelkasten;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import space.lopstory.zettelkasten.NoteData.Note;
import space.lopstory.zettelkasten.NoteData.Tag;

public class FragmentAddNote extends Fragment {

    private EditText etContent, etHead;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_add_note, container, false );
        init(view);

        Helper.context = view.getContext();
        return view;
    }

    @Override
    public void onDestroyView() {
        String strContent = etContent.getText().toString().trim();
        String strHead = etHead.getText().toString().trim();
        strHead = strHead.replace( ".", "" );

        Helper.flush(strHead,strContent );

        super.onDestroyView();
    }



    private void init(View v){
        MainActivity.nowFragment = "AddNote";
        etContent = v.findViewById( R.id.editTextContent );
        etHead = v.findViewById( R.id.editTextNoteHead );
    }


}
