package example.blogsite.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MessageDTO {
    private String message;
    private Long fromId;
    private Long toId;
}
