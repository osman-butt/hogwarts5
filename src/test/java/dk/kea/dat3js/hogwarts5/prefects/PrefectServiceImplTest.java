package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.exception.BadRequestException;
import dk.kea.dat3js.hogwarts5.exception.NotFoundException;
import dk.kea.dat3js.hogwarts5.students.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrefectServiceImplTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private PrefectServiceImpl prefectService;

    private final StudentResponseDTO harry = new StudentResponseDTO(1, "Harry", "Potter", "Gryffindor", null, "Gryffindor", 5, "MALE", false);
    private final StudentResponseDTO hermionePrefect = new StudentResponseDTO(2, "Hermione", "Granger", "Gryffindor", null, "Gryffindor", 5, "FEMALE", true);
    private final StudentResponseDTO ron = new StudentResponseDTO(3, "Ron", null, "Weasley", null, "Gryffindor", 5, "MALE", false);
    private final StudentResponseDTO dracoPrefect = new StudentResponseDTO(4, "Draco", null, "Malfoy", null, "Slytherin", 5, "MALE", true);
    private final StudentResponseDTO cedricPrefect = new StudentResponseDTO(5,"Cedric",null, "Diggory",null, "hufflepuff", 6, "MALE",true);
    private final StudentResponseDTO luna = new StudentResponseDTO(6,"Luna",null, "Lovegood",null, "ravenclaw", 4, "FEMALE",false);
    private final StudentResponseDTO percyPrefect = new StudentResponseDTO(7,"Percy", "Ignatius","Weasley",null,"Gryffindor",5,"MALE", true);
    private final StudentResponseDTO ginny = new StudentResponseDTO(8,"Ginny","Molly","Weasley",null,"Gryffindor",4,"FEMALE",false);
    @Test
    void whenTwoPrefectsExistInSameHouse_thenIsValidForPrefectReturnsFalse() {
        // TESTING: isValidForPrefect(List<StudentResponseDTO> prefects, String studentGender, int schoolYear)

        // Arrange
        List<StudentResponseDTO> prefects = List.of(percyPrefect, hermionePrefect);
        String studentGender = "MALE";
        int schoolYear = 5;

        // Act
        boolean result = prefectService.isValidForPrefect(prefects, studentGender, schoolYear);

        // Assert
        assertFalse(result);
    }

    @Test
    void whenSchoolYearIsBelowFive_thenIsValidForPrefectReturnsFalse() {
        // TESTING: isValidForPrefect(List<StudentResponseDTO> prefects, String studentGender, int schoolYear)

        // Arrange
        List<StudentResponseDTO> prefects = List.of(hermionePrefect);
        String studentGender = "MALE";
        int schoolYear = 4;

        // Act
        boolean result = prefectService.isValidForPrefect(prefects, studentGender, schoolYear);

        // Assert
        assertFalse(result);
    }

    @Test
    void whenSamePrefectGenderExist_thenIsValidForPrefectReturnsFalse() {
        // TESTING: isValidForPrefect(List<StudentResponseDTO> prefects, String studentGender, int schoolYear)

        // Arrange
        List<StudentResponseDTO> prefects = List.of(hermionePrefect);
        String studentGender = "FEMALE";
        int schoolYear = 5;

        // Act
        boolean result = prefectService.isValidForPrefect(prefects, studentGender, schoolYear);

        // Assert
        assertFalse(result);
    }

    @Test
    void whenSamePrefectGenderDoesNotExistAndSchoolYearAboveFive_thenIsValidForPrefectReturnsTrue() {
        // TESTING: isValidForPrefect(List<StudentResponseDTO> prefects, String studentGender, int schoolYear)

        // Arrange
        List<StudentResponseDTO> prefects = List.of(hermionePrefect);
        String studentGender = "MALE";
        int schoolYear = 5;

        // Act
        boolean result = prefectService.isValidForPrefect(prefects, studentGender, schoolYear);

        // Assert
        assertTrue(result);
    }

    @Test
    void whenStudentDoesNotExist_thenFindStudentByIdThrowsException() {
        // TESTING: findStudentById(int id)

        // Arrange
        int id = 0;

        // Arrange Mock
        when(studentService.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> prefectService.findStudentById(id));
    }

    @Test
    void whenStudentDoesExist_thenFindStudentByIdReturnsStudentResponseDTO() {
        // TESTING: findStudentById(int id)

        // Arrange
        StudentResponseDTO student = harry;
        int id = student.id();

        // Arrange Mock
        when(studentService.findById(id)).thenReturn(Optional.of(student));

        // Act
        StudentResponseDTO result = prefectService.findStudentById(id);

        // Act & Assert
        assert (result.equals(student));
    }

    @Test
    void whenStudentIsNotPrefect_thenGetPrefectByIdThrowsNotFoundException() {
        // getPrefectById(int id);

        // Arrange
        StudentResponseDTO student = harry;
        int id = student.id();
        // Arrange Mock
        when(studentService.findById(id)).thenReturn(Optional.of(student));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> prefectService.getPrefectById(id));
    }

    @Test
    void whenStudentIsPrefect_thenGetPrefectByIdReturnsStudentResponseDTO() {
        // getPrefectById(int id);
        // Arrange
        StudentResponseDTO student = hermionePrefect;
        int id = student.id();

        // Arrange Mock
        when(studentService.findById(id)).thenReturn(Optional.of(student));

        // Act
        StudentResponseDTO result = prefectService.getPrefectById(id);

        // Act & Assert
        assert (result.equals(student));
    }

    @Test
    void whenStudentsArePrefects_thenGetAllPrefectsReturnsListOfPrefects() {
        // getAllPrefects();
        // Arrange
        List<StudentResponseDTO> students = List.of(harry, hermionePrefect, ron, dracoPrefect, cedricPrefect, luna, percyPrefect, ginny);
        List<StudentResponseDTO> prefects = List.of(hermionePrefect, dracoPrefect, cedricPrefect, percyPrefect);

        // Arrange Mock
        when(studentService.findAll()).thenReturn(students);

        // Act
        List<StudentResponseDTO> result = prefectService.getAllPrefects();

        // Assert
        assert(result.equals(prefects));
    }

    @Test
    void whenStudentsAreNotPrefects_thenGetAllPrefectsReturnsEmptyList() {
        // getAllPrefects();
        // Arrange
        List<StudentResponseDTO> students = List.of(harry, ron, luna, ginny);
        List<StudentResponseDTO> prefects = List.of();

        // Arrange Mock
        when(studentService.findAll()).thenReturn(students);

        // Act
        List<StudentResponseDTO> result = prefectService.getAllPrefects();

        // Assert
        assert(result.equals(prefects));
    }

    @Test
    void whenNoStudentPrefectsExistInHouse_thenGetPrefectsInHouseReturnsEmptyList() {
        // getPrefectsInHouse(String house);
        // Arrange
        List<StudentResponseDTO> students = List.of(harry, ron, ginny);
        List<StudentResponseDTO> prefects = List.of();
        String house = "Gryffindor";

        // Arrange Mock
        when(studentService.findByHouseName(house)).thenReturn(students);

        // Act
        List<StudentResponseDTO> result = prefectService.getPrefectsInHouse(house);

        // Assert
        assert(result.equals(prefects));
    }

    @Test
    void whenStudentPrefectsExistInHouse_thenGetPrefectsInHouseReturnsListOfPrefects() {
        // getPrefectsInHouse(String house);
        // Arrange
        List<StudentResponseDTO> students = List.of(harry, hermionePrefect, ron, percyPrefect, ginny);
        List<StudentResponseDTO> prefects = List.of(hermionePrefect,percyPrefect);
        String house = "Gryffindor";

        // Arrange Mock
        when(studentService.findByHouseName(house)).thenReturn(students);

        // Act
        List<StudentResponseDTO> result = prefectService.getPrefectsInHouse(house);

        // Assert
        assert(result.equals(prefects));
    }

    @Test
    void whenTwoStudentPrefectsExist_thenCreatePrefectThrowsBadRequestException() {
        // createPrefect(PrefectRequestDTO prefectRequestDTO);

        // Arrange
        StudentResponseDTO student = harry;
        PrefectRequestDTO prefectRequestDTO = new PrefectRequestDTO(student.id());
        List<StudentResponseDTO> students = List.of(harry, hermionePrefect, ron, percyPrefect, ginny);
        String house = "Gryffindor";

        // Arrange Mock
        when(studentService.findById(student.id())).thenReturn(Optional.of(student));
        when(studentService.findByHouseName(house)).thenReturn(students);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> prefectService.createPrefect(prefectRequestDTO));
    }

    @Test
    void whenOneStudentPrefectsExistButUpdateFails_thenCreatePrefectThrowsBadRequestException() {
        // createPrefect(PrefectRequestDTO prefectRequestDTO);

        // Arrange
        StudentResponseDTO student = harry;
        PrefectRequestDTO prefectRequestDTO = new PrefectRequestDTO(student.id());
        List<StudentResponseDTO> students = List.of(harry, hermionePrefect, ron, ginny);
        String house = "Gryffindor";

        // Arrange Mock
        when(studentService.findById(student.id())).thenReturn(Optional.of(student));
        when(studentService.findByHouseName(house)).thenReturn(students);
        when(studentService.partialUpdate(eq(student.id()), any(StudentRequestDetailedDTO.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadRequestException.class, () -> prefectService.createPrefect(prefectRequestDTO));
    }

    @Test
    void whenOneStudentPrefectsExist_thenCreatePrefectReturns() {
        // createPrefect(PrefectRequestDTO prefectRequestDTO);

        // Arrange
        StudentResponseDTO student = harry;
        PrefectRequestDTO prefectRequestDTO = new PrefectRequestDTO(student.id());
        List<StudentResponseDTO> students = List.of(harry, hermionePrefect, ron, ginny);
        String house = "Gryffindor";
        StudentResponseDTO studentUpdated = new StudentResponseDTO(student.id(), student.firstName(), student.middleName(), student.lastName(),student.fullName(), student.house(),  student.schoolYear(), student.gender(), true);

        // Arrange Mock
        when(studentService.findById(student.id())).thenReturn(Optional.of(student));
        when(studentService.findByHouseName(house)).thenReturn(students);
        when(studentService.partialUpdate(eq(student.id()), any(StudentRequestDetailedDTO.class))).thenReturn(Optional.of(studentUpdated));

        // Act
        StudentResponseDTO result = prefectService.createPrefect(prefectRequestDTO);

        // Assert
        assert(result.equals(studentUpdated));
    }

    @Test
    void whenStudentIsNotPrefect_thenDeletePrefectThrowsNotFoundException() {
        // deletePrefect(int id);

        // Arrange
        StudentResponseDTO student = harry;
        int id = student.id();
        // Arrange Mock
        when(studentService.findById(id)).thenReturn(Optional.of(student));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> prefectService.deletePrefect(id));
    }

    @Test
    void whenStudentIsPrefectButUpdateFails_thenDeletePrefectThrowsBadRequestException() {
        // deletePrefect(int id);

        // Arrange
        StudentResponseDTO student = hermionePrefect;
        int id = student.id();
        // Arrange Mock
        when(studentService.findById(id)).thenReturn(Optional.of(student));
        when(studentService.partialUpdate(eq(id), any(StudentRequestDetailedDTO.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadRequestException.class, () -> prefectService.deletePrefect(id));
    }

    @Test
    void whenStudentIsPrefect_thenDeletePrefectReturnsStudentResponseDTO() {
        // deletePrefect(int id);

        // Arrange
        StudentResponseDTO student = hermionePrefect;
        StudentResponseDTO studentUpdated = new StudentResponseDTO(student.id(), student.firstName(), student.middleName(), student.lastName(),student.fullName(), student.house(),  student.schoolYear(), student.gender(), false);

        // Arrange Mock
        when(studentService.findById(student.id())).thenReturn(Optional.of(student));
        when(studentService.partialUpdate(eq(student.id()), any(StudentRequestDetailedDTO.class))).thenReturn(Optional.of(studentUpdated));

        // Act
        StudentResponseDTO result = prefectService.deletePrefect(student.id());

        // Assert
        assert(result.equals(studentUpdated));
    }

    @Test
    void whenPrefectStatusIsNotInBody_thenUpdatePrefectThrowsBadRequestException() {
        // updatePrefect(int id, StudentRequestDetailedDTO student);

        // Arrange
        int id = 0;
        StudentRequestDetailedDTO student = new StudentRequestDetailedDTO(id, null, null, null, null, null, null, null, null);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> prefectService.updatePrefect(1, student));
    }

    @Test
    void whenPrefectStatusIsTrue_thenUpdatePrefectReturnsCreatePrefect() {
        // updatePrefect(int id, StudentRequestDetailedDTO student);

        // Arrange
        StudentResponseDTO student = harry;
        StudentRequestDetailedDTO studentDetailedDTO = new StudentRequestDetailedDTO(student.id(), null, null, null, null, null, null, null, true);
        List<StudentResponseDTO> students = List.of(harry, hermionePrefect, ron, ginny);
        String house = "Gryffindor";
        StudentResponseDTO studentUpdated = new StudentResponseDTO(student.id(), student.firstName(), student.middleName(), student.lastName(),student.fullName(), student.house(),  student.schoolYear(), student.gender(), true);

        // Arrange Mock
        when(studentService.findById(student.id())).thenReturn(Optional.of(student));
        when(studentService.findByHouseName(house)).thenReturn(students);
        when(studentService.partialUpdate(eq(student.id()), any(StudentRequestDetailedDTO.class))).thenReturn(Optional.of(studentUpdated));

        // Act
        StudentResponseDTO result = prefectService.updatePrefect(student.id(), studentDetailedDTO);

        // Assert
        assert(result.equals(studentUpdated));
    }

    @Test
    void whenPrefectStatusIsFalse_thenUpdatePrefectReturnsDeletePrefect() {
        // updatePrefect(int id, StudentRequestDetailedDTO student);

        // Arrange
        StudentResponseDTO student = hermionePrefect;
        StudentRequestDetailedDTO studentDetailedDTO = new StudentRequestDetailedDTO(student.id(), null, null, null, null, null, null, null, false);
        StudentResponseDTO studentUpdated = new StudentResponseDTO(student.id(), student.firstName(), student.middleName(), student.lastName(),student.fullName(), student.house(),  student.schoolYear(), student.gender(), false);

        // Arrange Mock
        when(studentService.findById(student.id())).thenReturn(Optional.of(student));
        when(studentService.partialUpdate(eq(student.id()), any(StudentRequestDetailedDTO.class))).thenReturn(Optional.of(studentUpdated));

        // Act
        StudentResponseDTO result = prefectService.updatePrefect(student.id(), studentDetailedDTO);

        // Assert
        assert(result.equals(studentUpdated));
    }
}