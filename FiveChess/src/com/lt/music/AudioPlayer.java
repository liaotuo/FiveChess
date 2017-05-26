package com.lt.music;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
/***
 * 音乐播放器类
 * @author lt
 *	time 2016-7-5
 */
public class AudioPlayer extends Thread{
	Player player;
	InputStream music;
	//构造方法
	public AudioPlayer(InputStream file) {
		this.music = file;
	}
	@Override
	public void run() {
		super.run();
		try {
			play();
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
	}
	public void play() throws FileNotFoundException, JavaLayerException {
		
			BufferedInputStream buffer =
					new BufferedInputStream(music);
			player = new Player(buffer);
			player.play();
	}
}
