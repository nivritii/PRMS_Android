package sg.edu.nus.iss.phoenix.users.entity;

/**
 * Created by cherry on 23/09/2017.
 */

public class User {

    private String idNo;
    private String name;
    private String department;
    private String position;
    private String address;
    private String password;

    public User() {

    }

    public User(String idNo, String name, String department, String position, String address, String password) {
        this.idNo = idNo;
        this.name = name;
        this.department = department;
        this.position = position;
        this.address = address;
        this.password = password;
    }

    public User(String idNo, String name, String department, String position, String address) {
        this.idNo = idNo;
        this.name = name;
        this.department = department;
        this.position = position;
        this.address = address;
    }

    public String getIdNo() {
        return idNo;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(String password) {
        this.password = password;
    }
}
