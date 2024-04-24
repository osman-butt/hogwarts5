package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.House;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String firstName;
  private String middleName;
  private String lastName;
  @ManyToOne
  private House house;
  private Integer schoolYear; // 1-7

  public Student() {
  }

  public Student(String firstName, String lastName, House house, int schoolYear) {
    this(firstName, null, lastName, house, schoolYear);
  }

  public Student(String firstName, String middleName, String lastName, House house, int schoolYear) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.house = house;
    this.schoolYear = schoolYear;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = capitalize(firstName);
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    middleName = middleName.replaceAll("\\s+", " ").trim();
    if (middleName.contains(" ")) {
      int space = middleName.indexOf(" ");
        this.middleName = capitalize(middleName.substring(0, space)) + " " + capitalize(middleName.substring(space + 1));
    }
    this.middleName = Arrays.stream(middleName.split(" ")).map(this::capitalize).collect(Collectors.joining(" "));
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = capitalize(lastName);
  }

  public House getHouse() {
    return house;
  }

  public void setHouse(House house) {
    this.house = house;
  }

  public Integer getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(Integer schoolYear) {
    this.schoolYear = schoolYear;
  }

  public String getFullName() {
    return firstName  + (middleName!=null ? " " + middleName : "") + (lastName!=null ? " " + lastName : "");
  }

  public void setFullName(String fullName) {
    if (fullName == null || fullName.isEmpty()) {
      throw new IllegalArgumentException("fullName cannot be null or empty");
    }

    fullName = fullName.replaceAll("\\s+", " ").trim();

    int firstSpace = fullName.indexOf(" ");
    int lastSpace = fullName.lastIndexOf(" ");
    var names = fullName.split(" ");
    firstName = null;
    middleName = null;
    lastName = null;
    if (names.length == 1) {
      setFirstName(names[0]);
    }
    if (names.length == 2) {
      setFirstName(names[0]);
      setLastName(names[1]);
    }
    if (names.length > 2) {
      setFirstName(names[0]);
      setMiddleName(fullName.substring(firstSpace + 1, lastSpace));
      setLastName(fullName.substring(lastSpace + 1));
    }
  }

  private String capitalize(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return Objects.equals(getFirstName(), student.getFirstName()) && Objects.equals(getMiddleName(), student.getMiddleName()) && Objects.equals(getLastName(), student.getLastName()) && Objects.equals(getHouse().getName(), student.getHouse().getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFirstName(), getMiddleName(), getLastName(), getHouse().getName());
  }

}
