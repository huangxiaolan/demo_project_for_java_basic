package com.hxiaol.socketio.manager;

import com.hxiaol.socketio.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class SocketIoManager implements Manager {

    List<Unit> units = new ArrayList<>();
    @Override
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    @Override
    public void start() {
        units.forEach(unit -> unit.start());

    }

    @Override
    public void stop() {
        units.forEach(unit -> unit.stop());
    }

}