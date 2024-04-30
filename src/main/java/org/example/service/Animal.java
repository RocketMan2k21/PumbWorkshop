package org.example.service;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

@DatabaseTable(tableName = "animals")
public class Animal {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    @CsvBindByName(column = "Name")
    private String name;
    @DatabaseField
    @CsvBindByName(column = "Type")
    private String type;

    @DatabaseField
    @CsvBindByName(column = "Sex")
    private String sex;

    @DatabaseField
    @CsvBindByName(column = "Weight")
    private int weight;

    @DatabaseField
    @CsvBindByName(column = "Cost")
    private int cost;

    @DatabaseField
    private int category;

    public Animal() {

    }

    public Animal(int id, String name, String type, String sex, int weight, int cost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.weight = weight;
        this.cost = cost;
    }

    public void eveluateCategory() {
        if(cost >= 0 && cost <= 20)
            category = 1;
        else if(cost >= 21 && cost <= 41)
            category = 2;
        else if(cost >= 41 && cost <= 61)
            category = 3;
        else if (cost > 61)
            category = 4;
        else {
            throw new IncorrectAnimalCostException(" cost: " + cost);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", type='" + type + '\'' +
                ", sex='" + sex + '\'' +
                ", weight=" + weight +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && weight == animal.weight && cost == animal.cost && category == animal.category && Objects.equals(name, animal.name) && Objects.equals(type, animal.type) && Objects.equals(sex, animal.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, sex, weight, cost, category);
    }
}
