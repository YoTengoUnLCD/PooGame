package theLegendOfFinn.controller.stageManagers;

import java.awt.event.KeyEvent;
import java.util.logging.Level;

import theLegendOfFinn.controller.Manager;
import theLegendOfFinn.controller.RenderManager.Stage;
import theLegendOfFinn.controller.exceptions.TickerMissingException;
import theLegendOfFinn.model.Ticker;
import theLegendOfFinn.model.Ticker.Arena;
import theLegendOfFinn.view.menu.MapSelectionRenderer;

/**
 * Manages the map selection stage view.
 */
public class StageMapSelectionManager extends StageManager{

	public StageMapSelectionManager(Manager manager) {
		super(manager);
	}

	/**
	 * Handles a given key during the current stage
	 */
	public Stage handleStage(int key) {
		Stage stage = Stage.MAPSELECTION;
		MapSelectionRenderer menuMapSelection = masterRenderer.getMapSelectionRenderer();
		
		switch (key) {
		case KeyEvent.VK_UP:
			menuMapSelection.selectOption(-2);
			break;
		case KeyEvent.VK_RIGHT:
			menuMapSelection.nextOption();
			break;
		case KeyEvent.VK_DOWN:
			menuMapSelection.selectOption(2);
			break;
		case KeyEvent.VK_LEFT:
			menuMapSelection.previousOption();
			break;
		case KeyEvent.VK_ESCAPE:
			stage = Stage.MODE;
			break;
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_A:
			Ticker ticker = manager.getTicker();
			if (menuMapSelection.getOption().equals(MapSelectionRenderer.GRASS))
				ticker.setArena(Arena.GRASS);
			else if(menuMapSelection.getOption().equals(MapSelectionRenderer.ICE))
				ticker.setArena(Arena.ICE);
			else if(menuMapSelection.getOption().equals(MapSelectionRenderer.MOUNTAIN))
				ticker.setArena(Arena.MOUNTAIN);
			else if(menuMapSelection.getOption().equals(MapSelectionRenderer.LAVA))
				ticker.setArena(Arena.LAVA);
			try {
				manager.initialize();
			} catch (TickerMissingException e) {
				Manager.LOGGER.log(Level.WARNING, "Ticker object missing", e);
				e.printStackTrace();
			}
			stage = Stage.MAP;
			break;
		}
		return stage;
	}

}
