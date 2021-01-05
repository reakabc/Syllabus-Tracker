package com.abc.reak.syllabustracker;

public class Chapter {

    int id;
    String name, subject;
    float percentage, jee_percentage, neet_percentage;
    boolean is_theory_completed, is_numerical_completed;

    public Chapter(int id, String name, String subject, float percentage, float jee_percentage, float neet_percentage, boolean is_theory_completed, boolean is_numerical_completed) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.percentage = percentage;
        this.jee_percentage = jee_percentage;
        this.neet_percentage = neet_percentage;
        this.is_theory_completed = is_theory_completed;
        this.is_numerical_completed = is_numerical_completed;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", percentage=" + percentage +
                ", jee_percentage=" + jee_percentage +
                ", neet_percentage=" + neet_percentage +
                ", is_theory_completed=" + is_theory_completed +
                ", is_numerical_completed=" + is_numerical_completed +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getJee_percentage() {
        return jee_percentage;
    }

    public void setJee_percentage(float jee_percentage) {
        this.jee_percentage = jee_percentage;
    }

    public float getNeet_percentage() {
        return neet_percentage;
    }

    public void setNeet_percentage(float neet_percentage) {
        this.neet_percentage = neet_percentage;
    }

    public boolean isIs_theory_completed() {
        return is_theory_completed;
    }

    public void setIs_theory_completed(boolean is_theory_completed) {
        this.is_theory_completed = is_theory_completed;
    }

    public boolean isIs_numerical_completed() {
        return is_numerical_completed;
    }

    public void setIs_numerical_completed(boolean is_numerical_completed) {
        this.is_numerical_completed = is_numerical_completed;
    }
}
