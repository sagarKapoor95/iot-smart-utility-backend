package iot.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * The type Sign up request.
 */
@JsonDeserialize(builder = SignUpRequest.Builder.class)
public class SignUpRequest {
    private final String userName;
    private final String password;

    /**
     * Instantiates a new Sign up request.
     *
     * @param builder the builder
     */
    public SignUpRequest(Builder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
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

        private Builder() {
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
         * Build sign up request.
         *
         * @return the sign up request
         */
        public SignUpRequest build() {
            return new SignUpRequest(this);
        }
    }
}
