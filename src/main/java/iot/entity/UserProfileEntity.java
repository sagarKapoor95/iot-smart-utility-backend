package iot.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * The type User profile entity.
 */
@JsonDeserialize(builder = UserProfileEntity.Builder.class)
public class UserProfileEntity {
    private final String userName;
    private final String password;
    private final String token;

    /**
     * Instantiates a new User profile entity.
     *
     * @param builder the builder
     */
    public UserProfileEntity(Builder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
        this.token = builder.token;
    }

    /**
     * Gets pk.
     *
     * @return the pk
     */
    @JsonIgnore
    public String getPk() {
        return "user#"+this.userName;
    }

    /**
     * Gets sk.
     *
     * @return the sk
     */
    @JsonIgnore
    public String getSk() {
        return "password#"+this.password;
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
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
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
     * The type Builder.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static final class Builder {
        private String userName;
        private String password;
        private String token;

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
         * Build user profile entity.
         *
         * @return the user profile entity
         */
        public UserProfileEntity build() {
            return new UserProfileEntity(this);
        }
    }
}
