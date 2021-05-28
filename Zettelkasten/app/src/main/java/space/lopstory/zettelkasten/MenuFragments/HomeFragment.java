package space.lopstory.zettelkasten.MenuFragments;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import space.lopstory.zettelkasten.Account.LoginActivity;
import space.lopstory.zettelkasten.CardAdapter;
import space.lopstory.zettelkasten.FragmentAddNote;
import space.lopstory.zettelkasten.NoteData.Note;
import space.lopstory.zettelkasten.R;
import space.lopstory.zettelkasten.constant;

public class HomeFragment extends Fragment {

    ArrayList<Note> notes;
    ViewPager cardViewPager;
    PagerAdapter cardAdapter;
    int colors[] = null;


    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate( R.layout.fragment_home, container, false );

        notes = new ArrayList<>();
        notes.add( new Note(001,"НОВЫЙ ЗАКОН","Грустные новости") );
        notes.add(new Note(2,"Что ждет рынок многоэтажного строительства в Башкирии","На днях в Уфе состоялась экспертная дискуссия на тему «Многоэтажное строительство в Башкирии», организованная газетой «КоммерсантЪ-Уфа». В конференц-зале ВДНХ – ЭКСПО собрались региональные чиновники от строительной сферы, риэлторы и застройщики. Собрались, чтобы обсудить проблемы и перспективы многоэтажного строительства в Уфе и Башкирии.\n" +
                "\n") );
        notes.add(new Note(222,"BY","Жила маленькая птичка в маленьком домике Да знаешь я не знаю даже как описать такую бстановку так что думай сам Вот такие дела не понимять такие элементарные вещи просто возмутимо с твоей сторона") );
        notes.add(new Note(2323,"Sorry","Фильм Моя няня") );
        notes.add(new Note(123414,"BY","Совсем давно была няная") );
        notes.add(new Note(123,"Самое известное Сообщение кого-то было опубликованно сегодня","аааааааааа") );


        FloatingActionButton fab_search = root.findViewById( R.id.fab_search ),
                             fab_guide  = root.findViewById( R.id.fab_guide ),
                             fab_quite  = root.findViewById( R.id.fab_quit ),
                             fab_add    = root.findViewById( R.id.fab_add );





        fab_guide.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().add( R.id.host_fragment, new GuideFragment() ).commit();
            }
        } );

        fab_quite.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getActivity(), LoginActivity.class ) );
            }
        } );

        fab_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().add( R.id.host_fragment, new FragmentAddNote() ).commit();
            }
        } );

        fab_search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().add( R.id.host_fragment, new SearchFragment() ).commit();

            }
        } );


        cardViewPager = root.findViewById( R.id.card_viewPager );
        cardAdapter = new CardAdapter(notes,root.getContext());

        cardViewPager.setAdapter( cardAdapter );
        cardViewPager.setPadding( 130, 0, 130, 0 );

        int[] colors = getResources().getIntArray( R.array.card_colors );

        cardViewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < cardAdapter.getCount() - 1 && position < colors.length -1 ){

                    cardViewPager.setBackgroundColor(
                            (Integer)argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position] ,
                                    colors[position+1])
                    );
                }else{
                       cardViewPager.setBackgroundColor( colors[colors.length - 1] );
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );
        return root;
    }
}