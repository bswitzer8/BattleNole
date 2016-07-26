package bswitzer.android.com.battlenole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Ben on 7/23/2016.
 */
public class MainMenu extends AppCompatActivity {

    String[] items;

    ListView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);

        menu = (ListView) findViewById(R.id.menuList);

        items = new String[]{ "Play Online", "Play Local (vs. Human)", "Play Local (vs. CPU??)"};

        ArrayAdapter listItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        menu.setAdapter(listItems);

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Toast buttered;
                switch(position)
                {
                    case 0:
                        // this would be for online vs. human
                        buttered = Toast.makeText(getApplicationContext(), "Prepare for online play!", Toast.LENGTH_SHORT);
                        buttered.show();
                        call();
                        break;
                    case 1:
                        // this would be for local vs. human
                        buttered = Toast.makeText(getApplicationContext(), "Local human pass and play!", Toast.LENGTH_SHORT);
                        buttered.show();
                        call();
                        break;
                    case 2:
                        buttered = Toast.makeText(getApplicationContext(), "Local cpu play!", Toast.LENGTH_SHORT);
                        buttered.show();
                        call();
                        // this would be for local vs. cpu.
                        break;

                    default:
                        // invalid selection, let's make some toast.
                        buttered = Toast.makeText(getApplicationContext(), "Invalid Selection. How'd you click this?", Toast.LENGTH_SHORT);
                        buttered.show();
                        break;
                }




            }
        });
    }

    private void call()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
