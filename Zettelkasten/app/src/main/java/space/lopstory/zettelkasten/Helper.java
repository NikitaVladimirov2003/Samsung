package space.lopstory.zettelkasten;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.telephony.CellSignalStrength;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

import space.lopstory.zettelkasten.NoteData.Note;

public class Helper {

    public  static Context context;

    public static void flush( String strHead, String strContent){
        String[] params  = new String[] { strHead,strContent};
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute( params );
    }


    private static class MyAsyncTask extends AsyncTask<String, Void, Void>{

        private static DatabaseReference myDataBase = FirebaseDatabase.getInstance().getReference().child(constant.User).child(constant.Email);
        private Integer GlobalNoteID;
        private static ArrayList<String> convert_tags(String strContent){
            ArrayList<String> tags = new ArrayList<>();
            String words[] = strContent.replace( '\n', ' ' ).split( " " );
            for(String s : words){
                if(s.charAt( 0 ) == '#'){
                    s = s.replace( '.',' ' );
                    s = s.replace( "#", "" );
                    s = s.toLowerCase();
                    tags.add( s );
                }
            }
            return tags;
        }


        @Override //TODO
        protected Void doInBackground(String... strings) {

            myDataBase.child( constant.GlobalNoteID ).get().addOnCompleteListener( new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful() && task.getResult().getValue(Integer.class) != null)
                        GlobalNoteID = task.getResult().getValue(Integer.class).intValue();
                    else {
                        myDataBase.child( constant.GlobalNoteID ).setValue( 1 );
                        GlobalNoteID = 1;
                    }
                    //Todo
                    String  strHead = strings[0],
                            strContent = strings[1];

                    ArrayList<String> tags = convert_tags( strContent );
                    Note newNote = new Note(GlobalNoteID, strHead,strContent );
                    myDataBase.child( constant.notes ).push().setValue( newNote );
                    for(String tag : tags) {
                        if (tag.equals( "" )) {
                            myDataBase.child( constant.tags ).child( "EmptyTag" ).child( constant.id ).push().setValue(GlobalNoteID );
                        } else {
                            myDataBase.child( constant.tags ).child( tag.trim() ).push().setValue(GlobalNoteID );
                        }
                    }
                    GlobalNoteID++;
                    myDataBase.child( constant.GlobalNoteID ).setValue( GlobalNoteID );
                }
            } );



            return null;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText( context,"Saving" , Toast.LENGTH_SHORT).show();
        }
        @Override
        protected void onPostExecute(Void aVoid) { }
    }

}
