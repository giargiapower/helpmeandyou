

package com.example.Segnalazioni.Model;


import javax.persistence.*;
@Entity
@Table(name = "category")
public class Category {
    @Id
    private String type;

    public Category(String type) {
        this.type = type;
    }

    public Category() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Category{" +
                "type='" + type + '\'' +
                '}';
    }
}
