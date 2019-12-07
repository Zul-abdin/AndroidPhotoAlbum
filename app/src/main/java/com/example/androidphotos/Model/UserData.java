package com.example.androidphotos.Model;

import java.util.ArrayList;

public class UserData {

    public static ArrayList<Album> albums = null;

    public static void setAlbums(ArrayList<Album> albumArrayList){
        albums = albumArrayList;
    }
}
