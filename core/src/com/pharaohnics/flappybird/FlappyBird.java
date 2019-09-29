package com.pharaohnics.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoTopo;
	private Texture canoTopoMaior;
	private Texture canoBaixo;
	private Texture canoBaixoMaior;
	private Random numeroRandomico;


	//Atributos de configuraçao
	private int larguraDispositivo;
	private int alturaDispositivo;
	private float variacaoPassaro = 0;
	private float velocidadeQueda = 0;
	private float posicaoVerticalInicial;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
	private float deltaTime;
	private float alturaEntreCanosRandomica;



	@Override
	public void create () {
		numeroRandomico = new Random();
		//Cria o SpriteBatch com o nome de batch
		batch = new SpriteBatch();
		//Cria e define as imagens para o passaro
		passaros  = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo 	= new Texture("fundo.png");

		canoBaixo = new Texture("cano_baixo.png");
		canoBaixoMaior = new Texture("cano_baixo_maior.png");
		canoTopoMaior = new Texture("cano_topo_maior.png");
		canoTopo = new Texture("cano_topo.png");

		//Recebe nos atributos a altura e largura da tela
		larguraDispositivo 	= Gdx.graphics.getWidth();
		alturaDispositivo 	= Gdx.graphics.getHeight();
		posicaoVerticalInicial = alturaDispositivo / 2;
		posicaoMovimentoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 300;

	}

	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime();
		variacaoPassaro += deltaTime *5 ;
		posicaoMovimentoCanoHorizontal -= deltaTime * 200;

		velocidadeQueda++;

		if(variacaoPassaro > 2)	variacaoPassaro = 0;

		if (Gdx.input.justTouched())
			velocidadeQueda = -15;


		if(posicaoVerticalInicial > 0 || velocidadeQueda < 0)
			//posicaoVerticalInicial = posicaoVerticalInicial - velocidadeQueda
			posicaoVerticalInicial -= velocidadeQueda ;

		//Verifica se o cano saiu inteiramente da tela
		if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()){
			posicaoMovimentoCanoHorizontal = larguraDispositivo;
			alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200  ;
		}

		//Cria o batch , aqui dentro vai somente os objetos apresentados na tela
		batch.begin(); 	//Inicio do batch

		batch.draw(fundo,0,0, larguraDispositivo, alturaDispositivo );
		batch.draw(canoTopoMaior,posicaoMovimentoCanoHorizontal , alturaDispositivo /2 + espacoEntreCanos /2 + alturaEntreCanosRandomica );
		batch.draw(canoBaixoMaior,posicaoMovimentoCanoHorizontal , alturaDispositivo /2 - canoBaixoMaior.getHeight() - espacoEntreCanos /2 + alturaEntreCanosRandomica);
		batch.draw(passaros[(int) variacaoPassaro], 120, posicaoVerticalInicial );

		batch.end();	//Final do batch

	}
	

}
