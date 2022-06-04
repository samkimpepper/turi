package com.turi.turi0411.util;


public class GeometryUtils {
    public static Location calculateByDirection(Double baseLat, Double baseLong, double distance, Double bearing) {

        Double radianLat = toRadian(baseLat);
        Double radianLong = toRadian(baseLong);
        Double radianAngle = toRadian(bearing);
        Double distanceRadius = distance / 6371.01;

        Double lat = Math.asin(Math.sin(radianLat) * Math.cos(distanceRadius) +
                Math.cos(radianLat) * Math.sin(distanceRadius) * Math.cos(radianAngle));
        Double longitude = radianLong +
                Math.atan2(Math.sin(radianAngle) * Math.sin(distanceRadius) * Math.cos(radianLat),
                        Math.cos(distanceRadius) - Math.sin(radianLat) * Math.sin(lat));
        longitude = (longitude + 540) % 360 - 180;

        return new Location(toDegree(lat), toDegree(longitude));
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }


}
