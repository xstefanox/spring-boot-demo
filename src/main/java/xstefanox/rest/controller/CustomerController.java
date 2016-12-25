package xstefanox.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xstefanox.entity.Customer;
import xstefanox.entity.CustomerRepository;
import xstefanox.rest.resource.CustomerResource;
import xstefanox.rest.resourceassembler.CustomerResourceAssembler;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerRepository customerRepository;
    private final CustomerResourceAssembler customerResourceAssembler;
    private final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler;

    @Autowired
    public CustomerController(
            final CustomerRepository customerRepository,
            final CustomerResourceAssembler customerResourceAssembler,
            final PagedResourcesAssembler<Customer> customerPagedResourcesAssembler) {
        this.customerRepository = customerRepository;
        this.customerResourceAssembler = customerResourceAssembler;
        this.customerPagedResourcesAssembler = customerPagedResourcesAssembler;
    }

    @RequestMapping(method = GET)
    @Transactional(readOnly = true)
    public PagedResources<CustomerResource> getCustomers(Pageable pageable) {

        LOGGER.debug("pageable = {}", pageable);

        List<Customer> customers;

        try (Stream<Customer> stream = customerRepository.streamAll()) {
            customers = stream.collect(toList());
        }

        return customerPagedResourcesAssembler.toResource(new PageImpl<>(new ArrayList<>(customers)), customerResourceAssembler);
    }

    @RequestMapping(method = POST)
    public CustomerResource create(@RequestBody Customer customer) {

        LOGGER.debug("saving customer {}", customer);

        customer = customerRepository.save(customer);

        return customerResourceAssembler.toResource(customer);
    }
}
