package es.maetd.marketcreators.sftp.client.service.dto.input;

import es.maetd.marketcreators.sftp.client.service.dto.common.FileTransferCommonDTO;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.core.io.Resource;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DownloadFileServiceIDTO extends FileTransferCommonDTO {

    @NotNull
    private String originPath;

    @NotNull
    private Resource destinationResource;
}
