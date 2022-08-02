package dto;

public class MakeOrderResponse {
    private int track;
    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public MakeOrderResponse(int track) {
        this.track = track;
    }


}
