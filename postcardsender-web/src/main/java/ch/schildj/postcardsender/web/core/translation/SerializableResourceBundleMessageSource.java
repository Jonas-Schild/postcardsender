package ch.schildj.postcardsender.web.core.translation;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

/**
 * Class SerializableResourceBundleMessageSource.
 */
public class SerializableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource implements BulkReadableMessageSource {

    @Override
    public Properties getAllProperties(Locale locale) {
        clearCacheIncludingAncestors();
        PropertiesHolder propertiesHolder = getMergedProperties(locale);
        return propertiesHolder.getProperties();
    }

}
