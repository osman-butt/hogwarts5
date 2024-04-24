package dk.kea.dat3js.hogwarts5.teachers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TeacherTest {
    @Test
    void givenMiddleName_whenGetFullName_thenReturnFirstMiddleAndLast() {
        // Arrange
        Teacher teacher = new Teacher("Albus", "Percival Wulfric Brian", "Dubledore", null, "Defence Against the Dark Arts", null);

        // Act
        var fullName = teacher.getFullName();

        // Assert
        assertEquals("Albus Percival Wulfric Brian Dubledore", fullName);
    }

    @Test
    void givenNoMiddleName_whenGetFullName_thenReturnFirstAndLast() {
        // Arrange
        Teacher teacher = new Teacher("Minerva", "McGonagall", null, "Defence Against the Dark Arts", null);

        // Act
        var fullName = teacher.getFullName();

        // Assert
        assertEquals("Minerva McGonagall", fullName);
    }

    @Test
    void setFullNameWithMiddleName() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last", null, "Defence Against the Dark Arts", null);
        // Act
        teacher.setFullName("Albus Percival Wulfric Brian Dubledore");
        // Assert
        assertEquals("Albus", teacher.getFirstName());
        assertEquals("Percival Wulfric Brian", teacher.getMiddleName());
        assertEquals("Dubledore", teacher.getLastName());
    }

    @Test
    void setFullNameWithoutMiddleName() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last", null, "Defence Against the Dark Arts", null);
        // Act
        teacher.setFullName("Minerva McGonagall");
        // Assert
        assertEquals("Minerva", teacher.getFirstName());
        assertNull(teacher.getMiddleName());
        assertEquals("McGonagall", teacher.getLastName());
    }

    @Test
    void setFullNameWithoutLastName() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last", null, "Defence Against the Dark Arts", null);
        // Act
        teacher.setFullName("Firenze");
        // Assert
        assertEquals("Firenze", teacher.getFirstName());
        assertNull(teacher.getMiddleName());
        assertNull(teacher.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last", null, "Defence Against the Dark Arts", null);
        // Act
        teacher.setFullName("Albus Percival Wulfric Brian Dubledore");
        // Assert
        assertEquals("Albus", teacher.getFirstName());
        assertEquals("Percival Wulfric Brian", teacher.getMiddleName());
        assertEquals("Dubledore", teacher.getLastName());
    }

    @Test
    void setFullNameWithEmptyString() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last", null, "Defence Against the Dark Arts", null);

        // Act
        teacher.setFullName("");

        // Act & Assert
        assertEquals("First", teacher.getFirstName());
        assertEquals("Middle", teacher.getMiddleName());
        assertEquals("Last", teacher.getLastName());
    }

    @Test
    void setFullNameWithNull() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last", null, "Defence Against the Dark Arts", null);

        // Act
        teacher.setFullName(null);

        // Assert
        assertEquals("First", teacher.getFirstName());
        assertEquals("Middle", teacher.getMiddleName());
        assertEquals("Last", teacher.getLastName());
    }

    @Test
    void capitalizeIndividualNames() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last", null, "Defence Against the Dark Arts", null);

        // Act
        teacher.setFirstName("sEverUs");
        teacher.setMiddleName("PRiNce");
        teacher.setLastName("snAPe");

        // Assert
        assertEquals("Severus", teacher.getFirstName());
        assertEquals("Prince", teacher.getMiddleName());
        assertEquals("Snape", teacher.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithCrazyCapitalization() {
        // Arrange
        Teacher teacher = new Teacher("First", "Middle", "Last",null, "Defence Against the Dark Arts", null);

        // Act
        teacher.setFirstName("seveRUS");
        teacher.setMiddleName("PRINCE");
        teacher.setLastName("snapE");

        // Assert
        assertEquals("Severus", teacher.getFirstName());
        assertEquals("Prince", teacher.getMiddleName());
        assertEquals("Snape", teacher.getLastName());
    }

    @Test
    void capitalizeIndividualNamesWithMultipleMiddleNamesAndCrazyCapitalization() {
        // Arrange
        Teacher teacher = new Teacher("harry", "james", "potter",null, "Defence Against the Dark Arts", null);

        // Act
        teacher.setFirstName("alBUS");
        teacher.setMiddleName("perCIVAl WulFric bRIan");
        teacher.setLastName("dumbleDORE");

        // Assert
        assertEquals("Albus", teacher.getFirstName());
        assertEquals("Percival Wulfric Brian", teacher.getMiddleName());
        assertEquals("Dumbledore", teacher.getLastName());
    }
}
