package com.tw.workshop.controller;

import com.tw.workshop.repository.ProductRepository;
import com.tw.workshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Product> getAll(@RequestParam(name = "pageIndex", defaultValue = "0")int pageIndex,
                                    @RequestParam(name = "pageSize",defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest(pageIndex, pageSize);
        return repository.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable(value = "id") Long id, @RequestBody Product product) {
        if (!repository.exists(id)) {
            throw new ProductNotFoundException("product " + id + " not found");//should return 400 bad request.
        }
        product.setId(id);
        return repository.save(product);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "id") Long id) {
        if (!repository.exists(id)) {
            throw new ProductNotFoundException("product " + id + " not found");//should return 400 bad request.
        }
        repository.delete(id);
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Product> searchByPrice(@RequestParam(name = "low")int low,
                                           @RequestParam(name = "high")int high){
        return repository.findByPriceBetween(low, high);
    }

    private class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
}
