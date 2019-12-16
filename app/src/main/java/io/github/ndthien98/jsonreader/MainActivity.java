package io.github.ndthien98.jsonreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String link = "https://jsonplaceholder.typicode.com/users";
    String data = "";
    ArrayList<User> userData;
    UserAdapter userAdapter;
    ListView listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userData = new ArrayList<>();
        GetData getData = new GetData();
        getData.execute();

        userAdapter = new UserAdapter();
        userAdapter.setContext(this);
        listUser = findViewById(R.id.list_user);
        listUser.setAdapter(userAdapter);


    }

    class GetData extends AsyncTask<Void, Void, Boolean> {
        final String TAG = "GETDATA";
        URL url;

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int resCOde = connection.getResponseCode();
                Log.d("TAG", "doInBackground: " + resCOde);

                BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = buffer.readLine()) != null) {
                    builder.append(line);
                }
                data = builder.toString();
                buffer.close();

                return true;
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: " + e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Log.d(TAG, "doInBackground: postExcute:" + data);
            try {
                Gson gson = new Gson();
                userData = gson.fromJson(data, new TypeToken<ArrayList<User>>() {
                }.getType());
                userAdapter.notifyDataSetChanged();

                userAdapter.setData(userData);
                userAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.d(TAG, "onPostExecute: " + e.getMessage());
            }
        }
    }
}
