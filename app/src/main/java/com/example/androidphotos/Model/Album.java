package com.example.androidphotos.Model;

import java.util.ArrayList;

public class Album {
    public ArrayList<Photo> photos;
    public Photo thumbnail;
    public String name;

    /**
     * Album class constructor.
     *
     * @param name String
     */
    public Album(String name){
        photos = new ArrayList<>();
        thumbnail = new Photo();
        this.name = name;
    }

    /**
     * Adds photo to the album.
     *
     * @param p Photo
     */
    public void addPhoto(Photo p){
        photos.add(p);
    }

    /**
     * Returns the list of photos in the album.
     *
     * @return ArrayList
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }


    /**
     * Checks if another Album object is equal to this object.
     *
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Album)) {
            return false;
        }

        Album a = (Album) obj;

        if(this.thumbnail.equals(a.thumbnail)
                && this.name.equals(a.name)
                && this.photos.containsAll(a.photos)
                && a.photos.containsAll(this.photos)){
            return true;
        }

        return false;
    }

    /**
     * Overrides toString() method in the object class.
     * String representation of the Album class.
     *
     * @return String
     */
    @Override
    public String toString() {
        String s = name + " | " + "Number of photos: " + photos.size();
        return s;
    }
}
