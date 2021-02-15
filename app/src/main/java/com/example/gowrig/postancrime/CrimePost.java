import org.apache.http.HttpEntity; import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient; import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient; import org.apache.http.util.EntityUtils;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.view.View;
import android.view.View.OnClickListener; import android.widget.Button;
import android.widget.EditText; import android.widget.Toast;
public class CrimePost extends Activity {

    /** Called when the activity is first created. */ EditText CrimeName_txt,CrimeType_txt,Location_txt; Button Post,Take_pic;
    private HttpResponse response; private HttpClient httpclient; private String responseText = "";
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.crimepost);

        CrimeName_txt=(EditText)findViewById(R.id.editText1); CrimeType_txt=(EditText)findViewById(R.id.editText2); Location_txt=(EditText)findViewById(R.id.editText3);
        Take_pic=(Button)findViewById(R.id.button_pic); Post=(Button)findViewById(R.id.button1);
        Take_pic.setOnClickListener(new OnClickListener() { @Override
        public void onClick(View v) {

            String cname=CrimeName_txt.getText().toString().trim(); if(!cname.equals("")) { ServerIPAddress.setCrimeName(cname);
                Intent i = new Intent(CrimePost.this, PreviewActivity.class); startActivity(i);
            }else{

                Toast.makeText(CrimePost.this, "Enter Crime Name", Toast.LENGTH_SHORT).show();
            }

        }

        });

        Post.setOnClickListener(new OnClickListener() {



            @Override

            public void onClick(View v) {

// TODO Auto-generated method stub

                String cname=CrimeName_txt.getText().toString().trim(); String ctype=CrimeType_txt.getText().toString().trim(); String location=Location_txt.getText().toString().trim(); String aadhar=ServerIPAddress.getHotelname().toString(); String name=ServerIPAddress.getUname().toString();
                String details="POST$"+cname+"$"+ctype+"$"+location+"$"+name+"$"+aadhar;
                String result=getServerConnection(details); if(!result.equals("")&&result.equals("POSTED")){ Toast.makeText(getApplicationContext(), "Posted", 10).show();
                }

                else{

                    Toast.makeText(getApplicationContext(), "Not Posted", 10).show();

                }

            }

        });

    }

    private String getServerConnection(String data){ try {
        httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet("http://"+ServerIPAddress.getIpaddress()+":5555/"+data);
        response = httpclient.execute(httpget); HttpEntity entity = response.getEntity(); responseText = EntityUtils.toString(entity);
    }catch (Exception e){ Toast.makeText(this, "error"+e.toString(),
            Toast.LENGTH_LONG).show();

    }

        return responseText.trim();

    }

}


