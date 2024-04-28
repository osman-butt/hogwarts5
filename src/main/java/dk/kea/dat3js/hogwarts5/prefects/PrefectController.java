package dk.kea.dat3js.hogwarts5.prefects;

import dk.kea.dat3js.hogwarts5.students.StudentResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prefects")
public class PrefectController {
    private final PrefectService prefectService;

    public PrefectController(PrefectService prefectService) {
        this.prefectService = prefectService;
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createPrefect(@RequestBody PrefectRequestDTO prefectRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prefectService.createPrefect(prefectRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getPrefectFromId(@PathVariable int id) {
        return ResponseEntity.ok(prefectService.getPrefectById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllPrefects() {
        return ResponseEntity.ok(prefectService.getAllPrefects());
    }

    @GetMapping("/house/{house}")
    public ResponseEntity<List<StudentResponseDTO>> getAllPrefectsInHouse(@PathVariable String house) {
        return ResponseEntity.ok(prefectService.getPrefectsInHouse(house));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrefect(@PathVariable int id) {
        return ResponseEntity.ok(prefectService.deletePrefect(id));
    }
}
