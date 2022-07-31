package dto;

public class CourierAuthWithoutPassword {
    private String login;
    public CourierAuthWithoutPassword(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
