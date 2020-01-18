package io.github.cottonmc.modhelper.api.events;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Function;

public class HandlerManager<T> {

    private List<T> handlers;

    private boolean hasEvents;

    HandlerManager(Class<T> handlerClass, Class... annotations) {
        handlers = new LinkedList<>();

        ServiceLoader.load(handlerClass).iterator().forEachRemaining(e -> {
            handlers.add(e);
            for (Class annotation : annotations) {
                if (!e.getClass().isAnnotationPresent(annotation)) {
                    handlers.remove(e);
                }
            }
        });

        hasEvents = !handlers.isEmpty();
    }

    public List<T> getHandlers() {
        return handlers;
    }

    public boolean shouldRun() {
        return hasEvents;
    }

    public boolean runEvent(Function<T, Boolean> cancelHandler) {
        if (shouldRun()) {
            for (T handler : handlers) {
                if (cancelHandler.apply(handler))
                    return false;
            }
        }
        return true;
    }


    public void executeEvent(Consumer<T> eventHandler) {
        if (shouldRun()) {
            for (T handler : handlers) {
                eventHandler.accept(handler);
            }
        }
    }

    public boolean executeEvent(Function<T, Boolean> cancelHandler, Consumer<T> eventHandler) {
        boolean runEvents = runEvent(cancelHandler);
        if (runEvents)
            handlers.forEach(eventHandler);

        return runEvents;
    }

    public void executeEvent(Function<T, Boolean> cancelHandler, Consumer<T> eventHandler, CallbackInfo ci) {
        if (!executeEvent(cancelHandler, eventHandler)) {
            ci.cancel();
        }
    }

    public void executeEvent(Function<T, Boolean> cancelHandler, Consumer<T> eventHandler, Function<T, Boolean> returnHandler) {
        boolean runEvents = runEvent(cancelHandler);
        if (runEvents) {
            for (T handler : handlers) {
                eventHandler.accept(handler);
            }
            for (T handler : handlers) {
                if (returnHandler.apply(handler)) {
                    return;
                }
            }
        }
    }
}
