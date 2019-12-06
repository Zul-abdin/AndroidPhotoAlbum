package com.example.androidphotos.Model;

public class Tag {
    private String name;
    private String value;

    /**
     * Tag class constructor
     *
     * @param name String
     * @param value String
     */
    public Tag(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * Changes the values in the tag class.
     *
     * @param name String
     * @param value String
     */
    public void changeTag(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of the Tag class.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the Tag class.
     *
     * @return String
     */
    public String getValue() {
        return value;
    }

    /**
     * Overrides toString() method in the object class.
     * String representation of Tag.
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.name+ " : " + this.value;
    }

    /**
     * Checks if a Tag class equal to another Tag class.
     *
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tag)) {
            return false;
        }

        Tag t = (Tag) obj;

        if(t.name.equals(this.name) && t.value.equals(this.value)){
            return true;
        }

        return false;
    }
}
