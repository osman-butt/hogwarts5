package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void givenMiddleName_whenGetFullName_thenReturnFirstMiddleAndLast() {
        // Arrange
        Student student = new Student("Harry", "James", "Potter", null, 5);

        // Act
        var fullName = student.getFullName();

        // Assert
        assertEquals("Harry James Potter", fullName);
    }

    @Test
    void givenNoMiddleName_whenGetFullName_thenReturnFirstAndLast() {
        // Arrange
        Student student = new Student("Cho", "Chang",  null, 5);

        // Act
        var fullName = student.getFullName();

        // Assert
        assertEquals("Cho Chang", fullName);
    }

    @Test
    void setFullNameWithMiddleName() {
        // Arrange
        Student student = new Student("First", "Middle", "Last", null, 5);
        // Act
        student.setFullName("Harry James Potter");
        // Assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void setFullNameWithoutMiddleName() {
        // Arrange
        Student student = new Student("First", "Middle", "Last", null, 5);
        // Act
        student.setFullName("Cho Chang");
        // Assert
        assertEquals("Cho", student.getFirstName());
        assertNull(student.getMiddleName());
        assertEquals("Chang", student.getLastName());
    }

    @Test
    void setFullNameWithoutLastName() {
        // Arrange
        Student student = new Student("First", "Middle", "Last", null, 5);
        // Act
        student.setFullName("Harry");
        // Assert
        assertEquals("Harry", student.getFirstName());
        assertNull(student.getMiddleName());
        assertNull(student.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        // Arrange
        Student student = new Student("First", "Middle", "Last", null, 5);
        // Act
        student.setFullName("Harry James Sirius Potter");
        // Assert
        assertEquals("Harry", student.getFirstName());
        assertEquals("James Sirius", student.getMiddleName());
        assertEquals("Potter", student.getLastName());
    }

    @Test
    void setFullNameWithEmptyString() {
        // Arrange
        Student student = new Student("First", "Middle", "Last", null, 5);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            student.setFullName("");
        });
        assertEquals("fullName cannot be null or empty", exception.getMessage());

        assertEquals("Middle", student.getMiddleName());
        assertEquals("Last", student.getLastName());
    }

    @Test
    void setFullNameWithNull() {
        // Arrange
        Student student = new Student("First", "Middle", "Last", null, 5);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            student.setFullName(null);
        });
        assertEquals("fullName cannot be null or empty", exception.getMessage());

        assertEquals("Middle", student.getMiddleName());
        assertEquals("Last", student.getLastName());
    }
}