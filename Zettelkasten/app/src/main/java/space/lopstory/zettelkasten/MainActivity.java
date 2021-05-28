package space.lopstory.zettelkasten;

import android.animation.ArgbEvaluator;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import space.lopstory.zettelkasten.Account.LoginActivity;
import space.lopstory.zettelkasten.MenuFragments.GuideFragment;
import space.lopstory.zettelkasten.MenuFragments.HomeFragment;
import space.lopstory.zettelkasten.MenuFragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    static public String nowFragment = "HOME";
    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser == null){
            startActivity( new Intent(MainActivity.this, LoginActivity.class ) );
        }

        constant.Email = auth.getCurrentUser().getEmail().split( "@" )[0];

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


    }


    public void setNowFragment(String nowFragment) {
        this.nowFragment = nowFragment;
    }



    @Override
    public void onBackPressed() {
        if(nowFragment != "HOME") {
            super.onBackPressed();
            if(nowFragment == "VIEW"){
                nowFragment = "SEARCH";
            }else {
                nowFragment = "HOME";
            }
        }
    }
}
