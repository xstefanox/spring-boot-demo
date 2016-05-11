package xstefanox;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.hal.ResourcesMixin;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Service
public class HalBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object BeanPostProcessor, String beanName) throws BeansException {
        return BeanPostProcessor;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        List<HttpMessageConverter<?>> converters = null;

        if (bean instanceof RequestMappingHandlerAdapter) {

            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter) bean;
            converters = adapter.getMessageConverters();
        }

        if (bean instanceof AnnotationMethodHandlerAdapter) {

            AnnotationMethodHandlerAdapter adapter = (AnnotationMethodHandlerAdapter) bean;
            converters = Arrays.asList(adapter.getMessageConverters());
        }

        if (bean instanceof RestTemplate) {

            RestTemplate template = (RestTemplate) bean;
            converters = template.getMessageConverters();
        }

        if (converters != null) {
            for (HttpMessageConverter<?> converter : converters) {
                if (converter instanceof MappingJackson2HttpMessageConverter) {
                    MappingJackson2HttpMessageConverter halConverterCandidate = (MappingJackson2HttpMessageConverter) converter;
                    ObjectMapper objectMapper = halConverterCandidate.getObjectMapper();
                    if (Jackson2HalModule.isAlreadyRegisteredIn(objectMapper)) {

                        Jackson2HalModule jackson2HalModule = new Jackson2HalModule();
                        jackson2HalModule.setMixInAnnotation(Resources.class, AlwaysSerializingResourcesMixin.class);

                        objectMapper.registerModule(jackson2HalModule);

                        return bean;
                    }
                }
            }
        }

        return bean;
    }
}
