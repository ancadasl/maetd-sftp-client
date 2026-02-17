package es.maetd.marketcreators.sftp.client.sources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import net.schmizz.sshj.xfer.InMemoryDestFile;
import org.springframework.core.io.Resource;

public class InMemoryDestinationFileResource extends InMemoryDestFile {

    private final Resource resource;

    public InMemoryDestinationFileResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new FileOutputStream(resource.getFile());
    }

    @Override
    public OutputStream getOutputStream(boolean append) throws IOException {
        return new FileOutputStream(resource.getFile(), append);
    }
}
