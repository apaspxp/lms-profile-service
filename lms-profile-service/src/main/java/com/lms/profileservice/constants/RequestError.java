package com.lms.profileservice.constants;

import lombok.ToString;

public class RequestError {

    @ToString
    public static enum CreateProfileRequest {

        CREATE_PROFILE_REQ_EMPTY("Profile request cannot be empty;");

        private String error;
        private CreateProfileRequest(String error) {this.error = error;}
        public String msg() {return error;}
    }

}