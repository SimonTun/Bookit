package com.bookit.demo;

public class SubjectClass {

    private SUBJECT name;
    private boolean enabled;


    public SubjectClass() {
    }

    public SubjectClass(SUBJECT name) {
        this.name = name;
    }

    public SUBJECT getName() {
        return name;
    }

    public void setName(SUBJECT name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name=" + name +
                ", enabled=" + enabled +
                '}';
    }
}
