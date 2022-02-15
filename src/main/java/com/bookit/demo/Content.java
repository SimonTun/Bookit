package com.bookit.demo;

import java.util.List;

public class Content {
    int id;
    private SUBJECT subjects;
    boolean enabled;


    public Content(int id,SUBJECT subjects, boolean enabled) {
        this.id=id;
        this.subjects = subjects;
        this.enabled = enabled;

    }

    public Content(SUBJECT subjects, boolean enabled) {
        this.subjects = subjects;
        this.enabled = enabled;

    }

    public Content(SUBJECT subjects) {
        this.subjects = subjects;
    }

    public Content(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SUBJECT getSubjects() {
        return subjects;
    }

    public void setSubjects(SUBJECT subjects) {
        this.subjects = subjects;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Content{" +
                "subject=" + subjects +
                ", enabled=" + enabled +
                '}';
    }
}


