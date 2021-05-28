package space.lopstory.zettelkasten.MenuFragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import space.lopstory.zettelkasten.GuideAdapter;
import space.lopstory.zettelkasten.MainActivity;
import space.lopstory.zettelkasten.R;

public class GuideFragment extends Fragment {

    private ViewPager pager;
    private GuideAdapter adapter;
    private LinearLayout dotsLayout;
    private  TextView[] Dots;
    private View root;

    FloatingActionButton fab;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate( R.layout.fragment_guide, container, false );
        MainActivity.nowFragment = "GUIDE";
        dotsLayout = root.findViewById(R.id.dots_layout);
        pager = root.findViewById( R.id.guide_pager );

        adapter = new GuideAdapter(root.getContext());
        pager.setAdapter( adapter );
        addDotsIndicator( 0 );

        pager.addOnPageChangeListener( onPageChangeListener );
        return root;
    }

    public void addDotsIndicator(int position){
        Dots = new TextView[adapter.getCount()];
        dotsLayout.removeAllViews();
        for(int i = 0; i < Dots.length; i++){
            Dots[i] = new TextView( root.getContext() );
            Dots[i].setText( Html.fromHtml( "&#8226" ) );

            Dots[i].setTextSize( 35 );
            Dots[i].setTextColor( getResources().getColor( R.color.black ) );

            dotsLayout.addView( Dots[i] );
        }

        if(Dots.length > 0){
            Dots[position].setTextColor( getResources().getColor( R.color.design_default_color_primary ) );
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };



}