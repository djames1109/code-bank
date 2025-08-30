package org.castle.djames.scimforge.pojocreator.parser;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import java.nio.charset.StandardCharsets;
import java.util.Locale;


public class AbstractPojoBuilder {

    public static final Configuration FREEMARKER_CONFIGURATION;

    static {
        FREEMARKER_CONFIGURATION = new Configuration(Configuration.VERSION_2_3_31);

        // Where do we load the templates from:
        final String templatePackage = "/scim/translator/freemarker/templates";
        FREEMARKER_CONFIGURATION.setClassForTemplateLoading(EndpointDefinitionBuilder.class, templatePackage);

        // Some other recommended settings:
        FREEMARKER_CONFIGURATION.setIncompatibleImprovements(new Version(2, 3, 20));
        FREEMARKER_CONFIGURATION.setDefaultEncoding(StandardCharsets.UTF_8.name());
        FREEMARKER_CONFIGURATION.setLocale(Locale.ENGLISH);
        FREEMARKER_CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

}
