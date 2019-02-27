package ch.schildj.postcardsender.apicall.model.apiRequestDto;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;

/**
 * Transferobject to send the request to the Postcard API of Post CH
 * Recipientaddress-Elements
 */
public class RecipientAddressDto {
    private String title;
    private String lastname;
    private String firstname;
    private String company;
    private String street;
    private String houseNr;
    private String zip;
    private String city;
    private String poBox;

    public RecipientAddressDto(PostcardApiDto postcard) {
        this.lastname = postcard.getRecipientLastname();
        this.firstname = postcard.getRecipientFirstname();
        this.street = postcard.getRecipientStreet();
        this.houseNr = postcard.getRecipientHousenr();
        this.zip = postcard.getRecipientZip();
        this.city = postcard.getRecipientCity();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }
}
