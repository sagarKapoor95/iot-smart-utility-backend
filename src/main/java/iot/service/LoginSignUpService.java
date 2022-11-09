package iot.service;

import iot.bo.UserToken;
import iot.converter.UserProfileConverter;
import iot.entity.UserProfileEntity;
import iot.entity.UserTokenEntity;
import iot.repository.UserRepository;
import iot.utility.JavaUtil;

import java.time.Instant;
import java.time.Period;

/**
 * The type Login sign up service.
 */
public class LoginSignUpService {
    private final UserRepository userRepository;

    /**
     * Instantiates a new Login sign up service.
     *
     * @param userRepository the user repository
     */
    public LoginSignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Sign up user token entity.
     *
     * @param userName the user name
     * @param password the password
     * @return the user token entity
     */
    public UserTokenEntity signUp(String userName, String password) {
        if (userRepository.getUserDetails(userName, password) != null) {
            return null;
        }

        final var user = addUser(userName, password);
        return addToken(user);
    }

    /**
     * Login user token entity.
     *
     * @param userName the user name
     * @param password the password
     * @return the user token entity
     */
    public UserTokenEntity login(String userName, String password) {
        if (userRepository.getUserDetails(userName, password) == null) {
            return null;
        }

        final var user = addUser(userName, password);
        return addToken(user);
    }

    /**
     * Gets user.
     *
     * @param token the token
     * @return the user
     */
    public UserTokenEntity getUser(String token) {
        return userRepository.getUserToken(token);
    }

    /**
     * Validate token boolean.
     *
     * @param token the token
     * @return the boolean
     */
    public boolean validateToken(String token) {
        final var tokenEntity = userRepository.getUserToken(token);

        if (tokenEntity.getExpiry()<=Instant.now().getEpochSecond()) {
            return false;
        }

        return true;
    }

    private UserTokenEntity addToken(UserProfileEntity user) {
        final var expiry = Instant.now().plus(Period.ofDays(1)).getEpochSecond();
        final var token =
                UserProfileConverter.toUserTokenEntity(user.getUserName(), user.getPassword(), user.getToken(), expiry);
        return userRepository.setUserToken(token);
    }

    private UserProfileEntity addUser(String userName, String password) {

        final var token = JavaUtil.getSaltString(8);
        final var user = UserProfileConverter.toUserProfileEntity(userName, password, token);
        return userRepository.setUserDetails(user);
    }
}
