package dev.SpringBootAPI.ECommerce.dtos.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryPathDTO {

    private Long id;
    private String name;
    private Long parentId;
}