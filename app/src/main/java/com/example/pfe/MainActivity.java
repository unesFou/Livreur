package com.example.pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String sequence,lal,lon,prix;
    //TextView txt;
   // Button btn_maps;

    private static String JSON_URL = "http://10.0.2.2:8089/commandes";
    ArrayList<HashMap<String,String>> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friendsList = new ArrayList<>();
        listView = findViewById(R.id.listView_id);

        GetData getData = new GetData();
        getData.execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView sequence = (TextView) view.findViewById(R.id.textView);
                TextView txtlal = (TextView) view.findViewById(R.id.textView2);
                TextView txtlon = (TextView) view.findViewById(R.id.textView3);

                //TextView prix = (TextView) view.findViewById(R.id.textView5);


                String sequences = sequence.getText().toString();
                String clients = "";
                float txtlals = Float.parseFloat(txtlal.getText().toString());
                float txtlons = Float.parseFloat(txtlon.getText().toString());



                Commande cmd = new Commande(clients,sequences,prix,txtlals,txtlons);

               // int j = parent.getPositionForView(view);

                Intent i = new Intent(MainActivity.this,MapsActivity.class);
                i.putExtra("cmd",cmd);


                startActivity(i);
            }
        });
        /*
         */
    }


    public class GetData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String current="";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    //Open Connection
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    //Input Stream
                    InputStream in = urlConnection.getInputStream();
                    //Input StreamReader
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for (int i = 0 ; i<jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    sequence = jsonObject1.getString("sequence");
                    lal = jsonObject1.getString("lal");
                    lon = jsonObject1.getString("lon");
                    prix = jsonObject1.getString("prix");

                    //HashMap
                    HashMap<String,String> friends = new HashMap<>();
                    friends.put("sequence",((i+1)+" - "+sequence));
                    friends.put("lal",lal);
                    friends.put("lon",lon);
                    friends.put("prix","Total : "+prix+" DH");
                    friendsList.add(friends);

                    //Displaying the result
                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this,
                            friendsList,
                            R.layout.row_layout,
                            new String[]{"sequence","lal","lon","prix"},
                            new int[]{R.id.textView, R.id.textView2,R.id.textView3,R.id.textView5}
                    );
                    listView.setAdapter(adapter);

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}