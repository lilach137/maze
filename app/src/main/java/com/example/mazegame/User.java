package com.example.mazegame;

import java.util.Map;

public class User {


        private String name;

        private String password;
        private String userId;
        private Map<Integer,Integer> scores;
        private boolean premium;

        private int lastLevel;


        public User(String name, boolean premium, int lastLevel) {
            this.name = name;
            this.premium = premium;
            this.lastLevel = lastLevel;
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

        public int getLastLevel() {
            return lastLevel;
        }

        public void setLastLevel(int lastLevel) {
            this.lastLevel = lastLevel;
        }

    public Map<Integer, Integer> getScores() {
            return scores;
        }

        public void setScores(Map<Integer, Integer> scores) {
            this.scores = scores;
        }
}
