import org.mindrot.jbcrypt.BCrypt;

public class GeneratePassword {
    public static void main(String[] args) {
        String password = "1234";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("BCrypt hash for '1234': " + hashed);
    }
}
