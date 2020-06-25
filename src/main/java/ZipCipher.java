import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;


public class ZipCipher extends BaseCipher {
    private String password;

    public ZipCipher(String password) {
        this.password = password;
    }

    @Override
    public void encode(String input, String output) throws Exception {
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(EncryptionMethod.AES);
        parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

        ZipFile zip = new ZipFile(output, password.toCharArray());

        zip.addFile(input, parameters);

    }

    @Override
    public void decode(String input, String output) throws Exception {
        ZipFile zip = new ZipFile(input, password.toCharArray());
        zip.extractAll(new File(output).getParent());
    }

    @Override
    public String getEncodesFileExtension() {
        return ".zip";
    }
}
