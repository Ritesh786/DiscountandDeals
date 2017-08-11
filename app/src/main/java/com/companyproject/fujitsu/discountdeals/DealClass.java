package com.companyproject.fujitsu.discountdeals;

/**
 * Created by lue on 11-08-2017.
 */

public class DealClass {

    private String image;
    private  String dealtitle;

    public DealClass() {
    }

    private String cityname;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDealtitle() {
        return dealtitle;
    }

    public void setDealtitle(String dealtitle) {
        this.dealtitle = dealtitle;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getMinimumcredit() {
        return minimumcredit;
    }

    public void setMinimumcredit(String minimumcredit) {
        this.minimumcredit = minimumcredit;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    private String statename;

    public DealClass(String image, String dealtitle, String cityname, String statename, String countryname, String minimumcredit, String categoryname) {
        this.image = image;
        this.dealtitle = dealtitle;
        this.cityname = cityname;
        this.statename = statename;
        this.countryname = countryname;
        this.minimumcredit = minimumcredit;
        this.categoryname = categoryname;
    }

    private String countryname;
    private String minimumcredit;
    private String categoryname;


}
