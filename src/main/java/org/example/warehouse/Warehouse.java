package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private String name;
    private List<ProductRecord> products;

    private List<ProductRecord> changedProducts;


    private Warehouse(String name) {

        this.name = name;
        products = new ArrayList<ProductRecord>();
        changedProducts = new ArrayList<ProductRecord>();
    }

    //getInstance without a name parameter returns a warehouse named warehouse
    public static Warehouse getInstance() {
        return new Warehouse("Warehouse");
    }

    //using a name returns a warehouse with the set name
    public static Warehouse getInstance(String name) {
        return new Warehouse(name);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price) {
        //Tests for valid inputs
        if (name == null || name == "") {throw new IllegalArgumentException("Product name can't be null or empty.");}
        if (getProductById(id).isPresent()) throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        if (category==null) throw new IllegalArgumentException("Category can't be null.");
        if (id == null) id = UUID.randomUUID();
        if (price == null) price = BigDecimal.ZERO;

        //adds product to list
        ProductRecord product = new ProductRecord(id, name, category, price);
        products.add(product);
        return product;

    }

    public Optional<ProductRecord> getProductById(UUID id){
        return products.stream().filter(v -> v.uuid().equals(id)).findFirst();
    }

    public void updateProductPrice(UUID id, BigDecimal price){
        Optional<ProductRecord> prodOptional= getProductById(id);
        if (prodOptional.isPresent()){
            var oldProduct=prodOptional.get();
            var newProduct=new ProductRecord(oldProduct.uuid(), oldProduct.name(), oldProduct.category(), price);
            products.remove(oldProduct);
            products.add(newProduct);
            changedProducts.add(oldProduct);
        }
        else throw new IllegalArgumentException("Product with that id doesn't exist.");
    }

    public List<ProductRecord> getChangedProducts(){
        return changedProducts;
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    public List<ProductRecord> getProductsBy(Category category){
        return products.stream().filter(productRecord -> productRecord.category().equals(category)).collect(Collectors.toList());
    }


}
