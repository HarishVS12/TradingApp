package com.trading.tradingapp.Retrofit;

//Plain Old Java Object(POJO) Class for JSON

public class LoginJSON {

        private Boolean error;
        private String message;
        private String id;

        public Boolean getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public String getID() {
            return id;
        }
}
