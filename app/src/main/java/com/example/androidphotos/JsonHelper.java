package com.example.androidphotos;

import android.content.Context;

import com.example.androidphotos.Model.Album;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    static String listToJson(ArrayList<Album> list){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(list);
    }

    static void jsonToFile(String json, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("savedAlbums.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String fileToJson(Context context){

        String result = "";

        try{
            InputStream inputStream = context.openFileInput("savedAlbums.txt");

            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String returnedString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((returnedString = bufferedReader.readLine()) != null){
                    stringBuilder.append(returnedString);
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    static ArrayList<Album> jsonToList(String json) {
        if(json.equals("")){
            return new ArrayList<Album>();
        }

        Gson gson = new Gson();
        ArrayList<Album> albumArrayList = gson.fromJson(json, new TypeToken<List<Album>>(){}.getType());

        return albumArrayList;
    }
}
