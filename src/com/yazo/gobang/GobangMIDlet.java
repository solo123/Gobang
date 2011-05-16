package com.yazo.gobang;

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public class GobangMIDlet extends MIDlet implements CommandListener {
	private Display dis;
	private Form frm;
	private Image img;
	private Command cmdExit;
	private Command cmdStart;
	private Command cmdSet;
	private Command cmdInt;
	private GobangCanvas gobangCanvas;
	private Options options;
	private Introduction ind;

	public GobangMIDlet() {
		dis = Display.getDisplay(this);
		frm = new Form(null);
		try {
			int w = frm.getWidth();
			int h = frm.getHeight();
			Image cover = Image.createImage("/GoBang.png");
			if (cover.getHeight()>=h || cover.getWidth()>=h){
				img = ImageUtil.effect_resizeImage(cover, w, h);
			} else {
				img = Image.createImage(w-1,h-1);
				Graphics g = img.getGraphics();
				g.setColor(0);
				g.fillRect(0, 0, w, h);
				g.drawImage(cover, (w-cover.getWidth())/2, (h-cover.getHeight())/2, Graphics.TOP|Graphics.LEFT);
				g = null;
			}
		} catch (IOException _ex) {
			img = Image.createImage(1, 1);
		}
		cmdExit = new Command("退出", Command.EXIT, 1);
		cmdStart = new Command("开局", Command.SCREEN, 1);
		cmdSet = new Command("设置", Command.SCREEN, 1);
		cmdInt = new Command("介绍", Command.SCREEN, 1);
		gobangCanvas = new GobangCanvas(this);
		options = new Options(this);
		ind = new Introduction(this);
	}

	public void startApp() {
		frm.append(img);
		frm.addCommand(cmdStart);
		frm.addCommand(cmdSet);
		frm.addCommand(cmdInt);
		frm.addCommand(cmdExit);
		frm.setCommandListener(this);
//		LogoCanvas log = new LogoCanvas();
		dis.setCurrent(frm);
//		dis.setCurrent(log);
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean arg0) {
	}

	public void commandAction(Command c, Displayable s) {
		if (c == cmdExit) {
			destroyApp(false);
			notifyDestroyed();
		} else if (c == cmdStart) {
			dis.setCurrent(gobangCanvas);
			gobangCanvas.setOptions(options.getBoardSize(),
					options.isComputerFirst(), options.getDegree());
			gobangCanvas.newStage();
		} else if (c == cmdSet)
			dis.setCurrent(options.getForm());
		else if (c == cmdInt)
			dis.setCurrent(ind.getForm());
	}

	public void comeBack() {
		dis.setCurrent(frm);
	}
}