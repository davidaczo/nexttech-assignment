package david.daczo.assignment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private String name;
    private String command;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> requires;
}
