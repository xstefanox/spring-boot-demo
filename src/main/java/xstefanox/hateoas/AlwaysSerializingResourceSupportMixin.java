package xstefanox.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;

@JsonPropertyOrder({ "links", "id" })
public abstract class AlwaysSerializingResourceSupportMixin extends ResourceSupport {

    @Override
    @XmlElement(name = "link")
    @JsonProperty("_links")
    @JsonSerialize(using = Jackson2HalModule.HalLinkListSerializer.class)
    @JsonDeserialize(using = Jackson2HalModule.HalLinkListDeserializer.class)
    public abstract List<Link> getLinks();
}