package com.tw.workshop.repository;

import com.tw.workshop.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Iterable<Product> findByPriceBetween(int low, int high);
}
