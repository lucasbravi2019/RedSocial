package com.bravi.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Like {

    private int likes = 0;
    private Set<Cuenta> cuentas = new LinkedHashSet<>();

    public int getLikesQuantity() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void addLike(Cuenta cuenta) {
        if (cuenta != null) {
            boolean added = this.cuentas.add(cuenta);

            if (added)
                this.likes++;
        }
    }

    public void removeLike(Cuenta cuenta) {
        if (cuenta != null) {
            boolean removed = this.cuentas.remove(cuenta);

            if (removed)
                this.likes--;
        }
    }

}
