package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static service.EventEnum.LIMPAR_ESPACO;


public class Notificacao {
    private final Map<EventEnum, List<EventListeners>> listeners = new HashMap<>(){{
        put(LIMPAR_ESPACO, new ArrayList<>());
    }};

    public void subscriber(final EventEnum tipoEvento, EventListeners listener){
        var selectedListeners = listeners.get(tipoEvento);
        selectedListeners.add(listener);
    }

    public void notificar(final EventEnum tipoEvento){
        listeners.get(tipoEvento).forEach(l -> l.update(tipoEvento));
    }
}
