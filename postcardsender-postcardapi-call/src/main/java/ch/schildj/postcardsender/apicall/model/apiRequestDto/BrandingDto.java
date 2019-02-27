package ch.schildj.postcardsender.apicall.model.apiRequestDto;

import ch.schildj.postcardsender.apicall.model.PostcardApiDto;

/**
 * Transferobject to send the request to the Postcard API of Post CH
 * Branding-Elements
 */

public class BrandingDto {
    private BrandingTextDto brandingText;
    private BrandingQRCodeDto brandingQRCode;

    public BrandingDto(PostcardApiDto postcard) {
        if (postcard.getBrandText() != null) {
            this.brandingText = new BrandingTextDto(postcard);
        } else if (postcard.getBrandQr() != null) {
            this.brandingQRCode = new BrandingQRCodeDto(postcard);
        }
    }

    public BrandingTextDto getBrandingText() {
        return brandingText;
    }

    public void setBrandingText(BrandingTextDto brandingText) {
        this.brandingText = brandingText;
    }

    public BrandingQRCodeDto getBrandingQRCode() {
        return brandingQRCode;
    }

    public void setBrandingQRCode(BrandingQRCodeDto brandingQRCode) {
        this.brandingQRCode = brandingQRCode;
    }
}
