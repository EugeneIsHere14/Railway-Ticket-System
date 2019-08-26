package ua.nure.koshevyi.db.dao;

import ua.nure.koshevyi.db.bean.TicketTrainRouteBean;

import java.util.List;

public interface TicketTrainRouteBeanDAO extends GenericDAO<TicketTrainRouteBean> {

    List<TicketTrainRouteBean> getAllFreeSeats(int trainId);

    TicketTrainRouteBean getTicketInfo();
}