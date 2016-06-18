package theLegendOfFinn.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import theLegendOfFinn.controller.RenderManager;
import theLegendOfFinn.controller.communicators.Delegate;
import theLegendOfFinn.model.entity.character.enemy.EnemyCharacter;
import theLegendOfFinn.model.entity.character.enemy.boss.Boss;
import theLegendOfFinn.view.character.CharacterRenderer;
import theLegendOfFinn.view.menu.GameOverRenderer;
import theLegendOfFinn.view.menu.MapSelectionRenderer;
import theLegendOfFinn.view.menu.MenuRenderer;
import theLegendOfFinn.view.menu.ModeRenderer;
import theLegendOfFinn.view.menu.PauseRenderer;
import theLegendOfFinn.view.menu.StartingMenuRenderer;

public class MasterRenderer extends Canvas implements KeyListener {
	private static final long serialVersionUID = 1L;

	private static final String SAVEFILE_ISSING_MESSAGE = "savefile.finn not found.";
	private static final String WIN_MESSAGE = "Congratulations! You've won!";
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH * 3 / 4;
	public static final String TITLE = "The legend of Finn";

	private Delegate delegate;

	private JFrame frame;
	private BufferStrategy bs;
	private Graphics g;

	private CharacterRenderer characterRenderer;
	private MapRenderer mapRenderer;
	private MenuRenderer startingMenuRenderer;
	private PauseRenderer pauseRenderer;
	private GUIRenderer guiRenderer;
	private GameOverRenderer gameOverRenderer;
	private MapSelectionRenderer mapSelectionRenderer;
	private ModeRenderer modeRenderer;

	public MasterRenderer(Delegate delegate) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		this.delegate = delegate;

		characterRenderer = new CharacterRenderer();
		startingMenuRenderer = new StartingMenuRenderer();
		pauseRenderer = new PauseRenderer();
		gameOverRenderer = new GameOverRenderer();
		mapSelectionRenderer = new MapSelectionRenderer();
		modeRenderer = new ModeRenderer();

		frame = new JFrame();
		addKeyListener(this);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		requestFocus();
	}

	public void initialize() {
		mapRenderer = new MapRenderer(delegate.getMap(), delegate.getArena());
		guiRenderer = new GUIRenderer(delegate.getPlayer());
	}

	public void render(RenderManager.Stage stage) {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (stage) {
		case MENU:
			startingMenuRenderer.render(g);
			break;
		case MAP:
			mapRenderer.render(g);
			characterRenderer.draw(delegate.getPlayer());
			for (EnemyCharacter enemy : delegate.getEnemies()) {
				if (enemy.getClass().equals(Boss.class)) {
					Boss boss = (Boss) enemy;
					characterRenderer.draw(boss.getProjectiles());
				}
				characterRenderer.draw(enemy);
			}
			characterRenderer.render(g);
			characterRenderer.dispose();
			guiRenderer.render(g);
			break;
		case PAUSE:
			pauseRenderer.render(g);
			break;
		case GAMEOVER:
			gameOverRenderer.render(g);
			break;
		case MAPSELECTION:
			mapSelectionRenderer.render(g);
			break;
		case MODE:
			modeRenderer.render(g);
			break;
		}

		bs.show();
		g.dispose();
	}

	public void setMapRenderer(MapRenderer mapRenderer) {
		this.mapRenderer = mapRenderer;
	}

	public void setMenuRenderer(MenuRenderer menuRenderer) {
		this.startingMenuRenderer = menuRenderer;
	}

	public void setPauseRenderer(PauseRenderer pauseRenderer) {
		this.pauseRenderer = pauseRenderer;
	}

	public void setMapSelectionRenderer(MapSelectionRenderer mapSelectionRenderer) {
		this.mapSelectionRenderer = mapSelectionRenderer;
	}

	public void setModeRenderer(ModeRenderer modeRenderer) {
		this.modeRenderer = modeRenderer;
	}

	public void displayFileMissing() {
		JOptionPane.showMessageDialog(frame, SAVEFILE_ISSING_MESSAGE);
	}

	/**
	 * Returns the current MenuRenderer
	 * 
	 * @return menuRenderer - Current menu renderer.
	 */
	public MenuRenderer getMenuRenderer() {
		return startingMenuRenderer;
	}

	public PauseRenderer getPauseRenderer() {
		return pauseRenderer;
	}

	public GameOverRenderer getGameOverRenderer() {
		return gameOverRenderer;
	}

	public MapSelectionRenderer getMapSelectionRenderer() {
		return mapSelectionRenderer;
	}

	public ModeRenderer getModeRenderer() {
		return modeRenderer;
	}

	public void keyPressed(KeyEvent e) {
		delegate.passKeyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void setTitleSuffix(String suffix) {
		frame.setTitle(MasterRenderer.TITLE + suffix);
	}
	
	public void displayWin(){
		JOptionPane.showMessageDialog(frame, WIN_MESSAGE);
	}
	
}