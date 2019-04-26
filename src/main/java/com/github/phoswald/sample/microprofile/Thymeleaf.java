package com.github.phoswald.sample.microprofile;

import javax.enterprise.context.ApplicationScoped;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@ApplicationScoped
public class Thymeleaf {

    private final TemplateEngine templateEngine;

    protected Thymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(Thymeleaf.class.getClassLoader());
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false); // defaults to true, use false to update when modified

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public String render(String template, String rootName, Object rootObj) {
        Context context = new Context();
        context.setVariable(rootName, rootObj);
        String page = templateEngine.process(template, context);
        return page;
    }
}
