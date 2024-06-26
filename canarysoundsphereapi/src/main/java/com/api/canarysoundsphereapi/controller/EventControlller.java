package com.api.canarysoundsphereapi.controller;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.canarysoundsphereapi.model.Event;
import com.api.canarysoundsphereapi.model.UpdateResponse;
import com.api.canarysoundsphereapi.payload.response.MessageResponse;
import com.api.canarysoundsphereapi.services.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador para la gestión de eventos.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/events")
@Tag(name = "Events resource", description = "Operations related to events management")
public class EventControlller {

    @Autowired
    private EventService eventService;

    /**
     * EndPoint para para listar eventos.
     * 
     * @return Devuelve una lista de todos los eventos.
     */
    @Operation(summary = "Get all events", description = "Retrieves a list of all events.")
    @GetMapping()
    public ArrayList<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * EndPoint para buscar eventos por id
     * 
     * @param id
     * @return Devuelve el evento en funcion del id enviado si lo encuentra
     */
    @Operation(summary = "Get event by id", description = "Finds an event by its ID.")
    @GetMapping("/{id}")
    public Optional<Event> findById(@PathVariable("id") String id) {
        return eventService.findById(id);
    }

    /**
     * EndPoint para crear un evento
     * 
     * @param event
     * @return Devuelve el evento creado
     */
    @Operation(summary = "Create a new event", description = "Creates a new event and returns the created event.")
    @PostMapping("")
    public ResponseEntity<?> postEvent(@RequestBody Event event) {
        eventService.postEvent(event);
        return ResponseEntity.ok(event);
    }

    /**
     * EndPoint para eliminar un event
     * 
     * @param id
     * @return Devuelve un mensaje de confirmación de la eliminación con el id
     */
    @Operation(summary = "Delete an event", description = "Deletes an event by its ID and returns a confirmation message.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(new MessageResponse("The event with ID " + id + " has been deleted."));
    }

    /**
     * EndPoint para actualizar un evento
     * 
     * @param id
     * @param event
     * @return Devuelve un mensaje con el id del evento
     *         que se actualizo y un json con la información del objeto actualizado
     */
    @Operation(summary = "Update an event", description = "Updates an existing event by its ID and returns the updated event.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") String id, @RequestBody Event event) {
        eventService.updateEvent(id, event);
        // Crea una instancia del objeto de respuesta con el mensaje y el evento
        // actualizado
        UpdateResponse<Event> response = new UpdateResponse<>("The event with ID " + id + " has been updated.", event);
        // Devuelve el objeto de respuesta en la respuesta HTTP
        return ResponseEntity.ok(response);
    }
}
