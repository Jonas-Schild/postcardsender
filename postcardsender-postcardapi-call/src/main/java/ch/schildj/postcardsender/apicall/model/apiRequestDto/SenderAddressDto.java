package ch.schildj.postcardsender.apicall.model.apiRequestDto;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;

/**
 * Transferobject to send the request to the Postcard API of Post CH
 * Senderaddress-Elements
 */
public class SenderAddressDto {
    private String lastname;
    private String firstname;
    private String street;
    private String houseNr;
    private String zip;
    private String city;

    public SenderAddressDto(PostcardApiDto postcard) {
        this.lastname = postcard.getSenderLastname();
        this.firstname = postcard.getSenderFirstname();
        this.street = postcard.getSenderStreet();
        this.houseNr = postcard.getSenderHousenr();
        this.zip = postcard.getSenderZip();
        this.city = postcard.getSenderCity();
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
}
