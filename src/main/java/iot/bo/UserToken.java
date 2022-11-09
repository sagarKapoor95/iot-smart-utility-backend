package iot.bo;

/**
 * The type User token.
 */
public class UserToken {
    private String token;
    private Long timeStamp;

    /**
     * Instantiates a new User token.
     *
     * @param token     the token
     * @param timeStamp the time stamp
     */
    public UserToken(String token, Long timeStamp) {
        this.token = token;
        this.timeStamp = timeStamp;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public Long getTimeStamp() {
        return timeStamp;
    }
}
