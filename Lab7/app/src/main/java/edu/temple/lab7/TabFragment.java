package edu.temple.lab7;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;



/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    String url;
    WebView webView;
    int sitesVisited =0;
    View view;

    public TabFragment() {
        // Required empty public constructor
    }

    public int getSitesVisited()
    {
        return sitesVisited;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle starterPack = getArguments();
        if(getSitesVisited() == 0)
        {
            url =starterPack.getString(MainActivity.urlKey);
        }


    }



    public void changeUrl( String newUrl)
    {
        url = newUrl;

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        sitesVisited++;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab, container, false);

        webView =(WebView) view.findViewById(R.id.webView);
        changeUrl(url);
        return view;
    }

}
