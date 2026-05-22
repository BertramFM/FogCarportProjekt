package dk.ek.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafConfig {
    public static TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/"); // assuming templates are in resources/templates/
        templateResolver.setSuffix(".html");
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    public static String render(String template, Context context) {
        return templateEngine().process(template, context);

    }
}