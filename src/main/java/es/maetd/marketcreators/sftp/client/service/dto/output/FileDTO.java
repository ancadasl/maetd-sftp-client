package es.maetd.marketcreators.sftp.client.service.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private String name;

    private String path;

    private String extension;
}
