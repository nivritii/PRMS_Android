package sg.edu.nus.iss.phoenix.users.entity;

/**
 * Created by cherry on 24/09/2017.
 */

public class SystemAdministrator extends User {

    private String authority;

    public SystemAdministrator(String authority) {
        this.authority = authority;
    }

    public SystemAdministrator(String idNo, String name, String department, String position, String address, String age, String authority) {
        super(idNo, name, department, position, address, age);
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
