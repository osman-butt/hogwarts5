package dk.kea.dat3js.hogwarts5;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface PersonWithNames {
    String getFirstName();
    String getMiddleName();
    String getLastName();
    void setFirstName(String firstName);
    void setMiddleName(String middleName);
    void setLastName(String lastName);

    default String getFullName() {
        return getFirstName()  + (getMiddleName()!=null ? " " + getMiddleName() : "") + (getLastName()!=null ? " " + getLastName() : "");
    }

    default void setFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return;
        }

        fullName = fullName.replaceAll("\\s+", " ").trim();

        int firstSpace = fullName.indexOf(" ");
        int lastSpace = fullName.lastIndexOf(" ");

        if(firstSpace == -1){
            setFirstName(fullName);
            setMiddleName(null);
            setLastName(null);
        } else if (lastSpace == firstSpace){
            setFirstName(fullName.substring(0, firstSpace));
            setMiddleName(null);
            setLastName(fullName.substring(firstSpace + 1));
        } else {
            setFirstName(fullName.substring(0, firstSpace));
            setMiddleName(fullName.substring(firstSpace + 1, lastSpace));
            setLastName(fullName.substring(lastSpace + 1));
        }
    }

    default String capitalize(String name) {
        if (name == null) {
            return null;
        }
        if (name.contains(" ")){
            int space = name.indexOf(" ");
            return capitalize(name.substring(0, space)) + " " + capitalize(name.substring(space + 1));
        }

        if(name.startsWith("Mc")) return "Mc" + capitalize(name.substring(2));

        if(name.contains("-")) return Arrays.stream(name.split("-")).map(this::capitalize).collect(Collectors.joining("-"));

        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
