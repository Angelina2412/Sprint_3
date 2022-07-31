package dto;

public class CourierAuthWithoutLogin {
    private String password;
    public CourierAuthWithoutLogin(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
