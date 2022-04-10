package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class PartData {
    // type of object for the observable list in the diamond
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }
    public static boolean deletePart(int id) {
        for(Part part : getAllParts()) {
            if(part.getId() == id) {
                return getAllParts().remove(part);
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

    public static ObservableList<Part> filterPartsWithId(int id) {
        if (!(getFilteredParts().isEmpty())) {
            // clear list
            getFilteredParts().clear();
        }
        for (Part part : getAllParts()) {
            if(part.getId() == id) {
                getFilteredParts().add(part);
            }
        }
        if (getFilteredParts().isEmpty()) {
            return getAllParts();
        } else {
            return getFilteredParts();
        }
    }

    public static ObservableList<Part> filterPartsWithText(String searchText) {
        if (!(getFilteredParts().isEmpty())) {
            // clear list
            getFilteredParts().clear();
        }
        for (Part part : getAllParts()) {
            if(part.getName().contains(searchText)) {
                getFilteredParts().add(part);
            }
        }
        if (getFilteredParts().isEmpty()) {
            return getAllParts();
        } else {
            return getFilteredParts();
        }
    }

    public static void search(String searchText, TableView tableView) {
        try {
            int id = Integer.parseInt(searchText);
            tableView.setItems(filterPartsWithId(id));
            tableView.setItems(getFilteredParts());
        } catch (NumberFormatException error) {
            tableView.setItems(filterPartsWithText(searchText));
            tableView.setItems(getFilteredParts());
        }
    }

    public static boolean modify(int id, Part modifiedPart) {
        int index = 0;
        for(Part part : getAllParts()) {
            if(part.getId() == id) {
                getAllParts().set(index, modifiedPart);
                return true;
            }
            index++;
        }
        return false;
    }
}
