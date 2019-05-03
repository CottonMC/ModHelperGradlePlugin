package io.github.cottonmc.modhelper.annotations.test;

import io.github.cottonmc.contentgenerator.annotations.processor.DummyEvent;
import io.github.cottonmc.modhelper.api.events.Subscribe;
import java.util.*;

@Subscribe
class SimpleHandler implements DummyEvent{

    @Override
    public void handle(String thiz,Random random){
        System.out.println(thiz);
    }
}