package io.github.cottonmc.modhelper.annotations.test;

import io.github.cottonmc.contentgenerator.annotations.processor.DummyEvent;
import io.github.cottonmc.modhelper.annotations.Subscribe;

@Subscribe
class SimpleHandler2 implements DummyEvent{

    @Override
    public void handle(String thiz){
        System.out.println(thiz);
    }
}