package com.sbox.absforpwdassi;

import java.util.TimerTask;  
import com.sbox.absforpwdassi.UserHelper;
public class ExitTimerTask extends TimerTask 
{  
    @Override  
    public void run() {  
        UserHelper.setIsExit(false);  
    }  
}