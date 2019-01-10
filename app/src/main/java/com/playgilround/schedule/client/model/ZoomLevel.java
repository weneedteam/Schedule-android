package com.playgilround.schedule.client.model;

public enum ZoomLevel {

    /**
     * Zoom level 0 1:20088000.56607700 meters
     * Zoom level 1 1:10044000.28303850 meters
     * Zoom level 2 1:5022000.14151925 meters
     * Zoom level 3 1:2511000.07075963 meters
     * Zoom level 4 1:1255500.03537981 meters
     * Zoom level 5 1:627750.01768991 meters
     * Zoom level 6 1:313875.00884495 meters
     * Zoom level 7 1:156937.50442248 meters
     * Zoom level 8 1:78468.75221124 meters
     * Zoom level 9 1:39234.37610562 meters
     * Zoom level 10 1:19617.18805281 meters
     * Zoom level 11 1:9808.59402640 meters
     * Zoom level 12 1:4909.29701320 meters
     * Zoom level 13 1:2452.14850660 meters
     * Zoom level 14 1:1226.07425330 meters
     * Zoom level 15 1:613.03712665 meters
     * Zoom level 16 1:306.51856332 meters
     * Zoom level 17 1:153.25928166 meters
     * Zoom level 18 1:76.62964083 meters
     * Zoom level 19 1:38.31482042 meters
     * <p>
     * 목적지와, 내위치거리를 계산해서,
     * Google map zoom level을 결정함.
     */

    ZOOM_LEVEL_0(0, 20088000.56607700), ZOOM_LEVEL_1(1, 10044000.28303850),
    ZOOM_LEVEL_2(2, 5022000.14151925), ZOOM_LEVEL_3(3, 2511000.07075963),
    ZOOM_LEVEL_4(4, 1255500.03537981), ZOOM_LEVEL_5(5, 627750.01768991),
    ZOOM_LEVEL_6(6, 313875.00884495), ZOOM_LEVEL_7(7, 156937.50442248),
    ZOOM_LEVEL_8(8, 78468.75221124), ZOOM_LEVEL_9(9, 39234.37610562),
    ZOOM_LEVEL_10(10, 19617.18805281), ZOOM_LEVEL_11(11, 9808.59402640),
    ZOOM_LEVEL_12(12, 4909.29701320), ZOOM_LEVEL_13(13, 2452.14850660),
    ZOOM_LEVEL_14(14, 1226.07425330), ZOOM_LEVEL_15(15, 613.03712665),
    ZOOM_LEVEL_16(16, 306.51856332), ZOOM_LEVEL_17(17, 153.25928166),
    ZOOM_LEVEL_18(18, 76.62964083), ZOOM_LEVEL_19(19, 38.31482042);

    private int zoomLevel;
    private double distance;

    ZoomLevel(int zoomLevel, double distance) {
        this.zoomLevel = zoomLevel;
        this.distance = distance;
    }

    public static int getZoomLevel(double distance) {
        for (int i = ZoomLevel.values().length - 1; i > 0; i--) {
            ZoomLevel currentZoomLevel = ZoomLevel.values()[i];
            if (distance < currentZoomLevel.distance) {
                return currentZoomLevel.zoomLevel;
            }
        }
        return -1;
    }
}
