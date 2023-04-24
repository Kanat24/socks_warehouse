package socks_warehouse.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private int cottonPart;
    private int quantity;

    public Socks(String color, int cottonPart, int quantity) {
        this.color = color;
        if (cottonPart < 0 || cottonPart > 100) {
            throw new RuntimeException("Процентное содержание хлопка в составе носков должно быть от 0 до 100");
        } else {
            this.cottonPart = cottonPart;
        }

        if (quantity <= 0) {
            throw new RuntimeException("Количество носков не может быть отрицательным");
        } else {
            this.quantity = quantity;
        }

    }

    public Socks() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(int cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            throw new RuntimeException("Процентное содержание хлопка в составе носков должно быть от 0 до 100");
        } else {
            this.cottonPart = cottonPart;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Количество носков не может быть отрицательным");
        } else {
            this.quantity = quantity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPart == socks.cottonPart && quantity == socks.quantity && Objects.equals(id, socks.id) && Objects.equals(color, socks.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, cottonPart, quantity);
    }
}
