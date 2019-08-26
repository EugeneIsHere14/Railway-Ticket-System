package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.entity.Ticket;

import java.util.List;

public interface TicketDAO extends GenericDAO<Ticket> {

    boolean createTicket(Ticket entity, int quantity);

    int findUserId(String lastName);

    int findRouteId(int trainId);

    Ticket findCarriageTypeId(int carriageId);

    double findTicketPrice(int id);
}