package model;
/** This class is used to create parts that are inhouse*/
public class InHouse extends Part {
    private int machineId;
    /** This method gets the machine id */
    public int getMachineId() {
        return machineId;
    }
    /** This method sets the machine id */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    /** This method is the constructor for the inhouse part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
}
