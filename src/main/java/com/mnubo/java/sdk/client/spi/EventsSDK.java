package com.mnubo.java.sdk.client.spi;

import java.util.List;

import com.mnubo.java.sdk.client.models.Event;

/**
 * Event SDK Client. This interface gives access to handle events in specific objects.
 */
public interface EventsSDK {

    /**
     * Allows post events to an unique object. Note that in this case all Event.
     *
     * @param deviceId, device Id to send the events
     * @param events, list the events to be posted.
     */
    void send(String deviceId, List<Event> events);

    /**
     * Allows post events to several objects. In this case, the device id is taken
     * directly from each event. Thus, a post will be sent by each event in the list.
     *
     * @param events, list the events to be posted.
     */
    void send(List<Event> events);
}
