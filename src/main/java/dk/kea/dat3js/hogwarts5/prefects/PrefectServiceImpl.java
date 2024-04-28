package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.exception.BadRequestException;
import dk.kea.dat3js.hogwarts5.exception.NotFoundException;
import dk.kea.dat3js.hogwarts5.students.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrefectServiceImpl implements PrefectService {

    private final StudentService studentService;

    public PrefectServiceImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public StudentResponseDTO createPrefect(PrefectRequestDTO prefectRequestDTO) {
        StudentResponseDTO student = findStudentById(prefectRequestDTO.id());
        List<StudentResponseDTO> prefects = getPrefectsInHouse(student.house());
        if (!isValidForPrefect(prefects,student.gender(),student.schoolYear())) {
            throw new BadRequestException("Student is not valid for prefect");
        }
        Optional<StudentResponseDTO> optionalUpdatedStudent = updatePrefectStatus(student.id(), true);
        if (optionalUpdatedStudent.isEmpty()) {
            throw new BadRequestException("Failed to update student to prefect");
        }
        return optionalUpdatedStudent.get();
    }

    @Override
    public StudentResponseDTO getPrefectById(int id) {
        // TODO: Remove optional from here
        StudentResponseDTO student = findStudentById(id);
        if (!student.isPrefect()) {
            throw new NotFoundException("Student is not a prefect");
        }
        return student;
    }

    @Override
    public List<StudentResponseDTO> getAllPrefects() {
        List<StudentResponseDTO> students = studentService.findAll();
        return students.stream().filter(StudentResponseDTO::isPrefect).toList();
    }

    @Override
    public List<StudentResponseDTO> getPrefectsInHouse(String house) {
        List<StudentResponseDTO> students = studentService.findByHouseName(house);
        return students.stream().filter(StudentResponseDTO::isPrefect).toList();
    }

    @Override
    public StudentResponseDTO deletePrefect(int id) {
        StudentResponseDTO student = findStudentById(id);
        if (!student.isPrefect()) {
            throw new NotFoundException("Student is not a prefect");
        }
        Optional<StudentResponseDTO> optionalUpdatedStudent = updatePrefectStatus(student.id(), false);
        if (optionalUpdatedStudent.isEmpty()) {
            throw new BadRequestException("Failed to update student to prefect");
        }
        return optionalUpdatedStudent.get();
    }

    @Override
    public StudentResponseDTO updatePrefect(int id, StudentRequestDetailedDTO student) {
        if(student.getPrefect() == null) {
            throw new BadRequestException("Prefect status is required in body");
        }
        return student.getPrefect() ? createPrefect(new PrefectRequestDTO(id)) : deletePrefect(id);
    }

    public StudentResponseDTO findStudentById(int id) throws NotFoundException {
        Optional<StudentResponseDTO> optionalStudent = studentService.findById(id);
        if (optionalStudent.isEmpty()) throw new NotFoundException("Student not found with id=" + id);
        return optionalStudent.get();
    }

    public boolean isValidForPrefect(List<StudentResponseDTO> prefects, String studentGender, int schoolYear) {
        if (prefects.size() >= 2 | schoolYear<5) return false;
        return prefects.stream().noneMatch(student -> studentGender.equals(student.gender()));
    }

    public Optional<StudentResponseDTO> updatePrefectStatus(int id, boolean prefectStatus) {
        return studentService.partialUpdate(
                id,
                StudentRequestDetailedDTO.builder()
                        .id(id)
                        .prefect(prefectStatus).build());
    }
}
