public class UserData {
    private String name;
    private String birthday;

    public UserData(String name,String birthday){
        this.birthday = birthday;
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }
}
