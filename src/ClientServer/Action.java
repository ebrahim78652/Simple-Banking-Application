package ClientServer;

import java.io.Serializable;

public enum Action implements Serializable {
    LOGIN_REQUEST,
    CLOSE_CONNECTION_REQUEST,
    ERROR,
    LOGIN_ACCEPTED,
    DETAILS_OF_RECEIVER,
    DETAILS_OF_RECEIVER_CORRECT
}
