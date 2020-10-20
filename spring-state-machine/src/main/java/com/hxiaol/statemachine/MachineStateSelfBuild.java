package com.hxiaol.statemachine;

import org.springframework.statemachine.config.StateMachineBuilder;

public class MachineStateSelfBuild {
    public  static void main(String args[]){
        StateMachineBuilder.Builder builder = StateMachineBuilder.builder();

        builder.configureTransitions();



    }
}