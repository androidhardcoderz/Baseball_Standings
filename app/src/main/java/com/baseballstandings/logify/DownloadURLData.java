package com.baseballstandings.logify;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Scott on 11/23/2015.
 */
public class DownloadURLData  {

    public InputStream downloadData(String requestUrl) throws
            DownloadException, IOException {

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

		/* forming th java.net.URL object */
        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

		/* optional request header */
        urlConnection.setRequestProperty("Content-Type", "application/json");

        //set inital timeouts to occur if data has not been read in 10 seconds or
        //connection did not establish for 10 seconds since call
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(10000);

		/* optional request header */
        urlConnection.setRequestProperty("Accept", "application/json");

		/* for Get request */
        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();

		/* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(
                    urlConnection.getInputStream());
            return inputStream;
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }


}
