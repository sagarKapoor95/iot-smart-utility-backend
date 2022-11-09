package iot.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import static iot.constant.constant.USER_TOKEN_PK_PREFIX;
import static iot.constant.constant.USER_TOKEN_SK_PREFIX;

/**
 * The User token entity.
 */
@JsonDeserialize(builder = UserTokenEntity.Builder.class)
public class UserTokenEntity {
    private final String userName;
    private final String password;
    private final Long expiry;
    private final String token;

    /**
     * Instantiates a new User token entity.
     *
     * @param builder the builder
     */
    public UserTokenEntity(Builder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
        this.expiry = builder.expiry;
        this.token = builder.token;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    @JsonIgnore
    public String getPk() {
        return USER_TOKEN_PK_PREFIX+this.token;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    @JsonIgnore
    public String getSk() {
        return USER_TOKEN_SK_PREFIX;
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
     * Gets expiry.
     *
     * @return the expiry
     */
    @JsonIgnore
    public Long getExpiry() {
        return expiry;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String userName;
        private String password;
        private String token;
        private Long expiry;

        private Builder() {
        }

        /**
         * Sets pk.
         *
         * @param pk the pk
         * @return the pk
         */
        public Builder setPk(String pk) {
            return this;
        }

        /**
         * Sets sk.
         *
         * @param sk the sk
         * @return the sk
         */
        public Builder setSk(String sk) {
            return this;
        }

        /**
         * Sets user name.
         *
         * @param userName the user name
         * @return the user name
         */
        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        /**
         * Sets token.
         *
         * @param token the token
         * @return the token
         */
        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        /**
         * Sets expiry.
         *
         * @param expiry the expiry
         * @return the expiry
         */
        public Builder setExpiry(Long expiry) {
            this.expiry = expiry;
            return this;
        }

        /**
         * Sets password.
         *
         * @param password the password
         * @return the password
         */
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Build user token entity.
         *
         * @return the user token entity
         */
        public UserTokenEntity build() {
            return new UserTokenEntity(this);
        }
    }
}
