package es.maetd.marketcreators.sftp.client.service;

import es.maetd.marketcreators.sftp.client.service.dto.common.FileTransferCommonDTO;
import es.maetd.marketcreators.sftp.client.service.dto.input.DownloadFileServiceIDTO;
import es.maetd.marketcreators.sftp.client.service.dto.input.UploadFileServiceIDTO;
import es.maetd.marketcreators.sftp.client.service.dto.output.FileDTO;
import java.io.IOException;
import java.util.List;

public interface FileTransferService {

    void downloadFile(DownloadFileServiceIDTO downloadFileServiceIDTO);

    void uploadFile(UploadFileServiceIDTO uploadFileServiceIDTO);

    List<FileDTO> listDirectory(FileTransferCommonDTO dto, String remoteDirectory);

    void remove(FileTransferCommonDTO dto, String remoteDirectory) throws IOException;
}
