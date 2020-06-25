import javax.print.DocFlavor;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Md5 {

    private byte[] digest;

    private Md5(byte[] digest) {
        this.digest = digest;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Md5 md5 = (Md5) other;
        return Arrays.equals(digest, md5.digest);
    }


    @Override
    public int hashCode() {
        return Arrays.hashCode(digest);
    }

    public byte[] getDigest() {
        return digest;
    }

    public static Md5 fromDigest(byte[] digest) {
        return new Md5(digest);
    }

    @Override
    public String toString() {
        String result = "";


        for (byte b : digest) {
            result += String.format("%02x", b);
        }
        return result;
    }

    public static Md5 fromBytes(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(bytes);
        return new Md5(md5.digest());
    }
}
