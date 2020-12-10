package com.example.final_groupproject;

    public class CurrentEvent {

        private String name, url;
        private double priceMin, priceMax;
        private String imgURL, saved;
        private long id;
        private String eventTime, eventDate;

        public CurrentEvent(String NAME, String URl, String eventTime, String eventDate, double priceMin, double priceMax,long ID,String IMGURL, String SAVED){
            this.name = NAME;
            this.url = URl;
            this.eventTime = eventTime;
            this.eventDate = eventDate;
            this.priceMin = priceMin ;
            this.priceMax = priceMax;
            this.id = ID;
            this.imgURL = IMGURL;
            this.saved = SAVED;
        }

        public String getName(){
            return name;
        }
        public String getURL(){
            return url;
        }
        public String getEventTime(){
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
        public long getId(){
            return id;
        }
        public String getImgURL(){
            return imgURL;
        }
        public String getSaved(){
            return saved;
        }

        public void setID(long dbID){
            this.id = dbID;
        }
        public void setSaved(String s){
            saved = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }




