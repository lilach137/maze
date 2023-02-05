package com.example.mazegame;

import java.util.Map;

public class User {


        private String name;

        private String password;
        private String userId;
        private Map<Integer,Integer> scores;
        private boolean premium;

        public User(String name, String password, boolean premium) {
            this.name = name;
            this.password = password;
            this.premium = premium;
        }

        public User() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isPremium() {
            return premium;
        }

        public void setPremium(boolean premium) {
            this.premium = premium;
        }

        public Map<Integer, Integer> getScores() {
            return scores;
        }

        public void setScores(Map<Integer, Integer> scores) {
            this.scores = scores;
        }
}
