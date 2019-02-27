package ch.schildj.postcardsender.apicall.model.apiRequestDto;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;

/**
 * Transferobject to send the request to the Postcard API of Post CH
 * Type QR Code of Branding
 */
public class BrandingQRCodeDto {

    private String encodedText;
    private String accompanyingText;
    private String blockColor;
    private String textColor;

    public BrandingQRCodeDto(PostcardApiDto postcard) {
        this.encodedText = postcard.getBrandQr();
        this.accompanyingText = postcard.getBrandText();
        this.blockColor = postcard.getBrandBlockColor();
        this.textColor = postcard.getBrandTextColor();
    }

    public String getEncodedText() {
        return encodedText;
    }

    public void setEncodedText(String encodedText) {
        this.encodedText = encodedText;
    }

    public String getAccompanyingText() {
        return accompanyingText;
    }

    public void setAccompanyingText(String accompanyingText) {
        this.accompanyingText = accompanyingText;
    }

    public String getBlockColor() {
        return blockColor;
    }

    public void setBlockColor(String blockColor) {
        this.blockColor = blockColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
