package com.gmail.s0rInb.authentication;

import com.gmail.s0rInb.controller.Response;
import com.gmail.s0rInb.entities.User;
import com.gmail.s0rInb.entities.UserSession;
import com.gmail.s0rInb.repository.UserSessionRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;


@Controller
public class LoginController {
//    final Logger logger = LogManager.getLogger("Login");
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserSessionRepository userSessionRepository;
//    @Autowired
//    private UserValueService userValueService;
    @Autowired
    private ApplicationEventPublisher publisher;

	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		return salt;
	}

	private static String getHashWithSalt(String string) {
		String token = null;
		try {
			MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
			byte[] salt = getSalt();
			msgDigest.reset();
			msgDigest.update(salt);
			msgDigest.update(string.getBytes());
			byte byteData[] = msgDigest.digest();
			token = toHexString(byteData);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return token;
	}

	// This method converts the byte Array to Hexa String format
	private static String toHexString(byte[] byteData) {
		StringBuilder hexString = new StringBuilder();
		for (byte aByteData : byteData) {
			String hex = Integer.toHexString(0xFF & aByteData);
			hexString.append(hex);
		}
		return hexString.toString();
	}

    //для этих двух RequestMappingов нужен свой контролер
    @RequestMapping(value ={"/","/**"}, method = RequestMethod.GET)
    public String redirect(HttpServletRequest request,
                           HttpServletResponse httpResponse) {
        return "index";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public void error(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws TomcatException {
        String sb = String.valueOf(request.getAttribute("javax.servlet.error.status_code")) +
                " " +
                request.getAttribute("javax.servlet.error.message");
        throw new TomcatException(sb);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/loginUpdate", produces = "application/json")
    @ResponseBody
    public Response doLogin(HttpServletRequest request,
							HttpServletResponse httpResponse,
							@RequestBody final User userFromFront) {

        Response response = new Response();
        UserSession userSession = null;
        User user;
        //проверяем куки.
        if (request.getCookies()!=null) {
            // ищем пару - куки и значение юзерИД в md5
            for (Cookie cookie : request.getCookies()) {
                UserSession userSessionTemp = userSessionRepository.findByToken(cookie.getValue());

                 if (userSessionTemp != null) {
                     if (DigestUtils.md5Hex(userSessionTemp.getUser().getId().toString()).equals(cookie.getName())) {
                         userSession = userSessionTemp;
                         //освежаем сессию
                         userSession.setLastLogin(new Date());
						 userSessionRepository.save(userSession);
                     }
                 }
            }
            if (userSession!=null) {
                response.setEntityClass("userSession");
                response.setEntity(userSession);
                return response;
            }
        }
        //если сессии не было и не было кук - аутентифицируем
        if ((!userFromFront.getUsername().equals("") && !userFromFront.getUsername().equals("undefined")) && (!userFromFront.getPassword().equals("") && !userFromFront.getPassword().equals("undefined"))) {
            try {
               // logger.info("login: " + login + ", from IP: " + getIp(request) + ", user-agent: " + request.getHeader("user-agent"));
                user = authenticationService.authenticate(userFromFront.getUsername(), userFromFront.getPassword());
//				UserValue userValues = userValueService.findByUserIdAndUserValueName(user.getId(), "locale");
//				if (userValues == null) {
//					UserValue userValue = new UserValue();
//                    userValue.setName("locale");
//                    userValue.setValue("en");
//                    userValue.setUser(user);
//					userValueService.save(Collections.singletonList(userValue), getUser());
//				}
            } catch (AuthenticationException e) {
//                logger.info(e.getMessage());
                Login loginEntity = new Login();
                loginEntity.setMessage(e.getMessage());
                response.setEntity(loginEntity);
                return response;
            }

            userSession = new UserSession();
            userSession.setLastLogin(new Date());
            userSession.setUser(user);
            userSession.setToken(getHashWithSalt(user.getHash()));
			userSessionRepository.save(userSession);

            response.setEntityClass("userSession");
            response.setEntity(userSession);

            Cookie cookie = new Cookie(DigestUtils.md5Hex(user.getId().toString()), userSession.getToken());
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            return response;
        }
        //отправляем пустой ответ если ничего не было введено.
        return response;
    }

    private String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public void error() {

    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    public ModelAndView doLogout(HttpServletRequest request,
								 HttpServletResponse httpResponse){
        removeCookie(request,httpResponse);
        return new ModelAndView("redirect:/");
    }

    private void removeCookie(HttpServletRequest request, HttpServletResponse httpResponse) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                UserSession userSessionTemp = userSessionRepository.findByToken(cookie.getValue());
                if (userSessionTemp != null) {
                    if (DigestUtils.md5Hex(userSessionTemp.getUser().getId().toString()).equals(cookie.getName())) {
                        cookie.setValue("0");
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        httpResponse.addCookie(cookie);
						userSessionRepository.delete(userSessionTemp);
                    }
                }
            }
    }
}
