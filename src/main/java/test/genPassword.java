package test;

import com.gmail.s0rInb.authentication.AuthenticationService;
import com.gmail.s0rInb.entities.User;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class genPassword {
    private static final String ALGORITHM = "SHA-256";
    public static void main(String args[]){
        User user = new User();
        user.setId(12345L);
        user.setUsername("genPassword");
        user.setPassword("");
        com.gmail.s0rInb.authentication.AuthenticationService authenticationService = new AuthenticationService();
        user = authenticationService.hash(user);
        System.out.println("Hash is: "+user.getHash());
        System.out.println("Salt is: "+user.getSalt());
    }
}
