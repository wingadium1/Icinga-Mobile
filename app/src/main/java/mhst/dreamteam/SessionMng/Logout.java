package mhst.dreamteam.SessionMng;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import mhst.dreamteam.Const;
import mhst.dreamteam.GlobalConfig;

/**
 * Logs out from server
 * @author MinhNN
 */
public class Logout extends AsyncTask<Void, Void, Integer> {
    private HttpClient client; // Client to connect to server

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

        Log.i("Log out", "Logging out");
        client = new DefaultHttpClient(); // Create a client before connecting to server
    }

    @Override
    protected Integer doInBackground(Void... HttpUri) {
        // TODO Auto-generated method stub

        String sLogoutUrl = GlobalConfig.Server + GlobalConfig.logoutUri; // Logout Url

        HttpGet requestGet = new HttpGet(sLogoutUrl); // Create a GET request
        try {
            HttpResponse response = client.execute(requestGet); // Execute the request, return Redirect 302 if success
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
                Log.i("Log out", "Logged out");
                return Const.SESSION_LOGGED_OUT;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Const.ERROR_CONNECTION_ERROR;
        }
        Log.e("Log out", "Not yet");
        return Const.SESSION_LOGGED_IN;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        Log.i("Log out", "Retrieving data...");
    }

    @Override
    protected void onPostExecute(Integer result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }
}
