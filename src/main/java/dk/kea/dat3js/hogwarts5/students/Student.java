package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.PersonWithNames;
import dk.kea.dat3js.hogwarts5.house.House;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Student implements PersonWithNames {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String firstName;
  private String middleName;
  private String lastName;
  @ManyToOne
  private House house;
  private Integer schoolYear; // 1-7
  private boolean isPrefect;
  private Gender gender;

  public Student() {
  }

  public Student(String firstName, String lastName, House house, int schoolYear,Gender gender) {
    this(firstName, null, lastName, house, schoolYear,gender);
  }

  public Student(String firstName, String lastName, House house, int schoolYear,Gender gender, boolean isPrefect) {
    this(firstName, null, lastName, house, schoolYear,gender,isPrefect);
  }

  public Student(String firstName, String middleName, String lastName, House house, int schoolYear,Gender gender, boolean isPrefect) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.house = house;
    this.schoolYear = schoolYear;
    this.gender = gender;
    this.isPrefect = isPrefect;
  }

  public Student(String firstName, String middleName, String lastName, House house, int schoolYear,Gender gender) {
    this(firstName, middleName, lastName, house, schoolYear,gender,false);
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
    this.middleName = capitalize(middleName);
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

  public boolean isPrefect() {
    return isPrefect;
  }

  public void setPrefect(boolean prefect) {
    isPrefect = prefect;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
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
