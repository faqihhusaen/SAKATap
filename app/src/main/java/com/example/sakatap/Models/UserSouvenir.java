package com.example.sakatap.Models;

public class UserSouvenir {

    private Boolean souvenir1;
    private Boolean souvenir2;
    private Boolean souvenir3;

    public UserSouvenir() {

    }

    public UserSouvenir(Boolean souvenir1, Boolean souvenir2, Boolean souvenir3) {
        this.souvenir1 = souvenir1;
        this.souvenir2 = souvenir2;
        this.souvenir3 = souvenir3;
    }

    public Boolean getSouvenir1() {
        return souvenir1;
    }

    public void setSouvenir1(Boolean souvenir1) {
        this.souvenir1 = souvenir1;
    }

    public Boolean getSouvenir2() {
        return souvenir2;
    }

    public void setSouvenir2(Boolean souvenir2) {
        this.souvenir2 = souvenir2;
    }

    public Boolean getSouvenir3() {
        return souvenir3;
    }

    public void setSouvenir3(Boolean souvenir3) {
        this.souvenir3 = souvenir3;
    }
}
