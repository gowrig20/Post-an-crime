package com.example.gowrig.postancrime; import java.util.ArrayList; import java.util.StringTokenizer;
import org.apache.http.HttpEntity; import org.apache.http.HttpResponse; import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet; import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.util.EntityUtils; import android.app.Activity;
import android.app.AlertDialog; import android.app.ListActivity;
import android.content.DialogInterface; import android.content.Intent;
import android.os.Bundle; import android.util.Log; import android.view.View;
import android.view.View.OnClickListener; import  android.widget.AdapterView; import android.widget.ArrayAdapter; import android.widget.Button;
import android.widget.EditText; import android.widget.LinearLayout; import android.widget.ListView; import android.widget.TextView; import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener; public class LocationCrimeView extends ListActivity {
    /** Called when the activity is first created. */

    static String text = ""; String message = "";
    private HttpResponse response; private HttpClient httpclient; private String responseText = ""; String cname,ctype,clocation;
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);



        String value=ServerIPAddress.getGpsValues().toString(); String reply = getServerConnection("LOCATION$"+value);
// String reply="1";

        if(reply!=null && reply.intern()!="NODATA"){ message = reply;
// Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(getApplicationContext(), "No Data found", Toast.LENGTH_LONG).show();
        }

//Toast.makeText(getApplicationContext(), "MEssage.	"+message,

        Toast.LENGTH_LONG).show();

        StringTokenizer st = new StringTokenizer(message,"#");

//st.nextToken();

        ArrayList<String> al = new ArrayList<String>(); al.add("Title :");
        while(st.hasMoreTokens()){

            String data = (String)st.nextToken(); data = data.replace("$", "\n"); al.add(data);
        }

        al.add("Back");

        Log.v("Search output",al.toString());

        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, al));



        ListView lv = getListView(); lv.setTextFilterEnabled(true);


        lv.setOnItemClickListener(new OnItemClickListener() { public void onItemClick(AdapterView<?> parent, View view,
                                                                                      int position, long id) {

            String selectedText = ((TextView) view).getText().toString();

//ServerIPAddress.setSelectedvalue(selectedText); if(selectedText.intern() == "Back"){
            Intent intent = new Intent(LocationCrimeView.this,MenuActivity.class); startActivity(intent);
        }else{

            text = selectedText; Toast.makeText(getApplicationContext(), text, 10).show(); text = text.replace("\n", ":");
            StringTokenizer stt=new StringTokenizer(text,":"); stt.nextToken();
            cname=stt.nextToken(); stt.nextToken(); ctype=stt.nextToken(); stt.nextToken(); clocation=stt.nextToken();
            Toast.makeText(getApplicationContext(), cname+ctype+clocation, 10).show();
            ServerIPAddress.setCrimeName(cname);

            Intent i=new Intent(LocationCrimeView.this,ShowImage.class); startActivity(i);
//AlertBox("Accept");

// ServerIPAddress.setMeaning(text);

//Intent i1=new Intent(KuralTitle.this,KuralMeaning.class);

//startActivity(i1);

        }

        }

    });

}

    private String getServerConnection(String data){ try {
        httpclient = new DefaultHttpClient(); HttpGet httpget = new
                HttpGet("http://"+ServerIPAddress.getIpaddress()+":5555/"+data); response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity(); responseText = EntityUtils.toString(entity);


    }catch (Exception e){ Toast.makeText(this, "error"+e.toString(),
            Toast.LENGTH_LONG).show();



    }

        return responseText.trim();

    }

    private void AlertBox(String data){ AlertDialog.Builder alertDialog = new
            AlertDialog.Builder(LocationCrimeView.this);

// Setting Dialog Title alertDialog.setTitle("Crime");


// Setting Dialog Message alertDialog.setMessage(data);


// Setting Icon to Dialog alertDialog.setIcon(R.drawable.police);


// Setting Positive "Yes" Button alertDialog.setPositiveButton("Ok", new
        DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which) {

                String result=getServerConnection("COUNT$"+cname+"$"+ctype+"$"+clocation);
                if(!result.equals("")&&result.equals("POSTED")){ Toast.makeText(getApplicationContext(), "Updated", 10).show();
                }

                else{

                    Toast.makeText(getApplicationContext(), "Not Update", 10).show();

                }

                finish();

//oast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
            }

        });



// Setting Negative "NO" Button




// Showing Alert Message alertDialog.show();
    }

}

