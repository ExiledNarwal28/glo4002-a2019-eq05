package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.events.EventDate;

public class BookingConfiguration {

    private EventDate startEventDate;
    private EventDate endEventDate;

    public BookingConfiguration() {
        startEventDate = EventDate.getStartEventDate();
        endEventDate = EventDate.getEndEventDate();
    }

    public EventDate getStartEventDate() {
        return startEventDate;
    }

    public void setStartEventDate(EventDate startEventDate) {
        this.startEventDate = startEventDate;
    }

    public EventDate getEndEventDate() {
        return endEventDate;
    }

    public void setEndEventDate(EventDate endEventDate) {
        this.endEventDate = endEventDate;
    }
}