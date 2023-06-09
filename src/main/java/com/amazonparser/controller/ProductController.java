package com.amazonparser.controller;

import com.amazonparser.domain.Product;
import com.amazonparser.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public List<Product> listProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("{product_id}")
    public Product getProduct(@PathVariable("product_id") Product product){
        return product;
    }
    @GetMapping("/getProductByAsin/{asin}")
    public Product getProductByAsin(@PathVariable("asin") String asin){
        return productService.findByAsin(asin);
    }

    @PostMapping
    public Product create(@RequestBody String asin){
        Product newProduct = productService.parseProductByASIN(asin);
        if (newProduct.getReference().equals("")){
            return newProduct;
        }
        return productService.save(newProduct);
    }
    @PutMapping("{product_id}")
    public Product update(@PathVariable String product_id){
        return productService.updateProduct(product_id);
    }

    @DeleteMapping("{asin}")
    public boolean delete(@PathVariable String asin){
        return productService.deleteByAsin(asin);
    }
}
