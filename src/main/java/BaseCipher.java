public abstract class BaseCipher {

    public abstract void encode(String input, String output) throws Exception;

    public abstract void decode(String input, String output) throws Exception;

    public abstract String getEncodesFileExtension();

}
