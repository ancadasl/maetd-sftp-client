package es.maetd.marketcreators.sftp.client.service.dto.common;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FileTransferCommonDTO {

    @NotEmpty
    private String hostname;

    @NotNull
    private Integer port = 22;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
