package com.foody.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.util.List;
import java.util.Objects;

@Data
//this class wont have a separate table but will be included in other entities tables
@Embeddable
public class RestaurantDto {

    private Long id;

    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDto that = (RestaurantDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
