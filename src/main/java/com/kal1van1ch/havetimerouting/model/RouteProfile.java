package com.kal1van1ch.havetimerouting.model;

public enum RouteProfile {
    FOOT("foot"),
    CAR("car");

    private final String profile;

    private RouteProfile(
            String profile
    ){
        this.profile = profile;
    }

    public String getProfile(){
        return profile;
    }
}
