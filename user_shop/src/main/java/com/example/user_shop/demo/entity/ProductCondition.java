package user_shop.demo.entity;

import lombok.Data;

@Data
public class ProductCondition {
    String key;
    Integer maxPrice;
    Integer minPrice;
}
