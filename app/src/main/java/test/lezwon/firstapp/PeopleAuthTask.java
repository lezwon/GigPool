package test.lezwon.firstapp;

import android.content.Context;
import android.os.AsyncTask;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.Person;
import org.mortbay.util.IO;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Lezwon on 22-11-2016.
 */
class PeopleAuthTask extends AsyncTask<String, Void, Person> {

    private final Context context;
    private OnPeopleFetched activity;

    PeopleAuthTask(Context context) {
        this.context = context;
    }

    void addOnPeopleFetchedListener(OnPeopleFetched activity){
        this.activity = activity;
    }



    @Override
    protected Person doInBackground(String... authCode){
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        // Go to the Google API Console, open your application's
        // credentials page, and copy the client ID and client secret.
        // Then paste them into the following code.
        String clientId = context.getString(R.string.server_client_id);
        String clientSecret = context.getString(R.string.clientSecret);

        // Or your redirect URL for web based applications.
        String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
        String scope = "https://www.googleapis.com/auth/plus.login";

        GoogleTokenResponse tokenResponse = null;

        // Step 1: Authorize -->
        String authorizationUrl = new GoogleBrowserClientRequestUrl(clientId,
                redirectUrl,
                Arrays.asList(scope))
                .build();

        // Point or redirect your user to the authorizationUrl.
        System.out.println("Go to the following link in your browser:");
        System.out.println(authorizationUrl);


        // Step 2: Exchange -->

        try{
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    httpTransport, jsonFactory, clientId, clientSecret, authCode[0], redirectUrl).execute();

            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setClientSecrets(clientId, clientSecret)
                    .build()
                    .setFromTokenResponse(tokenResponse);

            People people = new People.Builder(httpTransport, jsonFactory, credential)
                    .build();

            return people.people().get("people/me").execute();

        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }

    @Override
    protected void onPostExecute(Person person) {
        this.activity.onPeopleFetched(person);
    }
}
