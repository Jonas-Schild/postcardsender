package ch.schildj.postcardsender.web.core.translation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Properties;


/**
 * This controller exposes the "angularMessageSource" resource bundle as JSON object. The bundles will be retrieved by
 * the "angular-translate-loader-static-files" "angular-translate" plugin asynchronously.
 * (https://github.com/angular-translate/bower-angular-translate-loader-static-files).
 */
@Api(value="translations", description="Translations-File")
@RestController
@RequestMapping("api/translations")
public class TranslationsResource {

    private final BulkReadableMessageSource messageSource;

    @Autowired
    public TranslationsResource(BulkReadableMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ApiOperation(value = "Send properties-files for the selected language")
    @RequestMapping(value = {"/messages/{lang}/messages.json"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Properties getMessages(@PathVariable String lang) {

        String language = lang;
        // returning default in case of trouble with path variable
        if (language == null || language.trim().length() == 0) {
            language = "en";
        }

        return messageSource.getAllProperties(new Locale(language));
    }

}
