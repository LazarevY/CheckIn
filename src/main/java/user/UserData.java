package user;

public class UserData {
    private String name;
    private int age;
    private Sex sex;

    public UserData(){

    }

    public UserData(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }


    @Override
    public String toString() {
        String space = System.lineSeparator();
        StringBuilder builder = new StringBuilder();
        builder.append("Name - ").append(name).append(";").append(space);
        builder.append("Age - ").append(age).append(";").append(space);
        builder.append("Sex - ").append(sex == Sex.MALE? "male" : "female").append(";").append(space);
        return builder.toString();
    }
}
