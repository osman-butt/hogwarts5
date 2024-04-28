package dk.kea.dat3js.hogwarts5.students;


import dk.kea.dat3js.hogwarts5.house.House;
import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private HouseService houseService;

    @InjectMocks
    private StudentService studentService;


    @Test
    void whenHouseDoesNotExist_thenFindByHouseNameReturnsEmptyList() {
        // Testing: findByHouseName(String houseName)

        // Arrange
        String houseName = "NonExistingHouse";

        // Arrange mock
        when(studentRepository.findByHouseName(houseName)).thenReturn(List.of());

        // Act
        List<StudentResponseDTO> students = studentService.findByHouseName(houseName);

        // Assert
        assertTrue(students.isEmpty());
    }

    @Test
    void whenHouseExist_thenFindByHouseNameReturnsListOfStudentResponseDTO() {
        // Testing: findByHouseName(String houseName)

        // Arrange
        String houseName = "Gryffindor";
        House gryffindor = new House("Gryffindor", "Godric Gryffindor", new String[] {"red", "gold"});
        Student harry = new Student("Harry", "James", "Potter", gryffindor, 5, Gender.MALE);
        Student hermione = new Student("Hermione", "Jean", "Granger", gryffindor, 5, Gender.FEMALE);
        Student ron = new Student("Ron", "Bilius", "Weasley", gryffindor, 5, Gender.MALE);

        // Arrange mock
        when(studentRepository.findByHouseName(houseName)).thenReturn(List.of(harry,hermione,ron));

        // Act
        List<StudentResponseDTO> students = studentService.findByHouseName(houseName);

        // Assert
        assertFalse(students.isEmpty());
        for (StudentResponseDTO student : students) {
            assertEquals(houseName, student.house());
        }
    }

    @Test
    void whenStudentDoesNotExist_thenPartialReturnsEmptyOptional() {
        // Testing: partialUpdate(int id, StudentRequestDetailedDTO student)

        // Arrange
        int id = 100;
        StudentRequestDetailedDTO student = new StudentRequestDetailedDTO(
                id, "First", "Middle", "Last",
                "First Middle Last","Gryffindor", 5,
                "MALE", false);

        // Arrange mock
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<?> result = studentService.partialUpdate(id, student);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void whenStudentDoesExist_thenPartialReturnsOptionalOfStudentResponseDTO() {
        // Testing: partialUpdate(int id, StudentRequestDetailedDTO student)

        // Arrange
        int id = 1;
        StudentRequestDetailedDTO student = new StudentRequestDetailedDTO(
                id, "First", "Middle", "Last",
                "First Middle Last","Gryffindor", 5,
                "MALE", true);
        House gryffindor = new House("Gryffindor", "Godric Gryffindor", new String[] {"red", "gold"});
        Student existingStudent = new Student("Harry", "James", "Potter", gryffindor, 5, Gender.MALE);
        existingStudent.setId(id);
        Student updatedStudent = new Student("First", "Middle", "Last", gryffindor, 5, Gender.MALE);
        updatedStudent.setId(id);
        updatedStudent.setPrefect(true);
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO(id, "First", "Middle", "Last","First Middle Last", "Gryffindor", 5, "MALE", true);

        // Arrange mock
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(houseService.findById(student.getHouse())).thenReturn(Optional.of(gryffindor));
        when(studentRepository.save(existingStudent)).thenReturn(updatedStudent);

        // Act
        Optional<StudentResponseDTO> result = studentService.partialUpdate(id, student);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(studentResponseDTO, result.get());
    }
}