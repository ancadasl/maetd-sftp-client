package es.maetd.marketcreators.sftp.client.sources;

import java.io.IOException;
import java.io.InputStream;
import lombok.SneakyThrows;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import org.springframework.core.io.Resource;

public class InMemorySourceFileResource extends InMemorySourceFile {

    private final Resource resource;

    public InMemorySourceFileResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public String getName() {
        return resource.getFilename();
    }

    @SneakyThrows
    @Override
    public long getLength() {
        return resource.getFile().length();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }
}
