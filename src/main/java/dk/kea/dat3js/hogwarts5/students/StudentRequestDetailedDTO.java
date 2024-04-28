package dk.kea.dat3js.hogwarts5.students;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDetailedDTO {
    int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String name;
    private String house;
    private Integer schoolYear;
    private String gender;
    private Boolean prefect;
}
