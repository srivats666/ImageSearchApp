package com.example.sjayaram.gridimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.sjayaram.gridimagesearch.Listeners.EndlessScrollListener;
import com.example.sjayaram.gridimagesearch.fragments.FilterSettingsDialog;
import com.example.sjayaram.gridimagesearch.adapters.ImageResultAdapter;
import com.example.sjayaram.gridimagesearch.models.ImageResult;
import com.example.sjayaram.gridimagesearch.R;
import com.example.sjayaram.gridimagesearch.models.SearchFilter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class SearchActivity extends ActionBarActivity implements FilterSettingsDialog.FilterSettingDialogListener {

    private StaggeredGridView gvResults;
    private ArrayList<ImageResult> results;
    private ImageResultAdapter aImageResults;
    private static final int filter_code = 56;
    private SearchFilter filter;
    private int start;
    private String query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        results = new ArrayList();
        aImageResults = new ImageResultAdapter(this, results);
        gvResults.setAdapter(aImageResults);

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                  start = totalItemsCount;
                  onImageSearch(false);
            }
        });

        filter =  new SearchFilter();
    }

    public void setupViews(){
        gvResults = (StaggeredGridView)findViewById(R.id.gvResults);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = results.get(position);
                i.putExtra("result", result);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setInputType(InputType.TYPE_NULL);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                SearchActivity.this.query = query;
                onImageSearch(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void onImageSearch(boolean newSearchFlag){
        //String query = etQuery.getText().toString();

        if(newSearchFlag) {
            aImageResults.clear();
            start = 0;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        StringBuilder url = new StringBuilder("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+ query +"&rsz=8" +
                "&start="+start);

        if(filter.colorFilter!= null && !"".equals(filter.colorFilter))
            url.append("&imgc=" + filter.colorFilter);

        if(filter.siteFilter!=null && !"".equals(filter.siteFilter))
            url.append("&as_sitesearch=" + filter.siteFilter);

        if(filter.imageSize!=null && !"".equals(filter.imageSize))
            url.append("&imgsz=" + filter.imageSize);

        if(filter.imageType!=null && !"".equals(filter.imageType))
            url.append("&imgtype=" + filter.imageType);

        if(!isNetworkAvailable()){
            //toast the error
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
        }
        else {

            client.get(url.toString(), new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("Debug ", response.toString());

                    try {
                        JSONArray imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                        //When you make changes to adapter it does modify underlying data
                        //results.addAll(ImageResult.fromJsonArray(imageResultsJson));
                        aImageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));
                    } catch (JSONException ex) {
                        Toast.makeText(getApplicationContext(), R.string.json_error_message, Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }

                    Log.i("INFO", results.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", "Fetch error");
                    Toast.makeText(getApplicationContext(), R.string.json_error_message, Toast.LENGTH_LONG).show();
                }

            });
        }

    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showFilterDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterSettingsDialog editNameDialog = FilterSettingsDialog.newInstance(getResources().getString(R.string.filter_search_label));
        editNameDialog.show(fm, "fragment_edit_name");
        Bundle args = new Bundle();
        args.putSerializable("filter", filter);
        editNameDialog.setArguments(args);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 1. Check for request code
        // 2. check for result code
        // 3. Extract result
        if(requestCode == filter_code){
            if(resultCode == RESULT_OK){
                filter = (SearchFilter)data.getSerializableExtra("filter");
            }
        }
    }

    @Override
    public void onFinishDialog(SearchFilter filter) {
        this.filter = filter;
    }


}
