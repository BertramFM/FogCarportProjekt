package dk.ek.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafConfig {
    public static TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
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