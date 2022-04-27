package model;
/** This class is used to create parts that are inhouse*/
public class InHouse extends Part {
    private int machineId;
    /** This method gets the machine id
     * @return machineId the machine id
     * */
    public int getMachineId() {
        return machineId;
    }
    /** This method sets the machine id
     * @param machineId the machine id
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    /** This method is the constructor for the inhouse part
     * @param id the id
     * @param name the name
     * @param price the price
     * @param stock the stock
     * @param min the min
     * @param max the max
     * @param machineId the machineId
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
}
