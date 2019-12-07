package com.example.androidphotos.Model;

import android.net.Uri;

import java.util.ArrayList;

public class Photo {
    private ArrayList<Tag> tags;
    public String caption;
    private Uri url;

    /**
     * Constructor of the Photo class.
     *
     * @param url String
     */
    public Photo(Uri url){
        tags = new ArrayList<>();
        caption = "";
        this.url = url;
    }

    /**
     * Copy constructor of the Photo class.
     *
     * @param photo Photo
     */
    public Photo(Photo photo){
        tags = new ArrayList<>();
        for(Tag tag : photo.getTags()){
            Tag t = new Tag(tag.getName(), tag.getValue());
            tags.add(t);
        }
        caption = photo.getCaption();
        url = photo.getUrl();
    }

    public Photo() {
        tags = new ArrayList<>();
        caption = "";
        this.url = null;
    }

    /**
     * Returns the caption of photo.
     *
     * @return String
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the caption of photo.
     *
     * @param caption String
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Returns an arraylist of tags.
     *
     * @return ArrayList
     */
    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * Adds tag to the tag Arraylist.
     *
     * @param tag Tag
     */
    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    /**
     * Removes a tag from the tag Arraylist.
     *
     * @param tag Tag
     */
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    /**
     * Gets file path url of the photo.
     *
     * @return String
     */
    public Uri getUrl() {
        return url;
    }

    /**
     * Sets the file path url of the photo.
     *
     * @param url String
     */
    public void setUrl(Uri url) {
        this.url = url;
    }

    /**
     * Overrides toString() method in the object class.
     * Returns the string representation of the class.
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.caption;
    }

    /**
     * Checks if another photo class is equal to this class.
     *
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Photo)) {
            return false;
        }

        Photo p = (Photo) obj;

        if(this.url.equals(p.url)){
            return true;
        }

        return false;
    }
}
