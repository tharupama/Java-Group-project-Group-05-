package model;

public abstract class User {

    protected String id;
    protected String name;

    // Constructor
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Abstract method (must override in Lecturer)
    public abstract void displayInfo();
}