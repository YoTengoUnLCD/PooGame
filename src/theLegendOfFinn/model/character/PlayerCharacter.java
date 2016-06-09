package theLegendOfFinn.model.character;

import theLegendOfFinn.model.Map;
import theLegendOfFinn.model.Position;
import theLegendOfFinn.view.Renderer;

public class PlayerCharacter extends Character {
	//Datos segun nivel de jugador?
	public static final int[] PLAYER_MAX_HP = {6, 10};
	public static final int[] PLAYER_ATTACK = {1, 2};
	public static final int[] PLAYER_VELOCITY = {5, 5};
	
	public PlayerCharacter(int level) {
		super(new Position(Map.WIDTH * Renderer.CELL_SIZE / 2 - Renderer.CELL_SIZE,
				Map.HEIGHT * Renderer.CELL_SIZE / 2 - Renderer.CELL_SIZE / 2), Direction.DOWN, PLAYER_MAX_HP[level], PLAYER_ATTACK[level], PLAYER_VELOCITY[level]);
	}
}