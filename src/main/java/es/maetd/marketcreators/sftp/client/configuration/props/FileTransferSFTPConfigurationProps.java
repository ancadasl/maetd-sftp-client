package es.maetd.marketcreators.sftp.client.configuration.props;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "maetd.clients.sftp")
public class FileTransferSFTPConfigurationProps {

    @NotNull
    private String hostname;

    @NotNull
    private Integer port;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Boolean verifyHost = false;

    @NotNull
    private String defaultUploadPath;
}
