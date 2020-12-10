package com.example.final_groupproject;

    public class CurrentEvent {

        private long id;
        private String name, url;
        private String eventTime, eventDate;
        private double priceMin, priceMax;

        public CurrentEvent(String name, String url, String eventTime, String eventDate, double priceMin, double priceMax,long id){
            this.name = name;
            this.url = url;
            this.eventTime = eventTime;
            this.eventDate = eventDate;
            this.priceMin = priceMin ;
            this.priceMax = priceMax;
            this.id = id;
        }
        public long getId() {
            return id;
        }
        public String getName(){

            return name;
        }
        public String getURL(){

            return url;
        }
        public String getEventTime()
        {
            return eventTime;
        }
        public String getEventDate(){

            return eventDate;
        }
        public double getPriceMin(){

            return priceMin;
        }
        public double getPriceMax(){

            return priceMax;
        }

        public String toString() {
            return name;
        }
    }




