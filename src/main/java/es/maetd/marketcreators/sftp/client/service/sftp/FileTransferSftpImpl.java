package es.maetd.marketcreators.sftp.client.service.sftp;

import es.maetd.marketcreators.sftp.client.service.FileTransferService;
import es.maetd.marketcreators.sftp.client.service.dto.common.FileTransferCommonDTO;
import es.maetd.marketcreators.sftp.client.service.dto.input.DownloadFileServiceIDTO;
import es.maetd.marketcreators.sftp.client.service.dto.input.UploadFileServiceIDTO;
import es.maetd.marketcreators.sftp.client.service.dto.output.FileDTO;
import es.maetd.marketcreators.sftp.client.sources.InMemoryDestinationFileResource;
import es.maetd.marketcreators.sftp.client.sources.InMemorySourceFileResource;
import java.io.FileNotFoundException;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.sftp.SFTPException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FileTransferSftpImpl implements FileTransferService {

    public static final String NO_SUCH_FILE = "no such file";
    public static final String NO_SUCH_DIRECTORY = "no such directory";

    @SneakyThrows
    public void downloadFile(DownloadFileServiceIDTO dto) {
        try (SSHClient sshClient = new SSHClient()) {
            setUpSshClient(sshClient, dto);

            try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
                try {
                    sftpClient.get(
                            dto.getOriginPath(), new InMemoryDestinationFileResource(dto.getDestinationResource()));
                    log.info("Archivo descargado correctamente desde {}", dto.getOriginPath());

                } catch (SFTPException e) {
                    if (e.getMessage().contains(NO_SUCH_FILE) || e.getMessage().contains(NO_SUCH_DIRECTORY)) {
                        log.warn("Archivo {} no encontrado en el servidor SFTP", dto.getOriginPath());
                        throw new FileNotFoundException("Archivo no encontrado: " + dto.getOriginPath());
                    } else {
                        throw e;
                    }
                }
            }
        }
    }

    @SneakyThrows
    public void uploadFile(UploadFileServiceIDTO dto) {
        try (SSHClient sshClient = new SSHClient()) {
            setUpSshClient(sshClient, dto);

            try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
                try {
                    sftpClient.put(
                            new InMemorySourceFileResource(dto.getDestinationResource()), dto.getDestinationPath());
                    log.info("Archivo subido correctamente a {}", dto.getDestinationPath());

                } catch (SFTPException e) {
                    if (e.getMessage().contains(NO_SUCH_FILE) || e.getMessage().contains(NO_SUCH_DIRECTORY)) {
                        log.warn("Error al subir el archivo {} al servidor SFTP", dto.getDestinationPath());
                        throw new FileNotFoundException("Error al subir archivo: " + dto.getDestinationPath());
                    } else {
                        throw e;
                    }
                }
            }
        }
    }

    @SneakyThrows
    public List<FileDTO> listDirectory(FileTransferCommonDTO dto, String remoteDirectory) {
        try (SSHClient sshClient = new SSHClient()) {
            setUpSshClient(sshClient, dto);

            try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
                return sftpClient.ls(remoteDirectory).stream()
                        .filter(r -> !r.isDirectory())
                        .map(r -> {
                            String name = r.getName();
                            String path = r.getPath();
                            String extension = "";
                            int lastDot = name.lastIndexOf('.');
                            if (lastDot > 0 && lastDot < name.length() - 1) {
                                extension = name.substring(lastDot + 1);
                            }
                            return new FileDTO(name, path, extension);
                        })
                        .toList();
            } catch (SFTPException e) {
                if (e.getMessage().contains(NO_SUCH_FILE) || e.getMessage().contains(NO_SUCH_DIRECTORY)) {
                    log.warn("No se encontraron archivos en el servidor SFTP");
                    throw new FileNotFoundException("No se encontraron archivos");
                } else {
                    throw e;
                }
            }
        }
    }

    @SneakyThrows
    private void setUpSshClient(SSHClient sshClient, FileTransferCommonDTO dto) {
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        sshClient.connect(dto.getHostname(), dto.getPort());
        sshClient.authPassword(dto.getUsername(), dto.getPassword());
    }
}
