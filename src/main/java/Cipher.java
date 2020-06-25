import java.io.*;


public class Cipher extends BaseCipher {
    private byte[] password;

    public Cipher(String password) {
        if (password != null) {
            this.password = password.getBytes();
        }
    }

    @Override
    public void encode(String inputPath, String outputPath) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputPath);
        FileOutputStream outputStream = new FileOutputStream(outputPath);

        encode(inputStream, outputStream);
        inputStream.close();
        outputStream.close();

    }

    @Override
    public void decode(String inputPath, String outputPath) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputPath);
        FileOutputStream outputStream = new FileOutputStream(outputPath);

        decode(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
    }

    private void encode(InputStream input, OutputStream output) throws Exception {

        Md5 md5 = Md5.fromBytes(password);

        output.write(md5.getDigest());

        encodeOrDecode(input, output);

    }

    private void decode(InputStream input, OutputStream output) throws Exception {

        byte[] digest = new byte[16];
        input.read(digest);


        Md5 md5FromFile = Md5.fromDigest(digest);
        Md5 md5FromPassword = Md5.fromBytes(password);

        if (!md5FromFile.equals(md5FromPassword)) {
            throw new InvalidPassword();
        }
        encodeOrDecode(input, output);
    }

    @Override
    public String getEncodesFileExtension() {
        return ".secret";
    }

    private void encodeOrDecode(InputStream input, OutputStream output) throws Exception {
        int c;
        int index = 0;

        while ((c = input.read()) != -1) {
            byte xored = (byte) (c ^ getPasswordByte(index));
            output.write(xored);
            index++;
        }
    }

    private byte getPasswordByte(int index) {

        return password[index % password.length];
    }

}