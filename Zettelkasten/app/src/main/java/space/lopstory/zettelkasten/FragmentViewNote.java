package space.lopstory.zettelkasten;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class FragmentViewNote extends Fragment {
    TextView noteHead, noteContent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_view, container, false );
        MainActivity.nowFragment = "VIEW";
        String head, content;
           head = getArguments().getString( constant.head );
           content = getArguments().getString( constant.content );
           noteHead = root.findViewById( R.id.text_view_nh );
         noteContent = root.findViewById( R.id.text_view_nc);
        noteHead.setText(head);
           noteContent.setText(content);

        return root;
    }
}
