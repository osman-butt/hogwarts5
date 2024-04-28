package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.students.StudentRequestDetailedDTO;
import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface PrefectService {
    StudentResponseDTO createPrefect(PrefectRequestDTO prefectRequestDTO);
    StudentResponseDTO getPrefectById(int id);
    List<StudentResponseDTO> getAllPrefects();
    List<StudentResponseDTO> getPrefectsInHouse(String house);
    StudentResponseDTO deletePrefect(int id);
    StudentResponseDTO updatePrefect(int id, StudentRequestDetailedDTO student);
}
