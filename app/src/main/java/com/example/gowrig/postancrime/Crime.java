package com.example.gowrig.postancrime;

import java.util.StringTokenizer; import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse; import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet; import org.apache.http.impl.client.DefaultHttpClient; import org.apache.http.util.EntityUtils;
import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.os.StrictMode; import android.view.View;
import android.view.View.OnClickListener; import android.widget.Button;
import android.widget.EditText; import android.widget.Toast;

public class Crime extends Activity {

    /** Called when the activity is first created. */ EditText uname_txt,pass_txt,Ip_txt,admin_txt; Button Login,Register;
    private HttpResponse response; private HttpClient httpclient; private String responseText = "";
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.login);
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); uname_txt=(EditText)findViewById(R.id.editText1); pass_txt=(EditText)findViewById(R.id.editText2); Ip_txt=(EditText)findViewById(R.id.editText3); Login=(Button)findViewById(R.id.button1); Register=(Button)findViewById(R.id.button2);
            admin_txt=(EditText)findViewById(R.id.editText_adminno); Login.setOnClickListener(new OnClickListener() {

                @Override

                public void onClick(View v) {

// TODO Auto-generated method stub

                    String uname=uname_txt.getText().toString().trim(); String pass=pass_txt.getText().toString().trim();
                    String adminNO=admin_txt.getText().toString().trim(); ServerIPAddress.setIpaddress(Ip_txt.getText().toString()); ServerIPAddress.setAdminContactno(adminNO);
                    String details="LOGIN$"+uname+"$"+pass; String result=getServerConnection(details);
                    if(!result.equals("")&&result.startsWith("VALID")){ StringTokenizer st=new StringTokenizer(result,"$"); st.nextToken();
                        String name=st.nextToken(); String aadhar=st.nextToken(); ServerIPAddress.setUname(name);
                        ServerIPAddress.setHotelname(aadhar);

                        Intent i=new Intent(Crime.this,MenuActivity.class); startActivity(i);
                    }

                }

            });

            Register.setOnClickListener(new OnClickListener() { @Override
            public void onClick(View v) {

// TODO Auto-generated method stub

                Intent i=new Intent(Crime.this,RegisterActivity.class); startActivity(i);
            }

            });

        }

    }

    private String getServerConnection(String data){ try {
        httpclient = new DefaultHttpClient(); HttpGet httpget = new
                HttpGet("http://"+ServerIPAddress.getIpaddress()+":5555/"+data); response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity(); responseText = EntityUtils.toString(entity);
    }

    catch (Exception e){

        Toast.makeText(this, "error"+e.toString(), Toast.LENGTH_LONG).show()

    }

        return responseText.trim();

    }

}
import java.util.StringTokenizer; import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse; import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet; import org.apache.http.impl.client.DefaultHttpClient; import org.apache.http.util.EntityUtils;
        import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.os.StrictMode; import android.view.View;
        import android.view.View.OnClickListener; import android.widget.Button;
        import android.widget.EditText; import android.widget.Toast;

public class Crime extends Activity {

    /** Called when the activity is first created. */ EditText uname_txt,pass_txt,Ip_txt,admin_txt; Button Login,Register;
    private HttpResponse response; private HttpClient httpclient; private String responseText = "";
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.login);
        if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy); uname_txt=(EditText)findViewById(R.id.editText1); pass_txt=(EditText)findViewById(R.id.editText2); Ip_txt=(EditText)findViewById(R.id.editText3); Login=(Button)findViewById(R.id.button1); Register=(Button)findViewById(R.id.button2);
            admin_txt=(EditText)findViewById(R.id.editText_adminno); Login.setOnClickListener(new OnClickListener() {

                @Override

                public void onClick(View v) {

// TODO Auto-generated method stub

                    String uname=uname_txt.getText().toString().trim(); String pass=pass_txt.getText().toString().trim();
                    String adminNO=admin_txt.getText().toString().trim(); ServerIPAddress.setIpaddress(Ip_txt.getText().toString()); ServerIPAddress.setAdminContactno(adminNO);
                    String details="LOGIN$"+uname+"$"+pass; String result=getServerConnection(details);
                    if(!result.equals("")&&result.startsWith("VALID")){ StringTokenizer st=new StringTokenizer(result,"$"); st.nextToken();
                        String name=st.nextToken(); String aadhar=st.nextToken(); ServerIPAddress.setUname(name);
                        ServerIPAddress.setHotelname(aadhar);

                        Intent i=new Intent(Crime.this,MenuActivity.class); startActivity(i);
                    }

                }

            });

            Register.setOnClickListener(new OnClickListener() { @Override
            public void onClick(View v) {

// TODO Auto-generated method stub

                Intent i=new Intent(Crime.this,RegisterActivity.class); startActivity(i);
            }

            });

        }

    }

    private String getServerConnection(String data){ try {
        httpclient = new DefaultHttpClient(); HttpGet httpget = new
                HttpGet("http://"+ServerIPAddress.getIpaddress()+":5555/"+data); response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity(); responseText = EntityUtils.toString(entity);
    }

    catch (Exception e){

        Toast.makeText(this, "error"+e.toString(), Toast.LENGTH_LONG).show()

    }

        return responseText.trim();

    }

}