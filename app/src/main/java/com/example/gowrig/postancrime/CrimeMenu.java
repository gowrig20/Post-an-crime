import android.app.Activity; import android.content.Intent; import android.os.Bundle; import android.view.View;
import android.view.View.OnClickListener; import android.widget.Button;
public class CrimeMenu extends Activity {

    /** Called when the activity is first created. */ Button Post,View;
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.crimemenu); Post=(Button)findViewById(R.id.button1); View=(Button)findViewById(R.id.button2); Post.setOnClickListener(new OnClickListener() {


        @Override

        public void onClick(View v) {

// TODO Auto-generated method stub

            Intent i=new Intent(CrimeMenu.this,CrimePost.class); startActivity(i);
        }

    });

        View.setOnClickListener(new OnClickListener() { @Override
        public void onClick(View v) {

// TODO Auto-generated method stub

            Intent i=new Intent(CrimeMenu.this,CrimeView.class); startActivity(i);
        }

        });

    }

}