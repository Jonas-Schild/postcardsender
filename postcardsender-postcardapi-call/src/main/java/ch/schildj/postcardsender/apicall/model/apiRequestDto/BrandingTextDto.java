package ch.schildj.postcardsender.apicall.model.apiRequestDto;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;

/**
 * Transferobject to send the request to the Postcard API of Post CH
 * Text part of Branding
 */
public class BrandingTextDto {

    private String text;
    private String blockColor;
    private String textColor;

    public BrandingTextDto(PostcardApiDto postcard) {
        this.text = postcard.getBrandText();
        this.blockColor = postcard.getBrandBlockColor();
        this.textColor = postcard.getBrandTextColor();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
