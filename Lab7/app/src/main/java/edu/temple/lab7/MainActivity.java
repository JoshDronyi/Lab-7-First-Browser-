package edu.temple.lab7;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity {

    int currentIndex = 0;
    TabFragment currentTab;
    ArrayList<TabFragment> fragmentList = new ArrayList<>();
    TabFragment newTab;
    String url;
    final static String urlKey = "theUrl";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        boolean weGoodBro = false;
        String itemText = item.getTitle().toString();

        if(itemText.equals(getResources().getString(R.string.newTab)))
        {
            addNewFragment();

            weGoodBro = true;

        }else if(itemText.equals(getResources().getString(R.string.next)))
        {
            if(currentIndex < (fragmentList.size()-1))
            {

                loadFragments(R.id.frame, ++currentIndex);
                currentTab = fragmentList.get(currentIndex);
            }else
            {
                Toast.makeText(this, "Currently at the last Tab", Toast.LENGTH_LONG).show();
            }
            weGoodBro = true;

        } else if(itemText.equals(getResources().getString(R.string.previous)))
        {
            if(currentIndex > 0)
            {

                loadFragments(R.id.frame, --currentIndex);
                currentTab = fragmentList.get(currentIndex);
            }else
            {
                Toast.makeText(this, "Currently at the first Tab", Toast.LENGTH_LONG).show();
            }
            weGoodBro = true;

        }


        return weGoodBro;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button okButton = (Button) findViewById(R.id.btnOK);
        final EditText urlTextbox  = (EditText) findViewById(R.id.editText);
        newTab= new TabFragment();
        addNewFragment();


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = urlTextbox.getText().toString().trim();
                String adaptedUrl;


                if(url.equals(""))
                    return;

                if(url.startsWith("http://www."))
                {
                    if(url.endsWith(".com"))
                    {
                        adaptedUrl = url;
                    }
                    else
                    {
                        adaptedUrl = url + ".com";
                    }
                }else {
                    if(url.startsWith("www."))
                    {
                        if(url.endsWith(".com"))
                        {
                            adaptedUrl = "http://" +url;
                        }else
                        {
                            adaptedUrl = "http://" +url + ".com";
                        }
                    }else
                    {

                            adaptedUrl = "https://www.google.com/webhp#q=" + url;

                    }

                }


                //Changes the url to specified Url
                    currentTab.changeUrl(adaptedUrl);
                    fragmentList.remove(currentIndex);
                    fragmentList.add(currentTab);

            }
        });




    }

    //Called once user requests a new Tab
    public void addNewFragment()
    {
        url = "http://www.google.com";
        newTab = new TabFragment();

        Bundle thisBundle = new Bundle();
        thisBundle.putString(urlKey,url);

        newTab.setArguments(thisBundle);

        currentIndex = fragmentList.size();

        fragmentList.add(newTab);
        loadFragments(R.id.frame, currentIndex);

        currentTab = fragmentList.get(currentIndex);



        Toast.makeText(this, "New Fragment added", Toast.LENGTH_SHORT).show();



    }

    public void loadFragments(int frameId, int fragmentIndex)
    {
        TabFragment current = fragmentList.get(fragmentIndex);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(frameId, current);
        ft.commit();
        fm.executePendingTransactions();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean weGoodBro = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);

        return weGoodBro;
    }


}
