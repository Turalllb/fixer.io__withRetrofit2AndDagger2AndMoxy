package mobiledimension.exchangerates.data.db.model;


import java.util.Comparator;

/**
 * Created by Турал on 30.11.2017.
 */
/*Данный класс - модель данных, хранящий название валюты и ее курс, с методами их сравнения между собой*/
public class ModelData {

    public static Comparator<ModelData> COMPARATOR_NAME = new Comparator<ModelData>() {
        @Override
        public int compare(ModelData o1, ModelData o2) {
            return o1.name.compareTo(o2.name);
        }
    };
    public static Comparator<ModelData> COMPARATOR_VALUE_DESCENDING = new Comparator<ModelData>() {
        @Override
        public int compare(ModelData o1, ModelData o2) {
            return o1.value.compareTo(o2.value);
        }
    };
    public static Comparator<ModelData> COMPARATOR_VALUE_ASCENDING = new Comparator<ModelData>() {
        @Override
        public int compare(ModelData o1, ModelData o2) {
            return o2.value.compareTo(o1.value);
        }

    };

    private String name;
    private Double value;

    ModelData(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Double getValue() {
        return this.value;
    }
}
