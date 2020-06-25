import static org.junit.jupiter.api.Assertions.*;

class Md5Test {

    @org.junit.jupiter.api.Test
    void fromBytes() throws  Exception{
        Md5 md5 = Md5.fromBytes("secret".getBytes());

        assertEquals("5ebe2294ecd0e0f08eab7690d2a6ee69", md5.toString());

    }
}