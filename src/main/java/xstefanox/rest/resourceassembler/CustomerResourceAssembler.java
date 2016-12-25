package xstefanox.rest.resourceassembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import xstefanox.entity.Customer;
import xstefanox.rest.controller.CustomerController;
import xstefanox.rest.resource.CustomerResource;

@Service
public class CustomerResourceAssembler extends ResourceAssemblerSupport<Customer, CustomerResource> {

    public CustomerResourceAssembler() {
        super(CustomerController.class, CustomerResource.class);
    }

    @Override
    public CustomerResource toResource(Customer entity) {

        CustomerResource resource = createResourceWithId(entity.getId(), entity);

        resource.setResourceId(entity.getId());
        resource.setFirstName(entity.getFirstName());
        resource.setLastName(entity.getLastName());

        return resource;
    }
}
