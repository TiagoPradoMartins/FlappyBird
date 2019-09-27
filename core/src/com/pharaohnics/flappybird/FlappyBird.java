package com.pharaohnics.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture passaro;
	private Texture fundo;

	//Atributos de configuraçao
	private int movimento = 0;
	private int larguraDispositivo;
	private int alturaDispositivo;

	@Override
	public void create () {
		batch = new SpriteBatch();
		passaro = new Texture("passaro1.png");
		fundo 	= new Texture("fundo.png");
		//Recebe nos atributos a altura e largura da tela
		larguraDispositivo 	= Gdx.graphics.getWidth();
		alturaDispositivo 	= Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 1, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin(); 	//Inicio do batch
		movimento++;
		batch.draw(fundo,0,0, larguraDispositivo, alturaDispositivo );
		batch.draw(passaro, movimento, 800);

		batch.end();	//Final do batch

	}
	
	/*@Override
	public void dispose () {
		batch.dispose();
		fundo.dispose();
		passaro.dispose();
	}*/
}
