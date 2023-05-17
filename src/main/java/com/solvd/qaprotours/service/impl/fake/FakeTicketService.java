package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.TicketService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public class FakeTicketService implements TicketService {

    @Override
    public Mono<Ticket> getById(final Long id) {
        Ticket ticket = generateTicket();
        return Mono.just(ticket);
    }

    @Override
    public Flux<Ticket> getAllByUserId(final String userId) {
        User user = generateUser();
        Tour tour = generateTour();
        List<Ticket> tickets = generateTickets(user, tour);
        return Flux.just(tickets.toArray(new Ticket[0]));
    }

    @Override
    public Flux<Ticket> getAllSoonTickets() {
        User user = generateUser();
        Tour tour = generateTour();
        List<Ticket> tickets = generateTickets(user, tour);
        return Flux.just(tickets.toArray(new Ticket[0]));
    }

    @Override
    public Flux<Ticket> getAllSoonNotConfirmedTickets() {
        User user = generateUser();
        Tour tour = generateTour();
        List<Ticket> tickets = generateTickets(user, tour);
        return Flux.just(tickets.toArray(new Ticket[0]));
    }

    @Override
    public Mono<Void> create(final String userId,
                             final Long tourId,
                             final Integer peopleAmount) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(final Long ticketId) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> confirm(final Long ticketId) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> cancel(final Long ticketId) {
        return Mono.empty();
    }

    private List<Ticket> generateTickets(final User user, final Tour tour) {
        Ticket ticket = new Ticket();
        ticket.setUserId(user.getId());
        ticket.setTour(tour);
        List<Ticket> tickets = List.of(ticket);
        return tickets;
    }

    private User generateUser() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setEmail(userEmail);
        return user;
    }

    private Tour generateTour() {
        String tourName = "Tour";
        String tourCountry = "Egypt";
        String tourCity = "Cairo";
        LocalDateTime tourArrivalTime = LocalDateTime.now().plusDays(1);
        Tour tour = new Tour();
        tour.setName(tourName);
        tour.setCountry(tourCountry);
        tour.setCity(tourCity);
        tour.setArrivalTime(tourArrivalTime);
        return tour;
    }

    private Ticket generateTicket() {
        Long ticketId = 1L;
        int clientsAmount = 2;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setClientsAmount(clientsAmount);
        Tour tour = new Tour();
        tour.setArrivalTime(LocalDateTime.now().plusDays(1));
        tour.setPlacesAmount(2);
        ticket.setTour(tour);
        return ticket;
    }

}
