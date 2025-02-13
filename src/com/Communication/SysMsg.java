/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Communication;

import java.awt.TrayIcon.MessageType;


/**
 *
 * @author Dell
 */
public abstract interface SysMsg {

    abstract String getMessage();

    abstract MessageType getType();

    abstract int getTime();
}
