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
        for(Part part : PartData.getAllParts()) {
            if(part.getId() == id) {
                return PartData.getAllParts().remove(part);
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
        if (!(PartData.getFilteredParts().isEmpty())) {
            // clear list
            PartData.getFilteredParts().clear();
        }
        for (Part part : PartData.getAllParts()) {
            if(part.getId() == id) {
                PartData.getFilteredParts().add(part);
            }
        }
        if (PartData.getFilteredParts().isEmpty()) {
            return PartData.getAllParts();
        } else {
            return PartData.getFilteredParts();
        }
    }

    public static ObservableList<Part> filterParts(String searchText) {
        if (!(PartData.getFilteredParts().isEmpty())) {
            // clear list
            PartData.getFilteredParts().clear();
        }
        for (Part part : PartData.getAllParts()) {
            if(part.getName().contains(searchText)) {
                PartData.getFilteredParts().add(part);
            }
        }
        if (PartData.getFilteredParts().isEmpty()) {
            return PartData.getAllParts();
        } else {
            return PartData.getFilteredParts();
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

    public static boolean modify(int id, Part modifiedPart) {
        int index = 0;
        for(Part part : PartData.getAllParts()) {
            if(part.getId() == id) {
                PartData.getAllParts().set(index, modifiedPart);
                return true;
            }
            index++;
        }
        return false;
    }
}
