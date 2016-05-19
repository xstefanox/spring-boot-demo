package xstefanox.hateoas;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Service
public class AlwaysSerializingHalObjectMapperBeanPostProcessor implements BeanPostProcessor {

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

        // process only the MappingJackson2HttpMessageConverter created to serialize HAL-formatted JSON
        if (converters != null) {
            for (HttpMessageConverter<?> converter : converters) {
                if (converter instanceof MappingJackson2HttpMessageConverter) {
                    MappingJackson2HttpMessageConverter halConverterCandidate = (MappingJackson2HttpMessageConverter) converter;
                    ObjectMapper objectMapper = halConverterCandidate.getObjectMapper();
                    if (Jackson2HalModule.isAlreadyRegisteredIn(objectMapper)) {

                        // override the default mixins with the ones configured to always serialize empty HAL properties
                        objectMapper.addMixIn(Resources.class, AlwaysSerializingResourcesMixin.class);
//                        objectMapper.addMixIn(ResourceSupport.class, AlwaysSerializingResourceSupportMixin.class);

                        return bean;
                    }
                }
            }
        }

        return bean;
    }
}
