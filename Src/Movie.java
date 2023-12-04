package Src;

public class Movie {

    private String title;
    private int year;

    public Movie(String title, int date) {
        this.title = title;
        this.year = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
