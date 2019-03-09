package ch.schildj.postcardsender.web.core.translation;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Properties;


interface BulkReadableMessageSource extends MessageSource {
    Properties getAllProperties(Locale locale);
}
