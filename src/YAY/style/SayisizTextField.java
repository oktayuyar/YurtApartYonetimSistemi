/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YAY.style;

import javafx.scene.control.TextField;

/**
 *
 * @author Furkan
 */
public class SayisizTextField extends TextField {
int i=0;
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[a-zA-Z İÜÇŞĞışçüğ]") || text.isEmpty()) {
        if(i==0 || start==0){
            if (text.equals("i")) {
                text =  "İ";
            }else{
        text = text.toUpperCase();
        i = 1;
            }
        }
        if(text.equals(" ")){
            i=0;
        }
        super.replaceText(start, end, text);
        }
    }
 
    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement); //To change body of generated methods, choose Tools | Templates.
    }
    public void buyut(String text){
        
    }
}
