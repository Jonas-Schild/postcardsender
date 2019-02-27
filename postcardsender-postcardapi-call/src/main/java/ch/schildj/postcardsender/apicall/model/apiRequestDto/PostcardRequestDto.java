package ch.schildj.postcardsender.apicall.model.apiRequestDto;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;

/**
 * Transferobject to send the request to the Postcard API of Post CH
 * Postcard-Elements
 */
public class PostcardRequestDto {
    private SenderAddressDto senderAddress;
    private RecipientAddressDto recipientAddress;
    private String senderText;
    private BrandingDto branding;

    public PostcardRequestDto(PostcardApiDto postcard) {
        this.senderAddress = new SenderAddressDto(postcard);
        this.recipientAddress = new RecipientAddressDto(postcard);
        this.senderText = postcard.getText();
        this.branding = new BrandingDto(postcard);
    }


    public SenderAddressDto getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(SenderAddressDto senderAddress) {
        this.senderAddress = senderAddress;
    }

    public RecipientAddressDto getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(RecipientAddressDto recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getSenderText() {
        return senderText;
    }

    public void setSenderText(String senderText) {
        this.senderText = senderText;
    }

    public BrandingDto getBranding() {
        return branding;
    }

    public void setBranding(BrandingDto branding) {
        this.branding = branding;
    }
}
