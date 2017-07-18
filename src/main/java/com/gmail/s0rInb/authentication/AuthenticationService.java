package com.gmail.s0rInb.authentication;

import com.gmail.s0rInb.entities.User;
import com.gmail.s0rInb.repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Danila on 17.08.2015.
 */
@Service("authenticationService")
public class AuthenticationService {

//    private static final Logger LOG = LogManager.getLogger("Login");
    private static final String ALGORITHM = "SHA-256";

    @PersistenceContext
    EntityManager em;

    @Autowired
    private UserRepository userRepository;

    public User hash(User user) {
      //  User user0 = new User();
        user.setId(user.getId());
        user.setUsername(user.getUsername());
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] saltBytes = generateRandomBytes();
            byte[] pwdBytes = user.getPassword().getBytes();
            byte[] bytesToHash = new byte[saltBytes.length + pwdBytes.length];
            System.arraycopy(saltBytes, 0, bytesToHash, 0, saltBytes.length);
            System.arraycopy(pwdBytes, 0, bytesToHash, saltBytes.length, pwdBytes.length);
            byte[] hash = md.digest(bytesToHash);
            user.setSalt(new String(Base64.encodeBase64(saltBytes)));
            user.setHash(new String(Base64.encodeBase64(hash)));
        } catch (NoSuchAlgorithmException e) {
//            LOG.error("no " + ALGORITHM + " found", e);
            throw new RuntimeException(e);
        }
        return user;
    }

    private byte[] generateRandomBytes() {
        Random random = new Random();
        byte[] array = new byte[6];
        random.nextBytes(array);
        return array;
    }

    public User authenticate(String username, String password) throws AuthenticationException{
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("No user found - " + username);
        }
//        if (user.getBlocked()){
//            throw new AuthenticationException("User is blocked - " + username);
//        }
//		if (StaffStatus.FIRED.equals(user.getStaff().getStaffStatus())) {
//			throw new AuthenticationException("User is fired - " + username);
//		}
        if (checkCredentials(user, password)) {
            user.setPasswordInputs(0);
            userRepository.save(user);
            return user;
        }
        if (user.getPasswordInputs()>=10){
//            user.setBlocked(true);
            user.setPasswordInputs(0);
            userRepository.save(user);
            throw new AuthenticationException("User is blocked - " + username);
        } else {
            user.setPasswordInputs(user.getPasswordInputs()+1);
            userRepository.save(user);
            throw new AuthenticationException("Wrong password! Password inputs left - " + (10 - user.getPasswordInputs()) + " of 10");
        }


    }

    //todo
    private boolean checkCredentials(User user, String password)  {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] decodedSalt = Base64.decodeBase64(user.getSalt());
            byte[] pwdBytes = password.getBytes();
            byte[] bytesToHash = new byte[decodedSalt.length + pwdBytes.length];
            System.arraycopy(decodedSalt, 0, bytesToHash, 0, decodedSalt.length);
            System.arraycopy(pwdBytes, 0, bytesToHash, decodedSalt.length, pwdBytes.length);
            byte[] hash = md.digest(bytesToHash);
            return Arrays.equals(hash, Base64.decodeBase64(user.getHash()));
        } catch (NoSuchAlgorithmException e) {
//            LOG.error("no " + ALGORITHM + " found", e);
            throw new RuntimeException(e);
        }
    }
}
