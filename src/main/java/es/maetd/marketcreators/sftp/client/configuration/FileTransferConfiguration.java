package es.maetd.marketcreators.sftp.client.configuration;

import es.maetd.marketcreators.sftp.client.configuration.props.FileTransferSFTPConfigurationProps;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.transport.verification.OpenSSHKnownHosts;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FileTransferConfiguration {

    private static final String KNOWN_HOSTS_FILE_NAME = "known_hosts";

    private final FileTransferSFTPConfigurationProps sftpConfigurationProps;

    public FileTransferConfiguration(FileTransferSFTPConfigurationProps sftpConfigurationProps) {
        this.sftpConfigurationProps = sftpConfigurationProps;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SSHClient configureSftpClient() throws IOException {

        SSHClient sshClient = new SSHClient();

        HostKeyVerifier hostKeyVerifier = sftpConfigurationProps.getVerifyHost()
                ? new OpenSSHKnownHosts(
                        new FileReader(new File(OpenSSHKnownHosts.detectSSHDir(), KNOWN_HOSTS_FILE_NAME)))
                : new PromiscuousVerifier();

        sshClient.addHostKeyVerifier(hostKeyVerifier);

        return sshClient;
    }
}
