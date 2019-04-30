package io.github.cottonmc.contentgenerator.annotations.processor

import io.github.cottonmc.modhelper.api.events.EventDescriptor

@EventDescriptor(
    mixinString = "dummy_string",
    targetClass = "java.lang.String",
    type = EventDescriptor.EventType.BEFORE
)
interface DummyEvent {
    fun handle(thiz:String)
}