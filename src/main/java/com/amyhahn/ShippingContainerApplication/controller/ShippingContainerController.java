package com.amyhahn.ShippingContainerApplication.controller;

import com.amyhahn.ShippingContainerApplication.exception.ResourceNotFoundException;
import com.amyhahn.ShippingContainerApplication.model.ShippingContainer;
import com.amyhahn.ShippingContainerApplication.repository.ShippingContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ShippingContainerController {
    @Autowired
    private ShippingContainerRepository shippingContainerRepository;

    @GetMapping("/shippingcontainers")
    public List <ShippingContainer> getAllShippingContainers() {
        return shippingContainerRepository.findAll();
    }

    @GetMapping("/shippingcontainers/{id}/{status}")
    public List <ShippingContainer> getShippingContainersByOwnerId(@PathVariable(value = "id") Long containerOwnerId, @PathVariable(name = "status") String status)
            throws ResourceNotFoundException {
        return shippingContainerRepository.findAvailableContainersByOwnerId(containerOwnerId, status);
    }

    @GetMapping("/shippingcontainers/{id}")
    public ResponseEntity <ShippingContainer> getShippingContainersById(@PathVariable(value = "id") Long containerId)
            throws ResourceNotFoundException {
        ShippingContainer shippingContainer = shippingContainerRepository.findById(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("ShippingContainer not found for this containerId :: " + containerId));
        return ResponseEntity.ok().body(shippingContainer);
    }

    @PostMapping("/shippingcontainers")
    public ShippingContainer createShippingContainer(@Valid @RequestBody ShippingContainer shippingContainer) {
        return shippingContainerRepository.save(shippingContainer);
    }

    @PutMapping("/shippingcontainers/{containerId}")
    public ResponseEntity <ShippingContainer> updateContainer(@PathVariable(value = "containerId") Long containerId,
                                                             @Valid @RequestBody ShippingContainer shippingContainerDetails) throws ResourceNotFoundException {

        ShippingContainer shippingContainer = shippingContainerRepository.findById(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("ShippingContainer not found for this containerId :: " + containerId));

        shippingContainer.setCustomerId(shippingContainerDetails.getCustomerId());
        shippingContainer.setContainerOwnerId(shippingContainerDetails.getContainerOwnerId());
        if (shippingContainer.getStatus() != shippingContainerDetails.getStatus()) {
            shippingContainer.setStatus(shippingContainerDetails.getStatus());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String statusTimestamp = dtf.format(now);
            shippingContainer.setStatusTimestamp(statusTimestamp);
        }
        final ShippingContainer updatedShippingContainer = shippingContainerRepository.save(shippingContainer);
        return ResponseEntity.ok(updatedShippingContainer);
    }

    @DeleteMapping("/shippingcontainers/{containerId}")
    public Map < String, Boolean > deleteShippingContainer(@PathVariable(value = "containerId") Long containerId)
            throws ResourceNotFoundException {
        ShippingContainer shippingContainer = shippingContainerRepository.findById(containerId)
                .orElseThrow(() -> new ResourceNotFoundException("ShippingContainer not found for this containerId :: " + containerId));

        shippingContainerRepository.delete(shippingContainer);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}