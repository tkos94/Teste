package tech.ada.java;

public class VideoGameSale {

    public int rank;
    public String name;
    public String platform;
    public String year;
    public String genre;
    public String publisher;
    public double naSales;
    public double euSales;
    public double jpSales;
    public double otherSales;
    public double globalSales;

    public VideoGameSale() {
    }

    public VideoGameSale(String name, String platform, String year, String genre, String publisher, double naSales, double euSales, double jpSales, double otherSales, double globalSales) {
        this.name = name;
        this.platform = platform;
        this.year = year;
        this.genre = genre;
        this.publisher = publisher;
        this.naSales = naSales;
        this.euSales = euSales;
        this.jpSales = jpSales;
        this.otherSales = otherSales;
        this.globalSales = globalSales;
    }
}

