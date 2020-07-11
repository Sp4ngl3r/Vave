package com.vave.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -4188496902716430795L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;

	public Game() {

		new Window(WIDTH, HEIGHT, "VAVE", this);
	}

	public synchronized void start() {

		thread = new Thread(this);
		thread.start();
		running = true;

	}

	public synchronized void stop() {

		try {

			thread.join();
			running = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {

		long last_time = System.nanoTime();
		double amount_of_ticks = 60;
		double ns = 1000000000 / amount_of_ticks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {

			long now = System.nanoTime();
			delta += (now - last_time) / ns;
			last_time = now;
			while (delta >= 1) {

				tick();
				delta--;
			}
			if (running) {

				render();
			}

			frames++;

			if (System.currentTimeMillis() - timer > 1000) {

				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.orange);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.dispose();
		bs.show();

	}

	private void tick() {

	}

	public static void main(String[] args) {

		new Game();
	}
}
