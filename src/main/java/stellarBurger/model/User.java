package stellarBurger.model;

public class User {


    private String email;
    private String password;
    private String name;

    // конструктор без параметров для Gson
    public User(){}

    // конструктор с параметрами
    public User (String email, String password, String name){
        this.email=email;
        this.name=name;
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
        return this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        return this.password = password;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
       return this.name = name;
    }


}
