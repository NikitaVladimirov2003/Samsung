package space.lopstory.zettelkasten;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class GuideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private String [] headers, discriptions;
    private int images[];

    public GuideAdapter(Context context){
       this.context=context;
        headers = context.getResources().getStringArray( R.array.headers );
        discriptions = context.getResources().getStringArray( R.array.discriptions );
        images = new int[]{R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground};
    }

    @Override
    public int getCount() {
        return headers.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService( context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate( R.layout.page_item, container,false );
        TextView tvHeader = view.findViewById( R.id.textViewHeader );
        TextView tvDiscription = view.findViewById( R.id.textViewDiscription );
        ImageView imageViewGuide = view.findViewById( R.id.imageViewGuide );

        tvHeader.setText( headers[position] );
        tvDiscription.setText( discriptions[position]);
        //imageViewGuide.setImageResource( images[position] ); //TODO Учти, что будет сервер

        container.addView( view );
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (RelativeLayout)object);
    }

}
