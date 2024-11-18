package task3;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MarvelCharacter {

    private int id;
    private String name;
    private String description;
    private String thumbnail;
    private List<String> comics;
    private List<String> series;
    private List<String> stories;
}

