# marketcreators-sftp-client

Librería para **transferencias de archivos SFTP** con Spring Boot.  
Permite subir, descargar y listar archivos en servidores SFTP de manera segura y desacoplada.

---

## Características

- Conexión SFTP usando **SSHClient** de la librería `sshj`.
- Transferencia de archivos en memoria o temporales.
- Métodos disponibles:
    - `downloadFile()`
    - `uploadFile()`
    - `listDirectory()`
- Integración nativa con Spring Boot usando beans y `@Service`.
- Configuración de host y autenticación mediante `FileTransferSFTPConfigurationProps`.
- Uso de try-with-resources para cierre automático de conexiones.

---

## Instalación

### Maven
```xml
<dependency>
    <groupId>es.maetd.marketcreators.sftp.client</groupId>
    <artifactId>marketcreators-sftp-client</artifactId>
    <version>1.0.0</version>
</dependency>


