package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class DataProvider {
    // type of object for the observable list in the diamond
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }
    public static boolean deletePart(int id) {
        for(Part part : DataProvider.getAllParts()) {
            if(part.getId() == id) {
                return DataProvider.getAllParts().remove(part);
            }
        }
        return false;
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

    public static ObservableList<Part> filterParts(int id) {
        if (!(DataProvider.getFilteredParts().isEmpty())) {
            // clear list
            DataProvider.getFilteredParts().clear();
        }
        for (Part part : DataProvider.getAllParts()) {
            if(part.getId() == id) {
                DataProvider.getFilteredParts().add(part);
            }
        }
        if (DataProvider.getFilteredParts().isEmpty()) {
            return DataProvider.getAllParts();
        } else {
            return DataProvider.getFilteredParts();
        }
    }
    public static ObservableList<Part> filterParts(String searchText) {
        if (!(DataProvider.getFilteredParts().isEmpty())) {
            // clear list
            DataProvider.getFilteredParts().clear();
        }
        for (Part part : DataProvider.getAllParts()) {
            if(part.getName().contains(searchText)) {
                DataProvider.getFilteredParts().add(part);
            }
        }
        if (DataProvider.getFilteredParts().isEmpty()) {
            return DataProvider.getAllParts();
        } else {
            return DataProvider.getFilteredParts();
        }
    }

    public static void search(String searchText, TableView tableView) {
        try {
            int id = Integer.parseInt(searchText);
            tableView.setItems(filterParts(id));
            tableView.setItems(getFilteredParts());
        } catch (NumberFormatException error) {
            tableView.setItems(filterParts(searchText));
            tableView.setItems(getFilteredParts());
        }
    }
}
