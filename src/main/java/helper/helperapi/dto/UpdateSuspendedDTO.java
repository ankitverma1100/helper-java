package helper.helperapi.dto;

import lombok.Data;

@Data
public class UpdateSuspendedDTO {
    private String marketid;
    private Boolean issuspended ;
}
