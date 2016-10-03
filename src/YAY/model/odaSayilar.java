/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.model;

/**
 *
 * @author oktay
 */
public class odaSayilar {
    private int topYatak;
    private int doluyatak;

    public int getTopYatak() {
        return topYatak;
    }

    public void setTopYatak(int topYatak) {
        this.topYatak = topYatak;
    }

    public int getDoluyatak() {
        return doluyatak;
    }

    public void setDoluyatak(int doluyatak) {
        this.doluyatak = doluyatak;
    }

    public odaSayilar(int topYatak, int doluyatak) {
        this.topYatak = topYatak;
        this.doluyatak = doluyatak;
    }

    public odaSayilar() {
    }
    
    
}
