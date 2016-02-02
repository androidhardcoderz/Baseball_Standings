package com.baseballstandings.logify;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Scott on 11/23/2015.
 */
public class InputStreamConverter {

    Context context;

    public InputStreamConverter(Context context){
        this.context = context;
    }

    /*
       converts an input stream to a single string
       used for xml and json files
     */
    public String convertInputStreamToString(InputStream is)
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(is));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        return result;
    }
}
