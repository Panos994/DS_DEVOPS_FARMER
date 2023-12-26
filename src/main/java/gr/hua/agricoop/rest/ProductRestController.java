package gr.hua.agricoop.rest;

import gr.hua.agricoop.entity.Product;
import gr.hua.agricoop.entity.User;
import gr.hua.agricoop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    //ok
    @GetMapping("")
    public List<Product> showProducts() {
        return productService.getProducts();
    }


    //ok
    @GetMapping("/new")
    public Product addProduct() {
        return new Product();
    }







  /*
    //not ok -- apla ebala @requestbody
    @PutMapping("{product_id}")
    public Product editProduct(@PathVariable Integer product_id) {


        return productService.getProduct(product_id);
    }
*/
    //ok
    @PutMapping("/{product_id}")
    public ResponseEntity<Product> editProduct(@PathVariable Integer product_id, @RequestBody Product product) {
        Product updatedProduct = productService.editProduct(product_id, product);
        return ResponseEntity.ok(updatedProduct);
    }





    //OK
    @PostMapping("/new")
    public List<Product> saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return productService.getProducts();
    }


    //OK
    @DeleteMapping("{product_id}")
    public List<Product> deleteProduct(@PathVariable Integer product_id) {
        productService.deleteProduct(product_id);
        return productService.getProducts();
    }
}