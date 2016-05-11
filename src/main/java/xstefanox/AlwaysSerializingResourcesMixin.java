package xstefanox;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;

@JsonPropertyOrder({ "content", "links" })
public abstract class AlwaysSerializingResourcesMixin<T> extends Resources<T> {

    @Override
    @XmlElement(name = "embedded")
    @JsonProperty("_embedded")
    @JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS, using = Jackson2HalModule.HalResourcesSerializer.class)
    @JsonDeserialize(using = Jackson2HalModule.HalResourcesDeserializer.class)
    public abstract Collection<T> getContent();

}
