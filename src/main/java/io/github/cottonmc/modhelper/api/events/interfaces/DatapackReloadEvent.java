package io.github.cottonmc.modhelper.api.events.interfaces;


import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;

@EventDescriptor(
        value = "io.github.cottonmc.modhelper.api.annotations.events.mixin.DatapackReloadEventMixin",
        hasBefore = false
)
@FunctionalInterface
public interface DatapackReloadEvent {
    void reload();
}