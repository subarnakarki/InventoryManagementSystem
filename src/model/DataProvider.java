package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataProvider {
    // type of object for the observable list in the diamond
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }
    public static  ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static  ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    public static int generateId(ObservableList<Part> parts) {
        return parts.size() + 1;
    }
}
