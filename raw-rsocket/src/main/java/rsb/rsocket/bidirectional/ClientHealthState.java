package rsb.rsocket.bidirectional;

public record ClientHealthState(String state) {

	public static final String STARTED = "started";

	public static final String STOPPED = "stopped";

}
