package crio.aman.entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    @NotBlank(message = "User name should not be blank")
    private String userName;
//    @NotBlank(message = "score value should be in between ")
//    @Min(value = 0, message = "score should be in between 0 to 100")
//    @Max(value = 100, message = "score should be in between 0 to 100")
    private Integer score;
    private List<Badges> badges;
}
