package model;
/** This class is used to create parts that are outsourced*/
public class Outsourced extends Part {
    private String companyName;
    /** This method gets the company name
     * @return company  name
     * */
    public String getCompanyName() {
        return companyName;
    }
    /** This method sets the company name
     * @param companyName the company name
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /** This method is the constructor for the outsourced part
     * @param id id of the part (auto generated)
     * @param name name of the part
     * @param price price of the part
     * @param stock amount of parts in stock
     * @param min minimum amount required for the part
     * @param max maximum amount required for the part
     * @param companyName name of the company where the part is outsourced from
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
}

