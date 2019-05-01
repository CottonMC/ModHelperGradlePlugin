package io.github.cottonmc.modhelper.api.events;

import java.util.Optional;

public class EventControl<T> {

    private boolean cancelled = false;
    private T overridenReturnValue= null;

    public void cancel() {
        cancelled = true;
    }

    boolean isCancelled() {
        return cancelled;

    }

    public void overrideReturnValue(T returnValue){
        overridenReturnValue = returnValue;
    }

    Optional<T> getOverridenReturnValue(){
        if(overridenReturnValue == null){
            return Optional.empty();
        }
        return Optional.of(overridenReturnValue);
    }
}
