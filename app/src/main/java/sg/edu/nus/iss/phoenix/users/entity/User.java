package sg.edu.nus.iss.phoenix.users.entity;

/**
 * Created by cherry on 23/09/2017.
 */

public class User {

    private String idNo;
    private String name;
    private String department;
    private String password;
    private String roles;

    public User() {

    }
    public User(String idNo, String password, String name,String roles){
        this.idNo = idNo;
        this.roles = roles;
        this.password = password;
        this.name = name;
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



    public String getPassword() {
        return password;
    }

    public String getRoles(){return roles;}

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRoles(String roles){
        this.roles=roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
