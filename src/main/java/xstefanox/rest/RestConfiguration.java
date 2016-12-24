package xstefanox.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import xstefanox.rest.filter.RequestIdFilter;
import xstefanox.rest.filter.RequestLoggerFilter;
import xstefanox.rest.filter.RequestWrapperFilter;

@EnableSwagger2
@Configuration
@ComponentScan   // import beans in the same package of this class (to enable filters, controllers, resource assemblers, etc.)
@ConditionalOnWebApplication
public class RestConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Configure content negotiation to only support Accept header.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaTypes.HAL_JSON);
        configurer.favorParameter(false);
        configurer.favorPathExtension(false);
    }

    /**
     * Configure the object mapper to serialize resources using HAL format when the serialization is handled by an
     * {@link org.springframework.web.bind.annotation.ExceptionHandler}.
     *
     * see http://stackoverflow.com/questions/33957999/custom-error-is-not-rendered-as-hal-in-spring-boot-1-3-and-spring-hateoas-0-19
     */
    @Bean
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(@Qualifier("_halObjectMapper") ObjectMapper halObjectMapper) {

        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
        jacksonMessageConverter.setObjectMapper(halObjectMapper);
        jacksonMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaTypes.HAL_JSON));

        return jacksonMessageConverter;
    }

    /**
     * Register a filter that generates a request ID for every request.
     */
    @Bean
    public FilterRegistrationBean requestIdRegistrationBean(RequestIdFilter requestIdFilter) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestIdFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);

        return registration;
    }

    /**
     * Register a filter that logs every request.
     */
    @Bean
    public FilterRegistrationBean requestLoggerRegistrationBean(RequestLoggerFilter requestLoggerFilter) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestLoggerFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(2);

        return registration;
    }

    /**
     * Register a filter that logs every request.
     */
    @Bean
    public FilterRegistrationBean requestWrapperRegistrationBean(RequestWrapperFilter requestWrapperFilter) {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(requestWrapperFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(3);

        return registration;
    }

    /**
     * Generate the Swagger UI for the API routes.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .directModelSubstitute(LocalDate.class, String.class);
    }
}
