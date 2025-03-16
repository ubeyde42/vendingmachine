package com.ubeyde.sample;

import com.ubeyde.sample.entity.Product;
import com.ubeyde.sample.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public InitialDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (productRepository.count() == 0) {

            // Ürünleri ekleyelim
            Product water = new Product();
            water.setName("Water");
            water.setPrice(25);
            water.setStockQuantity(100);
            productRepository.save(water);

            Product coke = new Product();
            coke.setName("Coke");
            coke.setPrice(35);
            coke.setStockQuantity(100);
            productRepository.save(coke);

            Product soda = new Product();
            soda.setName("Soda");
            soda.setPrice(45);
            soda.setStockQuantity(100);
            productRepository.save(soda);

            Product snickers = new Product();
            snickers.setName("Snickers");
            snickers.setPrice(50);
            snickers.setStockQuantity(100);
            productRepository.save(snickers);

            Product chips = new Product();
            chips.setName("Chips");
            chips.setPrice(40);
            chips.setStockQuantity(100);
            productRepository.save(chips);

            Product candyBar = new Product();
            candyBar.setName("Candy Bar");
            candyBar.setPrice(30);
            candyBar.setStockQuantity(100);
            productRepository.save(candyBar);

            Product energyDrink = new Product();
            energyDrink.setName("Energy Drink");
            energyDrink.setPrice(60);
            energyDrink.setStockQuantity(100);
            productRepository.save(energyDrink);

            Product juiceBox = new Product();
            juiceBox.setName("Juice Box");
            juiceBox.setPrice(55);
            juiceBox.setStockQuantity(100);
            productRepository.save(juiceBox);

            Product proteinBar = new Product();
            proteinBar.setName("Protein Bar");
            proteinBar.setPrice(45);
            proteinBar.setStockQuantity(100);
            productRepository.save(proteinBar);

            Product gum = new Product();
            gum.setName("Gum");
            gum.setPrice(20);
            gum.setStockQuantity(100);
            productRepository.save(gum);

            System.out.println("Örnek veriler başarıyla eklendi.");
        }
    }
}
