package codelen.alisherka.takingattandance;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    Button button, findButton;
    EditText editText;
    MongoCollection<Document> mongoCollection;
    TextView textView;

    String AppId = "application-0-zzrvt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.textEdit);
        findButton = (Button)findViewById(R.id.findButton);
        textView = (TextView)findViewById(R.id.resultFindText);

        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(AppId).build());
        Credentials credentials = Credentials.emailPassword("kumush@gmail.com", "12345678");

        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    Log.v("User","Login success");
                    User user = app.currentUser();
                    mongoClient = user.getMongoClient("mongodb-atlas");
                    mongoDatabase = mongoClient.getDatabase("attendance");
                    mongoCollection = mongoDatabase.getCollection("students");
//                    mongoCollection.insertOne(new Document("userId", user.getId()).append("data", editText.getText().toString())).getAsync(result1 -> {
//                        if(result.isSuccess()){
//                            Log.v("Data","Data successful inserted");
//                        }else{
//                            Log.v("Data", "Failed");
//                        }
//                    });

                }else{
                    Log.v("User", "Login Error");
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Document document = new Document().append("data","Find is working").append("myid", "1234");
                mongoCollection.insertOne(document).getAsync(result -> {
                    if(result.isSuccess()){
                        Toast.makeText(getApplicationContext(),"Inserted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Document queryFilter = new Document().append("myid", "1234");
                RealmResultTask<Document> findTask;
            }
        });
//
//        app.getEmailPassword().registerUserAsync("kumush@gmail.com","12345678", it->{
//            if(it.isSuccess()){
//                Log.v("User","Register success");
//            }else{
//                Log.v("User","Fail Register");
//            }
//        });
    }
}